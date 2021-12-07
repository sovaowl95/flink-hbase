package ru.table.lsfc;

import lombok.Getter;
import lombok.Setter;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import ru.entity.Entity;
import ru.table.dto.Row;
import ru.table.dto.TableDdl;

import java.sql.Timestamp;

/**
 * Таблица LSFC_AsyncFunctionExecution предназначена для хранения сведений об
 * истории статусов вызова асинхронных функций (в том числе межведомственных запросов).
 */
@Getter
@Setter
public final class LsfcAsyncFunctionExecution implements Entity {
  public static final String TABLE_NAME = "LSFC_AsyncFunctionExecution";
  public static final String CF_1 = "CF1";
  public static final TableDdl TABLE_DDL = new TableDdl(TABLE_NAME, new String[] {CF_1});

  private static final String CF_1_EXECUTION_ID = "ExecutionID";
  private static final String CF_1_DATE = "Date";
  private static final String CF_1_STATE = "State";

  private final Long id;
  private final Row<Long> executionID;
  private final Row<Timestamp> date;
  private final Row<Byte> state;

  public LsfcAsyncFunctionExecution(final Long id,
                                    final Long executionId,
                                    final Timestamp date,
                                    final Byte state) {
    this.id = id;
    this.executionID
        = new Row<>(CF_1, CF_1_EXECUTION_ID,
                    executionId);
    this.date
        = new Row<>(CF_1, CF_1_EXECUTION_ID,
                    date);
    this.state
        = new Row<>(CF_1, CF_1_STATE,
                    state);
  }


  @Override
  public Put toPut() {
    final Put put = new Put(Bytes.toBytes(id));
    executionID.addColumn(put);
    date.addColumn(put);
    state.addColumn(put);
    return put;
  }
}
