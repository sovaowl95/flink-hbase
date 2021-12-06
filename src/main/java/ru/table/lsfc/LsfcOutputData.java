package ru.table.lsfc;

import ru.table.dto.TableDdl;

public class LsfcOutputData {
  public static final String TABLE_NAME = "LSFC_OutputData";

  public static final String TABLE__CF1 = "CF1";

  public static final String TABLE__CF1__EXECUTION_ID = "ExecutionID";
  public static final String TABLE__CF1__CALC_OUTPUT_PARAMETER_ID = "CalcOutputParameterID";
  public static final String TABLE__CF1__RESULT = "Result";
  public static final String TABLE__CF1__STATE = "State";
  public static final String TABLE__CF1__MESSAGE = "Message";

  public static TableDdl getDdl() {
    return new TableDdl(TABLE_NAME,
                        new String[] {TABLE__CF1});
  }
}
