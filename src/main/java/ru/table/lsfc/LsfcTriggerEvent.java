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
// * Таблица LSFC_TriggerEvent предназначена для хранения сведений о событии,
// * инициировавшим запуск расчета.
// */
//@Getter
//@Setter
//public final class LsfcTriggerEvent implements Entity {
//  public static final String TABLE_NAME = "LSFC_TriggerEvent";
//  public static final String CF_1 = "CF1";
//  public static final TableDdl TABLE_DDL = new TableDdl(TABLE_NAME, new String[] {CF_1});
//
//  private static final String CF_1_TIMER_ID = "TimerID";
//  private static final String CF_1_VERSION_CONTEXT_ID = "VersionContextID";
//  private static final String CF_1_CHANGED_DATA = "ChangedData";
//
//  private final Long id;
//  private final Row<Long> timerId;
//  private final Row<Long> versionContextId;
//  private final Row<Long> changedData;
//
//  public LsfcTriggerEvent(final Long id,
//                          final Long timerId,
//                          final Long versionContextId,
//                          final Long changedData) {
//    this.id = id;
//    this.timerId
//        = new Row<>(CF_1, CF_1_TIMER_ID,
//                    timerId);
//    this.versionContextId
//        = new Row<>(CF_1, CF_1_VERSION_CONTEXT_ID,
//                    versionContextId);
//    this.changedData
//        = new Row<>(CF_1, CF_1_CHANGED_DATA,
//                    changedData);
//  }
//
//  @Override
//  public Put toPut() {
//    final Put put = new Put(Bytes.toBytes(id));
//    timerId.addColumn(put);
//    versionContextId.addColumn(put);
//    changedData.addColumn(put);
//    return put;
//  }
//}
