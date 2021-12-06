package ru.entity;

import org.apache.hadoop.hbase.client.Put;

public interface Entity {
  Put toPut();
}
