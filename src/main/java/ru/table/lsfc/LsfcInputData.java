package ru.table.lsfc;

import lombok.Getter;
import lombok.Setter;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import ru.entity.Entity;
import ru.table.dto.Row;
import ru.table.dto.TableDdl;


/**
 * Таблица LSFC_InputData предназначена для хранения сведений о данных,
 * необходимых для фактического расчета в узле.
 */
@Getter
@Setter
public final class LsfcInputData implements Entity {
  public static final String TABLE_NAME = "LSFC_InputData";
  public static final String CF_1 = "CF1";
  public static final TableDdl TABLE_DDL = new TableDdl(TABLE_NAME, new String[] {CF_1});

  private static final String CF_1_CALC_INPUT_PARAMETER_ID = "CalcInputParameterID";
  private static final String CF_1_TRIGGER_EVENT_ID = "TriggerEventID";
  private static final String CF_1_VERSION_CONTEXT_ID = "VersionContextID";
  private static final String CF_1_EXECUTION_ID = "ExecutionID";
  private static final String CF_1_OUTPUT_DATA_ID = "OutputDataID";
  private static final String CF_1_VALUE = "Value";

  private final Long id;
  private final Row<Long> calcInputParameterId;
  private final Row<Long> triggerEventId;
  private final Row<Long> versionContextId;
  private final Row<Long> executionId;
  private final Row<Long> outputDataId;
  private final Row<String> value;

  public LsfcInputData(final Long id,
                       final Long calcInputParameterId,
                       final Long triggerEventId,
                       final Long versionContextId,
                       final Long executionId,
                       final Long outputDataId,
                       final String value) {
    this.id = id;
    this.calcInputParameterId
        = new Row<>(CF_1, CF_1_CALC_INPUT_PARAMETER_ID,
                    calcInputParameterId);
    this.triggerEventId
        = new Row<>(CF_1, CF_1_TRIGGER_EVENT_ID,
                    triggerEventId);
    this.versionContextId
        = new Row<>(CF_1, CF_1_VERSION_CONTEXT_ID,
                    versionContextId);
    this.executionId
        = new Row<>(CF_1, CF_1_EXECUTION_ID,
                    executionId);
    this.outputDataId
        = new Row<>(CF_1, CF_1_OUTPUT_DATA_ID,
                    outputDataId);
    this.value
        = new Row<>(CF_1, CF_1_VALUE,
                    value);
  }

  public Put toPut() {
    final Put put = new Put(Bytes.toBytes(id));
    calcInputParameterId.addColumn(put);
    triggerEventId.addColumn(put);
    versionContextId.addColumn(put);
    executionId.addColumn(put);
    outputDataId.addColumn(put);
    value.addColumn(put);
    return put;
  }
}
