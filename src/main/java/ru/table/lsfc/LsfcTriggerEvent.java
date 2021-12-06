package ru.table.lsfc;

import ru.table.dto.TableDdl;

public final class LsfcTriggerEvent {
  public static final String TABLE_NAME = "LSFC_TriggerEvent";

  public static final String TABLE__CF1 = "CF1";

  public static final String TABLE__CF1__TIMER_ID = "TimerID";
  public static final String TABLE__CF1__VERSION_CONTEXT_ID = "VersionContextID";
  public static final String TABLE__CF1__CHANGED_DATA = "ChangedData";

  public static TableDdl getDdl() {
    return new TableDdl(TABLE_NAME,
                        new String[] {TABLE__CF1});
  }
}
