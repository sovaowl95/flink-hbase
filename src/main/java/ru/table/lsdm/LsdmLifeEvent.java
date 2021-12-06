package ru.table.lsdm;

import ru.table.dto.TableDdl;

public class LsdmLifeEvent {
  public static final String TABLE_NAME = "LSDM_LifeEvent";

  public static final String TABLE__CF1 = "CF1";

  public static final String TABLE__CF1__RECIPIENT_ID = "RecipientID";
  public static final String TABLE__CF1__EXECUTION_OUTPUT_DATA_ID = "ExecutionOutputDataID";
  public static final String TABLE__CF1__LIFE_EVENT_TYPE_ID = "LifeEventTypeID";
  public static final String TABLE__CF1__DATE = "Date";

  public static TableDdl getDdl() {
    return new TableDdl(TABLE_NAME,
                        new String[] {TABLE__CF1});
  }
}
