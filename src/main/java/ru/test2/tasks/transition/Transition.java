package ru.test2.tasks.transition;

import com.google.protobuf.BoolValue;
import ecp.zhs.Output;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
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
import ru.test2.tasks.transition.dto.TransitionSaveDto;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class Transition {
  private final MszStageService mszStageService;
  private final MszStageParamService mszStageParamService;

  private final BzMszTransitionService bzMszTransitionService;
  private final BzMszStageParamService bzMszStageParamService;

  @SneakyThrows
  public List<TransitionSaveDto> transition(final Map<String, Output> outputsMap,
                                            @Nullable final Msz msz,
                                            @Nonnull final String personId,
                                            final BzMsz bzMsz) {

    var res = new ArrayList<TransitionSaveDto>();
    final Optional<MszStage> mszStageOptional = mszStageService.findByMszOptional(msz);

    final Set<BzMszTransition> possibleTransition
        = bzMszTransitionService.findAll()
                                .stream()
                                .filter(TransitionPredicate.getPredicate(mszStageOptional))
                                .collect(Collectors.toSet());


    //todo: выглядит так, что нужно зацикливать. ибо 1 -> 2, 2 -> 3. но вроде как такие не могут быть...
    final ArrayList<BzMszTransition> possibleTransitionsList = new ArrayList<>();
    for (BzMszTransition bzMszTransition : possibleTransition) {
      final String outputId = bzMszTransition.getOutputId();
      final Output output = outputsMap.get(outputId);
      if (output == null) {
        continue;
      }

      if (output.getValue().unpack(BoolValue.class).getValue()) {
        possibleTransitionsList.add(bzMszTransition);
        if (possibleTransitionsList.size() > 1) {
          throw new RuntimeException("too many possible transitions");
        }
      }
    }

    if (possibleTransition.isEmpty()) {
      log.info("no possible transitions: ");
    } else {
      res.add(doTransition(outputsMap, msz, possibleTransitionsList.get(0), personId, bzMsz));
    }

    return res;
  }


  private TransitionSaveDto doTransition(final Map<String, Output> outputsMap,
                                         @Nullable Msz msz,
                                         final BzMszTransition bzMszTransition,
                                         @Nonnull final String personId,
                                         final BzMsz bzMsz) {
    final TransitionSaveDto transitionSaveDto = new TransitionSaveDto();

    if (msz == null) {
      msz = new Msz();
      msz.setId(UUID.randomUUID().toString());
      msz.setBzMszId(bzMsz.getId());
      msz.setPersonId(personId);

      transitionSaveDto.setMsz(msz);
    }

    final String toBzMszStageId = bzMszTransition.getToBzMszStageId();

    final MszStage mszStage = new MszStage();
    mszStage.setId(UUID.randomUUID().toString());
    mszStage.setMszId(msz.getId());
    mszStage.setBzMszTransactionStagesId(bzMszTransition.getId());
    mszStage.setBzMszStageId(toBzMszStageId);

    transitionSaveDto.setMszStage(mszStage);

    //проверить, что все параметры представлены
    final List<BzMszStageParam> bzMszStageParams = bzMszStageParamService.getAllByMszStage(toBzMszStageId);
    for (BzMszStageParam bzMszStageParam : bzMszStageParams) {
      if (outputsMap.get(bzMszStageParam.getOutputId()) == null) {
        throw new RuntimeException("parameter not present");
      }
    }

    for (BzMszStageParam bzMszStageParam : bzMszStageParams) {
      final MszStageParam mszStageParam = mszStageParamService.create(bzMszStageParam);
      transitionSaveDto.getParamList().add(mszStageParam);
    }

    return transitionSaveDto;
  }
}
