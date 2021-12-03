package ru;

import lombok.extern.slf4j.Slf4j;
import org.apache.flink.connector.hbase.sink.HBaseMutationConverter;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Put;
import ru.client.HBaseClientV2;
import ru.sink.MockTableMutationConverter;
import ru.sink.MockTableSink;
import ru.table.MockTable;
import ru.table.lsfc.LsfcExecutionChain;

@Slf4j
public class Main {

  //  public static final int BUFFER_FLUSH_MAX_SIZE_IN_BYTES = 10_000;
  public static final int BUFFER_FLUSH_MAX_SIZE_IN_BYTES = 100_000;
//  public static final int BUFFER_FLUSH_MAX_SIZE_IN_BYTES = 1_000_000;

  public static void main(String[] args) throws Exception {
    final Configuration configuration = createConfiguration();

    final HBaseClientV2 hBaseClientV2 = new HBaseClientV2(configuration);
//    MainMigrations.initMigrations(hBaseClientV2);


    final HBaseMutationConverter mutationConverter = new MockTableMutationConverter();
    final MockTableSink mockTableSink = new MockTableSink(MockTable.TABLE_NAME,
                                                          configuration,
                                                          mutationConverter,
                                                          BUFFER_FLUSH_MAX_SIZE_IN_BYTES,
                                                          BUFFER_FLUSH_MAX_SIZE_IN_BYTES,
                                                          BUFFER_FLUSH_MAX_SIZE_IN_BYTES); //todo: переделать на бины
    MainFlink.startFlink(mockTableSink);


//    hBaseClientV2.readAll(MockTable.TABLE_NAME);
    hBaseClientV2.countAll(MockTable.TABLE_NAME);
  }

  private static Configuration createConfiguration() {
    log.info("createConfiguration...");
    final Configuration configuration = HBaseConfiguration.create();
    configuration.set("hbase.zookeeper.quorum", "localhost");
    configuration.set("hbase.zookeeper.property.clientPort", "2181");

    log.info("createConfiguration... complete");
    return configuration;
  }


  public void test() {
//    Put put = new Put("".getBytes());
//    put.addColumn(LsfcExecutionChain.Cf1.CF_NAME.getBytes(),
//                  LsfcExecutionChain.Cf1.Id.Q_NAME.getBytes(),
//                  "value".getBytes());

    Put put = new Put("".getBytes());
    put.addColumn(LsfcExecutionChain.TABLE__CF1.getBytes(),
                  LsfcExecutionChain.TABLE__CF1__ID.getBytes(),
                  "value".getBytes());
  }
}
