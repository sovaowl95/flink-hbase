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
//import java.sql.Timestamp;
//
///**
// * Таблица LSFC_Execution предназначена для хранения сведений о фактах расчетов в конкретном узле в рамках
// * конкретного расчета на конкретных входных параметрах.
// */
//@Getter
//@Setter
//public final class LsfcExecution implements Entity {
//  public static final String TABLE_NAME = "LSFC_Execution";
//  public static final String CF_1 = "CF1";
//  public static final TableDdl TABLE_DDL = new TableDdl(TABLE_NAME, new String[] {CF_1});
//
//  private static final String CF_1_ALGORITHM_ID = "AlgorithmID";
//  private static final String CF_1_EXECUTION_CHAIN_ID = "ExecutionChainID";
//  private static final String CF_1_EXECUTION_DATE = "ExecutionDate";
//  private static final String CF_1_ERROR_MESSAGE = "ErrorMessage";
//
//  private final Long id;
//  private final Row<Long> algorithmId;
//  private final Row<Long> executionChainId;
//  private final Row<Timestamp> executionDate; //todo: как хранить время?
//  private final Row<String> errorMessage;
//
//  public LsfcExecution(final Long id,
//                       final Long algorithmId,
//                       final Long executionChainId,
//                       final Timestamp executionDate,
//                       final String errorMessage) {
//    this.id = id;
//    this.algorithmId
//        = new Row<>(CF_1, CF_1_ALGORITHM_ID,
//                    algorithmId);
//    this.executionChainId
//        = new Row<>(CF_1, CF_1_EXECUTION_CHAIN_ID,
//                    executionChainId);
//    this.executionDate
//        = new Row<>(CF_1, CF_1_EXECUTION_DATE,
//                    executionDate);
//    this.errorMessage
//        = new Row<>(CF_1, CF_1_ERROR_MESSAGE,
//                    errorMessage);
//  }
//
//  @Override
//  public Put toPut() {
//    final Put put = new Put(Bytes.toBytes(id));
//    algorithmId.addColumn(put);
//    executionChainId.addColumn(put);
//    executionDate.addColumn(put);
//    errorMessage.addColumn(put);
//    return put;
//  }
//}
