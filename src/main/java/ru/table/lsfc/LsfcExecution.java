package ru.table.lsfc;

import ru.table.dto.TableDdl;

public final class LsfcExecution {
  public static final String TABLE_NAME = "LSFC_Execution";

  public static final String TABLE__CF1 = "CF1";

  public static final String TABLE__CF1__ALGORITHM_ID = "AlgorithmID";
  public static final String TABLE__CF1__EXECUTION_CHAIN_ID = "ExecutionChainID";
  public static final String TABLE__CF1__EXECUTION_DATE = "ExecutionDate";
  public static final String TABLE__CF1__ERROR_MESSAGE = "ErrorMessage";

  public static TableDdl getDdl() {
    return new TableDdl(TABLE_NAME,
                        new String[] {TABLE__CF1});
  }
}
