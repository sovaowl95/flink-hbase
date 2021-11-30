package ru;

import lombok.extern.slf4j.Slf4j;
import org.apache.flink.connector.hbase.sink.HBaseMutationConverter;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import ru.client.HBaseClientV2;
import ru.sink.MockTableMutationConverter;
import ru.sink.MockTableSink;
import ru.table.MockTable;

@Slf4j
public class Main {
  public static void main(String[] args) throws Exception {
    final Configuration configuration = createConfiguration();

    final HBaseClientV2 hBaseClientV2 = new HBaseClientV2(configuration);
    MainMigrations.initMigrations(hBaseClientV2);


    final HBaseMutationConverter mutationConverter = new MockTableMutationConverter();
    final MockTableSink mockTableSink = new MockTableSink(MockTable.TABLE_NAME,
                                                          configuration,
                                                          mutationConverter,
                                                          100,
                                                          100,
                                                          100); //todo: переделать на бины
    MainFlink.startFlink(mockTableSink);




    hBaseClientV2.readAll(MockTable.TABLE_NAME);
  }

  private static Configuration createConfiguration() {
    log.info("createConfiguration...");
    final Configuration configuration = HBaseConfiguration.create();
    configuration.set("hbase.zookeeper.quorum", "localhost");
    configuration.set("hbase.zookeeper.property.clientPort", "2181");

    log.info("createConfiguration... complete");
    return configuration;
  }


}
