package ru.table.lsfc;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import ru.entity.Entity;

@Data
@Accessors(chain = true)
public class LsfcOutputDataDto implements Entity {
  private long id;
  private long executionId;
  private long calcOutputParameterId;
  private boolean result;
  private short state;
  private String message;

  public Put toPut() {
    final Put put = new Put(Bytes.toBytes(id));
    put.addColumn(LsfcOutputData.TABLE__CF1.getBytes(),
                  LsfcOutputData.TABLE__CF1__EXECUTION_ID.getBytes(),
                  Bytes.toBytes(executionId));

    put.addColumn(LsfcOutputData.TABLE__CF1.getBytes(),
                  LsfcOutputData.TABLE__CF1__CALC_OUTPUT_PARAMETER_ID.getBytes(),
                  Bytes.toBytes(calcOutputParameterId));

    put.addColumn(LsfcOutputData.TABLE__CF1.getBytes(),
                  LsfcOutputData.TABLE__CF1__RESULT.getBytes(),
                  Bytes.toBytes(result));

    put.addColumn(LsfcOutputData.TABLE__CF1.getBytes(),
                  LsfcOutputData.TABLE__CF1__STATE.getBytes(),
                  Bytes.toBytes(state));

    put.addColumn(LsfcOutputData.TABLE__CF1.getBytes(),
                  LsfcOutputData.TABLE__CF1__MESSAGE.getBytes(),
                  Bytes.toBytes(message));
    return put;
  }
}
