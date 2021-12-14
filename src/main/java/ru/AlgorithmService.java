package ru;

import ecp.zhs.Output;
import ecp.zhs.OutputVersion;
import ecp.zhs.WaveStateUpdate;
import ru.test.tasks.AggregationTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AlgorithmService {
  private static final ExecutorService executorService = Executors.newFixedThreadPool(10);

  void process(WaveStateUpdate waveStateUpdate) {
    final OutputVersion allOutputs = waveStateUpdate.getAllOutputs();
    for (Output output : allOutputs.getOutputsMap().values()) {
      final AggregationTask aggregationTask = new AggregationTask(output);
      executorService.execute(aggregationTask);
    }
  }
}
