//package ru.client;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.hbase.TableName;
//import org.apache.hadoop.hbase.client.Admin;
//import org.apache.hadoop.hbase.client.ColumnFamilyDescriptorBuilder;
//import org.apache.hadoop.hbase.client.Connection;
//import org.apache.hadoop.hbase.client.ConnectionFactory;
//import org.apache.hadoop.hbase.client.Put;
//import org.apache.hadoop.hbase.client.Result;
//import org.apache.hadoop.hbase.client.ResultScanner;
//import org.apache.hadoop.hbase.client.Scan;
//import org.apache.hadoop.hbase.client.Table;
//import org.apache.hadoop.hbase.client.TableDescriptorBuilder;
//import ru.table.dto.TableDdl;
//
//import java.io.IOException;
//
//@Slf4j
//public class HBaseClientV2 {
//  private final Connection connection;
//
//  public HBaseClientV2(Configuration configuration) throws Exception {
//    log.info("hbaseclient connection...");
//    connection = ConnectionFactory.createConnection(configuration);
//    log.info("hbaseclient connection... complete");
//  }
//
//  public void createTableIfNotExists(final TableDdl tableDdl) throws IOException {
//    try (Admin admin = connection.getAdmin()) {
//      final String name = tableDdl.getTableName();
//      final TableName tableName = TableName.valueOf(name);
//      if (admin.tableExists(tableName)) {
//        log.info("Table [" + tableName + "] is already existed.");
//      } else {
//        TableDescriptorBuilder builder = TableDescriptorBuilder.newBuilder(tableName);
//        for (String cf : tableDdl.getCf()) {
//          log.info("CF: " + cf);
//          builder.setColumnFamily(ColumnFamilyDescriptorBuilder.of(cf));
//        }
//
//        log.info("Creating a new table... ");
//        admin.createTable(builder.build());
//        log.info("Done.");
//      }
//    }
//  }
//
//
//  public void put(String tableName, Put put) throws IOException {
//    try (Table table = connection.getTable(TableName.valueOf(tableName))) {
//      table.put(put);
//    }
//  }
//
//  public void readAll(String tableName) throws Exception {
//    log.info("read all");
//    final ResultScanner resultScanner = connection.getTable(TableName.valueOf(tableName))
//                                                  .getScanner(new Scan());
//    for (Result result : resultScanner) {
//      log.info("---");
//      log.info("readAll1 {}", result.toString());
//      log.info("readAll2 {}", new String(result.getRow()));
//      log.info("readAll3 {}", new String(result.value()));
//      log.info("---");
//    }
//
//    log.info("read all - complete");
//  }
//
//  public void countAll(String tableName) throws Exception {
//    final ResultScanner resultScanner = connection.getTable(TableName.valueOf(tableName))
//                                                  .getScanner(new Scan());
//    log.info("counting...");
//    int count = 0;
//    for (Result result : resultScanner) {
//      count++;
//    }
//    log.info("rows num: {}", count);
//  }
//}
