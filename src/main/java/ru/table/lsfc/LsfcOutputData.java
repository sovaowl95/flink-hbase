package ru.table.lsfc;

import lombok.Getter;
import lombok.Setter;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import ru.entity.Entity;
import ru.table.dto.Row;
import ru.table.dto.TableDdl;

/**
 * Таблица LSFC_OutputData предназначена для хранения сведений о
 * результатах выполнения фактического расчета в узлах.
 */
@Getter
@Setter
public final class LsfcOutputData implements Entity {
  public static final String TABLE_NAME = "LSFC_OutputData";
  public static final String CF_1 = "CF1";
  public static final TableDdl TABLE_DDL = new TableDdl(TABLE_NAME, new String[] {CF_1});

  private static final String CF_1_EXECUTION_ID = "ExecutionID";
  private static final String CF_1_CALC_OUTPUT_PARAMETER_ID = "CalcOutputParameterID";
  private static final String CF_1_RESULT = "Result";
  private static final String CF_1_STATE = "State";
  private static final String CF_1_MESSAGE = "Message";

  private final String id;
  private final Row<Long> executionId;
  private final Row<Long> calcOutputParameterId;
  private final Row<Boolean> result;
  private final Row<Byte> state;
  private final Row<String> message;

  public LsfcOutputData(final String id,
                        final Long executionId,
                        final Long calcOutputParameterId,
                        final Boolean result,
                        final Byte state,
                        final String message) {
    this.id = id;
    this.executionId
        = new Row<>(CF_1, CF_1_EXECUTION_ID,
                    executionId);
    this.calcOutputParameterId
        = new Row<>(CF_1, CF_1_CALC_OUTPUT_PARAMETER_ID,
                    calcOutputParameterId);
    this.result
        = new Row<>(CF_1, CF_1_RESULT,
                    result);
    this.state
        = new Row<>(CF_1, CF_1_STATE,
                    state);
    this.message
        = new Row<>(CF_1, CF_1_MESSAGE,
                    message);
  }

  public Put toPut() {
    final Put put = new Put(Bytes.toBytes(id));
    executionId.addColumn(put);
    calcOutputParameterId.addColumn(put);
    result.addColumn(put);
    state.addColumn(put);
    message.addColumn(put);
    return put;
  }
}
