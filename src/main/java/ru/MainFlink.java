package ru;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.util.Collector;
import ru.task.MockTask;

import java.util.Random;
import java.util.UUID;

class MainFlink {
  private static final Random random = new Random();

  static void startFlink(SinkFunction sinkFunction) throws Exception {
    final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

    env.fromSequence(1, 10L)
       .flatMap((Long value, Collector<Object> out) -> {
         final MockTask mockTask = new MockTask();
         mockTask.setId(random.nextLong());
         mockTask.setSomeString(UUID.randomUUID().toString());
         out.collect(mockTask);
       })
       .returns((Class<Object>) Class.forName("ru.task.MockTask")) //todo: сделать нормально
       .addSink(sinkFunction);

    env.execute();
  }
}
