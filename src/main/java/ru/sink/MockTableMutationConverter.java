//package ru.sink;
//
//import org.apache.flink.connector.hbase.sink.HBaseMutationConverter;
//import org.apache.hadoop.hbase.client.Mutation;
//import org.apache.hadoop.hbase.client.Put;
//import ru.entity.MockEntity;
//import ru.table.MockTable;
//import ru.task.MockTask;
//
//import java.math.BigInteger;
//import java.util.Random;
//
//public class MockTableMutationConverter implements HBaseMutationConverter<MockTask> {
//
//  private static final Random RANDOM = new Random();
//
//  private static final byte[] BYTES_TRUE = {1};
//  private static final byte[] BYTES_FALSE = {0};
//
//  @Override
//  public void open() {
//    //ignore
//  }
//
//  @Override
//  public Mutation convertToMutation(MockTask mockTask) {
//    final MockEntity mockEntity = convert(mockTask);
//
//    final Put put = new Put(mockEntity.getId().getBytes());
//    put.addColumn(MockTable.ColumnFamilies.CF_FIRST.getName().getBytes(),
//                  "id".getBytes(),
//                  mockEntity.getId().getBytes());
//    put.addColumn(MockTable.ColumnFamilies.CF_FIRST.getName().getBytes(),
//                  "fio".getBytes(),
//                  mockEntity.getFio().getBytes());
//    put.addColumn(MockTable.ColumnFamilies.CF_FIRST.getName().getBytes(),
//                  "someString".getBytes(),
//                  mockEntity.getSomeString().getBytes());
//    put.addColumn(MockTable.ColumnFamilies.CF_SECOND.getName().getBytes(),
//                  "age".getBytes(),
//                  BigInteger.valueOf(mockEntity.getAge()).toByteArray());
//    put.addColumn(MockTable.ColumnFamilies.CF_SECOND.getName().getBytes(),
//                  "adult".getBytes(),
//                  Boolean.TRUE.equals(mockEntity.getAdult()) ? BYTES_TRUE : BYTES_FALSE);
//    return put;
//  }
//
//  private MockEntity convert(MockTask mockTask) {
//    final MockEntity mockEntity = new MockEntity();
//    mockEntity.setId(String.valueOf(mockTask.getId()));
//    mockEntity.setFio("someFio");
//    mockEntity.setSomeString(mockTask.getSomeString());
//    mockEntity.setAge((long) RANDOM.nextInt(20));
//    mockEntity.setAdult(mockEntity.getAge() > 18);
//
//    return mockEntity;
//  }
//}
