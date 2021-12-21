package ru.test2;

import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import ecp.zhs.Output;
import ecp.zhs.WaveStateUpdate;
import lombok.RequiredArgsConstructor;
import ru.test.mock.bz.BzMsz;
import ru.test.mock.bz.BzMszStageParam;
import ru.test.mock.bz.BzMszTransition;
import ru.test.mock.hbase.Msz;
import ru.test.mock.hbase.MszStage;
import ru.test.mock.hbase.MszStageParam;
import ru.test.service.bz.BzMszService;
import ru.test.service.bz.BzMszStageParamService;
import ru.test.service.bz.BzMszStageService;
import ru.test.service.bz.BzMszTransitionStagesService;
import ru.test.service.hbase.MszService;
import ru.test.service.hbase.MszStageParamService;
import ru.test.service.hbase.MszStageService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class EntryPoint {
  private final MszService mszService;
  private final MszStageParamService mszStageParamService;
  private final MszStageService mszStageService;

  private final BzMszService bzMszService;
  private final BzMszStageService bzMszStageService;
  private final BzMszStageParamService bzMszStageParamService;
  private final BzMszTransitionStagesService bzMszTransitionStagesService;


  //todo: их можно вынести в сервисы

  List<BzMsz> bzMszList;
  //  List<BzMszStage> bzMszStageList;
  List<BzMszStageParam> bzMszStageParamList;
  List<BzMszTransition> bzMszTransitionList;


  @PostConstruct
  public void init() {
    bzMszList = bzMszService.findAll();
//    bzMszStageList = bzMszStageService.findAll();
    bzMszStageParamList = bzMszStageParamService.findAll();
    bzMszTransitionList = bzMszTransitionStagesService.findAll();
  }

  public void go(final String personId,
                 final WaveStateUpdate waveStateUpdate) throws Exception {
    final Map<String, Output> outputsMap = waveStateUpdate.getAllOutputs().getOutputsMap(); //todo: проверить

    final List<Msz> currentMsz = mszService.findAllHumanMsz(personId);
    final List<MszStage> currentStages = mszStageService.findByMsz(currentMsz);

    final HashMap<BzMsz, Msz> map = createMap(currentMsz);

    for (Map.Entry<BzMsz, Msz> entry : map.entrySet()) {
      forEach(outputsMap, entry, personId);
    }
  }

  private void forEach(final Map<String, Output> outputsMap,
                       final Map.Entry<BzMsz, Msz> entry,
                       final String personId) throws Exception {
    //todo: загружаем из ЖС экземпляр этой мсз... ноо.... мы же уже загрузили
    final BzMsz bzMsz = entry.getKey();
    final Msz msz = entry.getValue();

    if (msz != null) {
      updateParams(outputsMap, msz);
    }
    transition(outputsMap, msz, personId, bzMsz);
  }

  private void updateParams(final Map<String, Output> outputsMap,
                            @Nonnull final Msz msz) {
    //todo: можно ли как-то оптимизировать?
    final Optional<MszStage> mszStageOptional = mszStageService.findByMsz(msz);
    //if(mszStageOptional.isPresent())//todo:
    final MszStage mszStage = mszStageOptional.get();
    final List<MszStageParam> allParams = mszStageParamService.findAllByMszStage(mszStage);

    for (BzMszStageParam bzMszStageParam : bzMszStageParamList) {
      final String outputId = bzMszStageParam.getOutputId();
      final Output output = outputsMap.get(outputId);

      //todo: в схеме не предусмотрено отсутствие этого параметра
      //todo: если output == null, то это ошибка. прерываем весь пайп.
      if (output != null /* && output.valueChanged */) { //todo:
        final Any value = output.getValue();
        //todo: нужно как-то понять тип этого value. вероятно по связи Msz с BzMsz ->
        // bzMszStageParam.getType()
        //в протобафе есть wrappers.proto

        final MszStageParam mszStageParam = new MszStageParam();
        mszStageParam.setId(UUID.randomUUID().toString());
        mszStageParam.setMszStageId(mszStage.getId());
        mszStageParam.setBzMszStageParamId(bzMszStageParam.getId());
        mszStageParam.setValue(value);
        mszStageParamService.save(mszStageParam); //todo: batch
        //todo: собрать все сущности в одну коллекцию, потом сохранить батчем
      }
    }
  }

  private void transition(final Map<String, Output> outputsMap,
                          @Nullable final Msz msz,
                          @Nonnull final String personId,
                          final BzMsz bzMsz) throws InvalidProtocolBufferException {
    final Optional<MszStage> mszStageOptional = mszStageService.findByMszOptional(msz);

    //todo: в доках: По этому этапу и данным БЗ находим возможные переходы...
    //todo: как может быть множество переходов?
    final Set<BzMszTransition> possibleTransition; //todo: equals&HashCode

    final Predicate<BzMszTransition> bzMszTransitionPredicate;
    if (mszStageOptional.isEmpty()
        || mszStageOptional.get().getBzMszTransactionStagesId() == null) {
      bzMszTransitionPredicate = bzMszTransition -> bzMszTransition.getFromBzMszStageId() == null;
    } else {
      bzMszTransitionPredicate = bzMszTransition -> {
        final String bzMszStageId = mszStageOptional.get().getBzMszStageId();
        return bzMszTransition.getFromBzMszStageId().equals(bzMszStageId);
      };
    }


    possibleTransition = bzMszTransitionList.stream()
                                            .filter(bzMszTransitionPredicate)
                                            .collect(Collectors.toSet());

    for (BzMszTransition bzMszTransition : possibleTransition) {
      final String outputId = bzMszTransition.getOutputId();
      final Output output = outputsMap.get(outputId); //todo: а если его нет?...

      if (output == null) {
        continue;
      }

      final Boolean unpack = output.getValue().unpack(Boolean.class); //todo: НЕ РАБОТАЕТ.  БЕСИТ!
      if (unpack) { //todo: логика value & valueChanged разная. придумать как это всё упростить

        //todo: перед вызовом Transition убедиться, что возможен только 1 переход
        doTransition(msz, bzMszTransition, personId, bzMsz);
      }
    }
  }

  private Msz doTransition(@Nullable Msz msz,
                           final BzMszTransition bzMszTransition,
                           @Nonnull final String personId,
                           final BzMsz bzMsz) {
    if (msz == null) {
      msz = new Msz();
      msz.setId(UUID.randomUUID().toString());
      msz.setBzMszId(bzMsz.getId());
      msz.setPersonId(personId);
    }
    final String toBzMszStageId = bzMszTransition.getToBzMszStageId();

    //todo: а нужно ли создавать?
    final MszStage mszStage = new MszStage();
    mszStage.setId(UUID.randomUUID().toString());
    mszStage.setMszId(msz.getId());
    mszStage.setBzMszTransactionStagesId(bzMszTransition.getId());
    mszStage.setBzMszStageId(toBzMszStageId);

    final List<BzMszStageParam> bzMszStageParams
        = bzMszStageParamList.stream()
                             .filter(bzMszStageParam -> bzMszStageParam.getBzMszStageId().equals(toBzMszStageId))
                             .collect(Collectors.toList());

    for (BzMszStageParam bzMszStageParam : bzMszStageParams) {
      final MszStageParam mszStageParam = mszStageParamService.create(bzMszStageParam);
      //todo: переиспользовать логику updateParams (код выше), учесть, что не нужно смотреть на значение valueChanged.
      // также, нужно нужно проверить, что все необходимые для Stage параметры пришли, если нет, то throw Exception.
      mszStageParamService.save(mszStageParam);
    }
    return msz;
  }

  private HashMap<BzMsz, Msz> createMap(List<Msz> currentMsz) {
    final HashMap<BzMsz, Msz> map = new HashMap<>(); //todo: @EqualsAndHashCode
    for (BzMsz bzMsz : bzMszList) {
      //todo: O(1)
      final Msz msz = currentMsz.stream()
                                .filter(mszCurr -> mszCurr.getBzMszId().equals(bzMsz.getId()))
                                .findFirst()
                                .orElse(null);
      map.put(bzMsz, msz);
    }
    return map;
  }
}
