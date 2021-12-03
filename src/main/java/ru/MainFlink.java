package ru;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import ru.task.MockTask;

import java.util.Random;
import java.util.UUID;

class MainFlink {
  private static final Random random = new Random();

  static void startFlink(SinkFunction sinkFunction) throws Exception {
    final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

    env.fromSequence(1, 1_000_000L)
       .map((Long value) -> {
         final MockTask mockTask = new MockTask();
         mockTask.setId(random.nextLong());
         mockTask.setSomeString(UUID.randomUUID().toString());
         return mockTask;
       })
       .addSink(sinkFunction);

    long start = System.currentTimeMillis();
    env.execute();
    long diff = System.currentTimeMillis() - start;
    System.out.println("diff = " + diff);
  }
}
