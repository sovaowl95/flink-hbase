//package ru.sink;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.flink.configuration.Configuration;
//import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
//import org.apache.hadoop.hbase.HBaseConfiguration;
//import org.apache.hadoop.hbase.TableName;
//import org.apache.hadoop.hbase.client.BufferedMutator;
//import org.apache.hadoop.hbase.client.BufferedMutatorParams;
//import org.apache.hadoop.hbase.client.Connection;
//import org.apache.hadoop.hbase.client.ConnectionFactory;
//import org.apache.hadoop.hbase.client.Put;
//import ru.table.HvacTable;
//
//import java.io.IOException;
//
//
//@Slf4j
//public class MySinc extends RichSinkFunction<Long> {
//  private static org.apache.hadoop.conf.Configuration configuration;
//  private static Connection connection = null;
//  private static BufferedMutator mutator;
////  private static int count = 0;
//
//  @Override
//  public void open(Configuration parameters) throws Exception {
//    configuration = HBaseConfiguration.create();
//    configuration.set("hbase.master", "192.168.3.101:60020");
//    configuration.set("hbase.zookeeper.quorum", "localhost");
//    configuration.set("hbase.zookeeper.property.clientPort", "2181");
//    try {
//      connection = ConnectionFactory.createConnection(configuration);
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//
//    //todo: херня какая-то
//    BufferedMutatorParams params = new BufferedMutatorParams(TableName.valueOf(HvacTable.TABLE_NAME));
//    params.writeBufferSize(2 * 1024 * 1024);
//    mutator = connection.getBufferedMutator(params);
//  }
//
//  @Override
//  public void close() throws IOException {
////    if (mutator != null) {
////      mutator.close();
////    }
////    if (connection != null) {
////      connection.close();
////    }
//  }
//
//  @Override
//  public void invoke(Long values,
//                     Context context) throws Exception {
//    String family = HvacTable.CF_FIRST;
//    String qualifier = "qualifier-2";
//    String value = "value-2";
//
//    final byte[] id = "bla-bla-2".getBytes();
//
//    Put put = new Put(id);
//    put.addColumn(family.getBytes(), qualifier.getBytes(), value.getBytes());
//
//    mutator.mutate(put);
//    mutator.flush();
//
//
////    // full 500 освежает данные
////    if (count >= 500) {
////      mutator.flush();
////      count = 0;
////    }
////    count = count + 1;
//  }
//
//}
