package ru.table.lsfc;

import ru.table.dto.TableDdl;

public class LsfcInputData {
  public static final String TABLE_NAME = "LSFC_InputData";

  public static final String TABLE__CF1 = "CF1";

  public static final String TABLE__CF1__CALC_INPUT_PARAMETER_ID = "CalcInputParameterID";
  public static final String TABLE__CF1__TRIGGER_EVENT_ID = "TriggerEventID";
  public static final String TABLE__CF1__VERSION_CONTEXT_ID = "VersionContextID";
  public static final String TABLE__CF1__EXECUTION_ID = "ExecutionID";
  public static final String TABLE__CF1__OUTPUT_DATA_ID = "OutputDataID";
  public static final String TABLE__CF1__VALUE = "Value";

  public static TableDdl getDdl() {
    return new TableDdl(TABLE_NAME,
                        new String[] {TABLE__CF1});
  }

}
