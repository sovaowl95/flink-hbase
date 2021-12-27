//package ru.table.dto;
//
//import lombok.Getter;
//import org.apache.commons.lang3.SerializationUtils;
//import org.apache.hadoop.hbase.client.Put;
//
//import java.io.Serializable;
//
//@Getter
//public class Row<V extends Serializable> {
//  private final String columnFamily;
//  private final String qualifier;
//  private final V value;
//
//  public Row(final String columnFamily,
//             final String qualifier,
//             final V value) {
//    this.columnFamily = columnFamily;
//    this.qualifier = qualifier;
//    this.value = value;
//  }
//
//  public void addColumn(Put put) {
//    put.addColumn(columnFamily.getBytes(),
//                  qualifier.getBytes(),
//                  SerializationUtils.serialize(value));
//  }
//}
