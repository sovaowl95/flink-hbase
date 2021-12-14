package ru.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;

@Slf4j
public class Hbase {
  public static Configuration createConfiguration() {
    log.info("createConfiguration...");
    final Configuration configuration = HBaseConfiguration.create();
    configuration.set("hbase.zookeeper.quorum", "localhost");
    configuration.set("hbase.zookeeper.property.clientPort", "2181");

    log.info("createConfiguration... complete");
    return configuration;
  }
}
