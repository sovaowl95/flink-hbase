package ru;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import ru.client.HBaseClientV2;
import ru.migration.MigrationV1;
import ru.sink.MySinc;
import ru.table.HvacTable;

public class Main {
  public static void main(String[] args) throws Exception {
    HBaseClientV2 hBaseClientV2 = new HBaseClientV2();
    hBaseClientV2.setUp();

    initMigrations(hBaseClientV2);

//    hBaseClientV2.write(HvacTable.TABLE_NAME, src);
//    hBaseClientV2.readAll(HvacTable.TABLE_NAME);
//    hBaseClientV2.close();


    startFlink();


    hBaseClientV2.readAll(HvacTable.TABLE_NAME);
  }

  private static void initMigrations(HBaseClientV2 hBaseClientV2) throws Exception {
    MigrationV1 migrationV1 = new MigrationV1();
    migrationV1.migrate(hBaseClientV2);
  }

  private static void startFlink() throws Exception {
    final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

    final DataStreamSource<Long> longDataStreamSource = env.fromSequence(1, 2L);
    longDataStreamSource.addSink(new MySinc());
    longDataStreamSource.print();

    env.execute();
  }
}
