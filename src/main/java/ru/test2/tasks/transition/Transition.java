package ru.test2.tasks.transition;

import com.google.protobuf.BoolValue;
import ecp.zhs.Output;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.test2.mock.bz.BzMsz;
import ru.test2.mock.bz.BzMszStageParam;
import ru.test2.mock.bz.BzMszTransition;
import ru.test2.mock.hbase.Msz;
import ru.test2.mock.hbase.MszStage;
import ru.test2.mock.hbase.MszStageParam;
import ru.test2.service.bz.BzMszParamService;
import ru.test2.service.bz.BzMszTransitionService;
import ru.test2.service.hbase.MszStageParamService;
import ru.test2.service.hbase.MszStageService;
import ru.test2.mock.dto.TransitionSaveDto;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
public class Transition {
  private final MszStageService mszStageService;
  private final MszStageParamService mszStageParamService;

  private final BzMszParamService bzMszParamService;
  private final BzMszTransitionService bzMszTransitionService;

  @SneakyThrows
  public List<TransitionSaveDto> transition(final Map<String, Output> outputsMap,
                                            @Nonnull final Optional<Msz> mszOptional,
                                            @Nonnull final String personId,
                                            final BzMsz bzMsz) {
    var res = new ArrayList<TransitionSaveDto>();

    final Optional<MszStage> mszStageOptional = mszStageService.findByMszOptional(mszOptional);

    final Set<BzMszTransition> possibleTransition
        = bzMszTransitionService.findAllPossibleTransition(mszStageOptional);

    final var possibleTransitionsList = new ArrayList<BzMszTransition>();
    for (BzMszTransition bzMszTransition : possibleTransition) {
      final String outputId = bzMszTransition.getOutputId();
      final Output output = outputsMap.get(outputId);
      if (output != null
          && output.getValue().unpack(BoolValue.class).getValue()) {
        possibleTransitionsList.add(bzMszTransition);
        if (possibleTransitionsList.size() > 1) {
          throw new RuntimeException("too many possible transitions");
        }
      }
    }
    if (possibleTransition.isEmpty()) {
      log.info("no possible transitions: ");
    } else {
      final TransitionSaveDto transitionSaveDto = doTransition(outputsMap,
                                                               mszOptional,
                                                               possibleTransitionsList.get(0),
                                                               personId,
                                                               bzMsz);
      res.add(transitionSaveDto);
    }

    return res;
  }


  private TransitionSaveDto doTransition(final Map<String, Output> outputsMap,
                                         @Nonnull final Optional<Msz> mszOptional,
                                         final BzMszTransition bzMszTransition,
                                         @Nonnull final String personId,
                                         final BzMsz bzMsz) {
    final TransitionSaveDto transitionSaveDto = new TransitionSaveDto();

    final Msz msz;
    if (mszOptional.isEmpty()) {
      msz = new Msz();
      msz.setId(UUID.randomUUID().toString());
      msz.setBzMszId(bzMsz.getId());
      msz.setPersonId(personId);

      transitionSaveDto.setMsz(msz);
    } else {
      msz = mszOptional.get();
    }

    final String toBzMszStageId = bzMszTransition.getToBzMszStageId();

    final MszStage mszStage = mszStageService.createMszStage(bzMszTransition,
                                                             bzMszTransition.getToBzMszStageId(),
                                                             msz);
    transitionSaveDto.setMszStage(mszStage);

    //проверить, что все параметры представлены
    final List<BzMszStageParam> bzMszStageParams = bzMszParamService.getAllByMszStage(toBzMszStageId);
    for (BzMszStageParam bzMszStageParam : bzMszStageParams) {
      if (outputsMap.get(bzMszStageParam.getOutputId()) == null) {
        throw new RuntimeException("parameter not present");
      }
    }

    for (BzMszStageParam bzMszStageParam : bzMszStageParams) {
      final Output value = outputsMap.get(bzMszStageParam.getOutputId());
      //todo: добавить проверку изменено ли значение.
      // попытаться переиспользовать Param
      final MszStageParam mszStageParam = mszStageParamService.create(mszStage.getId(),
                                                                      bzMszStageParam,
                                                                      value);
      transitionSaveDto.getParamList().add(mszStageParam);
    }

    return transitionSaveDto;
  }
}
