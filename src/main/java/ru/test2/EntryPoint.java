package ru.test2;

import ecp.zhs.Output;
import ecp.zhs.WaveStateUpdate;
import lombok.RequiredArgsConstructor;
import ru.test.mock.bz.BzMsz;
import ru.test.mock.hbase.Msz;
import ru.test.mock.hbase.MszStageParam;
import ru.test.service.bz.BzMszService;
import ru.test.service.hbase.MszService;
import ru.test.service.hbase.MszStageParamService;
import ru.test.service.hbase.MszStageService;
import ru.test2.tasks.param.Param;
import ru.test2.tasks.transition.Transition;
import ru.test2.tasks.transition.dto.TransitionSaveDto;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class EntryPoint {
  private final MszService mszService;
  private final MszStageService mszStageService;
  private final MszStageParamService mszStageParamService;

  private final BzMszService bzMszService;


  private final Param param;
  private final Transition transition;

  public void go(final String personId,
                 final WaveStateUpdate waveStateUpdate) {
    final Map<String, Output> outputsMap = waveStateUpdate.getAllOutputs().getOutputsMap();
    final var changedOutputs = waveStateUpdate.getWaveState().getExecutionsList()
                                              .stream()
                                              .flatMap(x -> x.getNewOutputVersionsList()
                                                             .stream()
                                                             .map(Output::getOutputId))
                                              .collect(Collectors.toSet());
    final HashMap<BzMsz, Msz> map = createMap(personId);


    List<MszStageParam> save1 = null;
    List<TransitionSaveDto> save2 = null;

    for (Map.Entry<BzMsz, Msz> entry : map.entrySet()) {
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

  private HashMap<BzMsz, Msz> createMap(final String personId) {
    final Map<String, Msz> currentMsz = mszService.findAllKeyBzMszId(personId);

    final HashMap<BzMsz, Msz> map = new HashMap<>();
    for (BzMsz bzMsz : bzMszService.getAll()) {
      map.put(bzMsz, currentMsz.getOrDefault(bzMsz.getId(), null));
    }
    return map;
  }
}
