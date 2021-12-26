package ru.test2;

import ecp.zhs.Output;
import ecp.zhs.WaveStateUpdate;
import lombok.RequiredArgsConstructor;
import ru.test.mock.bz.BzMsz;
import ru.test.mock.hbase.Msz;
import ru.test.service.bz.BzMszService;
import ru.test.service.hbase.MszService;
import ru.test2.tasks.Param;
import ru.test2.tasks.transition.Transition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class EntryPoint {
  private final MszService mszService;
//
  private final BzMszService bzMszService;
//  private final BzMszStageService bzMszStageService;
//  private final BzMszStageParamService bzMszStageParamService;
//  private final BzMszTransitionStagesService bzMszTransitionStagesService;

  private final Param param;
  private final Transition transition;

  public void go(final String personId,
                 final WaveStateUpdate waveStateUpdate) throws Exception {
    String outputId = "";
    var changedOutputs = waveStateUpdate.getWaveState().getExecutionsList()
                                        .stream()
                                        .flatMap(x -> x.getNewOutputVersionsList()
                                                       .stream()
                                                       .map(o -> o.getOutputId()))
                                        .collect(Collectors.toSet());
    var isOutputChanged = changedOutputs.contains(outputId);

    final Map<String, Output> outputsMap = waveStateUpdate.getAllOutputs().getOutputsMap(); //todo: проверить

    final List<Msz> currentMsz = mszService.findAllHumanMsz(personId);
//    final List<MszStage> currentStages = mszStageService.findByMsz(currentMsz);

    final HashMap<BzMsz, Msz> map = createMap(currentMsz);

    for (Map.Entry<BzMsz, Msz> entry : map.entrySet()) {
      //todo: загружаем из ЖС экземпляр этой мсз... ноо.... мы же уже загрузили
      final BzMsz bzMsz = entry.getKey();
      final Msz msz = entry.getValue();

      if (msz != null) {
        param.updateParams(outputsMap, msz, false);
      }
      transition.transition(outputsMap, msz, personId, bzMsz);
    }
  }

  private HashMap<BzMsz, Msz> createMap(final List<Msz> currentMsz) {
    final HashMap<BzMsz, Msz> map = new HashMap<>(); //todo: @EqualsAndHashCode
    for (BzMsz bzMsz : bzMszService.getAll()) {
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
