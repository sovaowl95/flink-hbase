package ru.table.lsts;

import lombok.experimental.UtilityClass;
import ru.table.dto.TableDdl;

@UtilityClass
public class LstsTimerExecution {
  public static final String TABLE_NAME = "LSTS_TimerExecution";

  public static final String TABLE__CF1 = "CF1";
  public static final String TABLE__CF1__ID = "ID";
  public static final String TABLE__CF1__TIMER_SCHEDULE_ID = "TimerScheduleID";
  public static final String TABLE__CF1__EXECUTION_DATE = "ExecutionDate";
  public static final String TABLE__CF1__EXECUTION_STATE = "ExecutionState";

  public static TableDdl getDdl() {
    return new TableDdl(TABLE_NAME,
                        new String[] {TABLE__CF1});
  }

}
