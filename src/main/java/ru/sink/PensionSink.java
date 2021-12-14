//package ru.sink;
//
//import org.apache.flink.connector.hbase.sink.HBaseMutationConverter;
//import org.apache.flink.connector.hbase.sink.HBaseSinkFunction;
//import org.apache.hadoop.conf.Configuration;
//import ru.entity.MockEntity;
//
//public class PensionSink extends HBaseSinkFunction<MockEntity> {
//  public PensionSink(final String hTableName,
//                     final Configuration conf,
//                     final HBaseMutationConverter mutationConverter,
//                     final long bufferFlushMaxSizeInBytes,
//                     final long bufferFlushMaxMutations,
//                     final long bufferFlushIntervalMillis) {
//    super(hTableName,
//          conf,
//          mutationConverter,
//          bufferFlushMaxSizeInBytes,
//          bufferFlushMaxMutations,
//          bufferFlushIntervalMillis);
//  }
//
//}
