package ru.test2.tasks.transition;

import com.google.protobuf.BoolValue;
import com.google.protobuf.InvalidProtocolBufferException;
import ecp.zhs.Output;
import lombok.RequiredArgsConstructor;
import ru.test.mock.bz.BzMsz;
import ru.test.mock.bz.BzMszStageParam;
import ru.test.mock.bz.BzMszTransition;
import ru.test.mock.hbase.Msz;
import ru.test.mock.hbase.MszStage;
import ru.test.mock.hbase.MszStageParam;
import ru.test.service.bz.BzMszStageParamService;
import ru.test.service.bz.BzMszTransitionService;
import ru.test.service.hbase.MszStageParamService;
import ru.test.service.hbase.MszStageService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class Transition {
  private final MszStageService mszStageService;
  private final MszStageParamService mszStageParamService;

  private final BzMszTransitionService bzMszTransitionService;
  private final BzMszStageParamService bzMszStageParamService;


  public void transition(final Map<String, Output> outputsMap,
                         @Nullable final Msz msz,
                         @Nonnull final String personId,
                         final BzMsz bzMsz) throws InvalidProtocolBufferException {
    final Optional<MszStage> mszStageOptional = mszStageService.findByMszOptional(msz);


    //todo: в доках: По этому этапу и данным БЗ находим возможные переходы...
    //todo: как может быть множество переходов?
    //todo: equals&HashCode
    final Set<BzMszTransition> possibleTransition
        = bzMszTransitionService.findAll()
                                .stream()
                                .filter(TransitionPredicate.getPredicate(mszStageOptional))
                                .collect(Collectors.toSet());

    for (BzMszTransition bzMszTransition : possibleTransition) {
      final String outputId = bzMszTransition.getOutputId();
      final Output output = outputsMap.get(outputId); //todo: а если его нет?...
      if (output == null) {
        continue;
      }

      final boolean unpack = output.getValue().unpack(BoolValue.class).getValue();
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
        = bzMszStageParamService.getAll()
                                .stream()
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
}
