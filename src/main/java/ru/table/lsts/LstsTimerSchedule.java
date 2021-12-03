package ru.table.lsts;

import lombok.experimental.UtilityClass;
import ru.table.dto.TableDdl;

@UtilityClass
public class LstsTimerSchedule {
  public static final String TABLE_NAME = "LSTS_TimerSchedule";

  public static final String TABLE__CF1 = "CF1";
  public static final String TABLE__CF1__ID = "ID";
  public static final String TABLE__CF1__CONFIGURATION_ID = "ConfigurationID";
  public static final String TABLE__CF1__OBJECT_ID = "ObjectID";
  public static final String TABLE__CF1__SCHEDULE_DATE = "ScheduleDate";

  public static TableDdl getDdl() {
    return new TableDdl(TABLE_NAME,
                        new String[] {TABLE__CF1});
  }

}
