//package ru.table.lsfc;
//
//import lombok.Getter;
//import lombok.Setter;
//import org.apache.hadoop.hbase.client.Put;
//import org.apache.hadoop.hbase.util.Bytes;
//import ru.entity.Entity;
//import ru.table.dto.Row;
//import ru.table.dto.TableDdl;
//
///**
// * Таблица LSFC_ExecutionChain предназначена для хранения сведений об
// * экземплярах цепочек расчетов в рамках совокупности экземпляра меры и вида МСЗ.
// */
//@Getter
//@Setter
//public final class LsfcExecutionChain implements Entity {
//  public static final String TABLE_NAME = "LSFC_ExecutionChain";
//  public static final String CF_1 = "CF1";
//  public static final TableDdl TABLE_DDL = new TableDdl(TABLE_NAME, new String[] {CF_1});
//
//  private static final String CF_1_KIND_MEASURE_ID = "KindMeasureID";
//  private static final String CF_1_MEASURE_ID = "MeasureID";
//  private static final String CF_1_CONTEXT_ID = "ContextID";
//
//  private final Long id;
//  private final Row<Long> kindMeasureId;
//  private final Row<Long> measureId;
//  private final Row<Long> contextId;
//
//  public LsfcExecutionChain(final Long id,
//                            final Long kindMeasureId,
//                            final Long measureId,
//                            final Long contextId) {
//    this.id = id;
//    this.kindMeasureId
//        = new Row<>(CF_1, CF_1_KIND_MEASURE_ID,
//                    kindMeasureId);
//    this.measureId
//        = new Row<>(CF_1, CF_1_MEASURE_ID,
//                    measureId);
//    this.contextId
//        = new Row<>(CF_1, CF_1_CONTEXT_ID,
//                    contextId);
//  }
//
//  @Override
//  public Put toPut() {
//    final Put put = new Put(Bytes.toBytes(id));
//    kindMeasureId.addColumn(put);
//    measureId.addColumn(put);
//    contextId.addColumn(put);
//    return put;
//  }
//}
