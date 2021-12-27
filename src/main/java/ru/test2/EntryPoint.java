package ru.test2;

import com.apollographql.apollo.ApolloClient;
import ecp.zhs.Output;
import ecp.zhs.WaveStateUpdate;
import ru.test.mock.bz.BzMsz;
import ru.test.mock.hbase.Msz;
import ru.test.mock.hbase.MszStageParam;
import ru.test.service.bz.BzMszParamService;
import ru.test.service.bz.BzMszService;
import ru.test.service.bz.BzMszTransitionService;
import ru.test.service.hbase.MszService;
import ru.test.service.hbase.MszStageParamService;
import ru.test.service.hbase.MszStageService;
import ru.test2.dto.TransitionSaveDto;
import ru.test2.tasks.param.Param;
import ru.test2.tasks.transition.Transition;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EntryPoint {
  public static final String HTTP_LOCALHOST_8080_GRAPHQL = "http://localhost:8080/graphql";

  private final MszService mszService;
  private final MszStageService mszStageService;
  private final MszStageParamService mszStageParamService;

  private final BzMszService bzMszService;
  private final BzMszParamService bzMszParamService;
  private final BzMszTransitionService bzMszTransitionService;


  private final Param param;
  private final Transition transition;

  private final ApolloClient apolloClient;

  public EntryPoint() {
    apolloClient = ApolloClient.builder()
                               .serverUrl(HTTP_LOCALHOST_8080_GRAPHQL)
                               .build();

    mszService = new MszService();
    mszStageService = new MszStageService();
    mszStageParamService = new MszStageParamService();

    bzMszService = new BzMszService(apolloClient);
    bzMszParamService = new BzMszParamService(apolloClient);
    bzMszTransitionService = new BzMszTransitionService(apolloClient);

    param = new Param(mszStageService, mszStageParamService, bzMszParamService);
    transition = new Transition(mszStageService, mszStageParamService, bzMszParamService, bzMszTransitionService);
  }

  public void go(final String personId,
                 final WaveStateUpdate waveStateUpdate) {
    final Map<String, Output> outputsMap = waveStateUpdate.getAllOutputs().getOutputsMap();
    final var changedOutputs
        = waveStateUpdate.getWaveState().getExecutionsList()
                         .stream()
                         .flatMap(x -> x.getNewOutputVersionsList()
                                        .stream()
                                        .map(Output::getOutputId))
                         .collect(Collectors.toSet());

    final HashMap<BzMsz, Msz> bzMszToUserMsz = mszService.createMszMap(personId,
                                                                       bzMszService.getAll());

    List<MszStageParam> save1 = null;
    List<TransitionSaveDto> save2 = null;

    //пройтись по каждому возможному МСЗ. по идее.. нужно еще фильтровать по OutputId
    for (Map.Entry<BzMsz, Msz> entry : bzMszToUserMsz.entrySet()) {
      final BzMsz bzMsz = entry.getKey();
      final Msz msz = entry.getValue();

      if (msz != null) {
        save1 = param.updateParams(outputsMap, msz, changedOutputs, false);
      }
      save2 = transition.transition(outputsMap, msz, personId, bzMsz);
    }

    saveAll(save1, save2);
  }

  private void saveAll(@Nullable final List<MszStageParam> save1,
                       @Nullable final List<TransitionSaveDto> save2) {
    if (save1 != null) {
      for (MszStageParam mszStageParam : save1) {
        mszStageParamService.save(mszStageParam);
      }
    }

    if (save2 != null) {
      for (TransitionSaveDto transitionSaveDto : save2) {
        if (transitionSaveDto.getMsz() != null) {
          mszService.save(transitionSaveDto.getMsz());
        }

        mszStageService.save(transitionSaveDto.getMszStage());
        mszStageParamService.saveAll(transitionSaveDto.getParamList());
      }
    }
  }
}
