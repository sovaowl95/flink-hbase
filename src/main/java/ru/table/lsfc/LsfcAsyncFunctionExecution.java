package ru.table.lsfc;

import ru.table.dto.TableDdl;

public class LsfcAsyncFunctionExecution {
  public static final String TABLE_NAME = "LSFC_AsyncFunctionExecution";

  public static final String TABLE__CF1 = "CF1";

  public static final String TABLE__CF1__EXECUTION_ID = "ExecutionID";
  public static final String TABLE__CF1__Date = "Date";
  public static final String TABLE__CF1__STATE = "State";

  public static TableDdl getDdl() {
    return new TableDdl(TABLE_NAME,
                        new String[] {TABLE__CF1});
  }
}
