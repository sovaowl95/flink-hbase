package ru.table.lsfc;

import ru.table.dto.TableDdl;

public final class LsfcExecutionChain {
  public static final String TABLE_NAME = "LSFC_ExecutionChain";

  public static final String TABLE__CF1 = "CF1";

  public static final String TABLE__CF1__KIND_MEASURE_ID = "KindMeasureID";
  public static final String TABLE__CF1__MEASURE_ID = "MeasureID";
  public static final String TABLE__CF1__CONTEXT_ID = "ContextID";

  public static TableDdl getDdl() {
    return new TableDdl(TABLE_NAME,
                        new String[] {TABLE__CF1});
  }
}
