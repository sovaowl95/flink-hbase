package ru.client;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptorBuilder;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.client.TableDescriptorBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
public class HBaseClientV2 {
  private Configuration configuration;
  private Connection connection;

  public void setUp() throws IOException {
    log.info("hbaseclient connection...");
    configuration = HBaseConfiguration.create();
    configuration.set("hbase.zookeeper.quorum", "localhost");
    configuration.set("hbase.zookeeper.property.clientPort", "2181");
    connection = ConnectionFactory.createConnection(configuration);
    log.info("hbaseclient connection... complete");
  }

  public void createTableIfNotExists(final String tableName,
                                     final String... columnFamilies) throws IOException {
    try (Admin admin = connection.getAdmin()) {
      TableName table = TableName.valueOf(tableName);
      if (admin.tableExists(table)) {
        System.out.println("Table [" + tableName + "] is already existed.");
        return;
      }

      TableDescriptorBuilder builder = TableDescriptorBuilder.newBuilder(table);
      for (String cf : columnFamilies) {
        System.out.println("CF: " + cf);
        builder.setColumnFamily(ColumnFamilyDescriptorBuilder.of(cf));
      }

      System.out.println("Creating a new table... ");
      admin.createTable(builder.build());
      System.out.println("Done.");
    }
  }

  public void write(String tableName, Put put) throws IOException {
    try (Table table = connection.getTable(TableName.valueOf(tableName))) {
      table.put(put);
    }
  }

  public void readAll(String tableName) throws Exception {
    TimeUnit.SECONDS.sleep(1);

    log.info("read all");
    final ResultScanner resultScanner = connection.getTable(TableName.valueOf(tableName))
                                                  .getScanner(new Scan());
    for (Result result : resultScanner) {
      log.info("---");
      log.info("readAll1 {}", result.toString());
      log.info("readAll2 {}", new String(result.getRow()));
      log.info("readAll3 {}", new String(result.value()));
      log.info("---");
    }

    log.info("read all - complete");
  }
}
