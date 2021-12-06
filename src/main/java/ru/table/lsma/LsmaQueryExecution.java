package ru.table.lsma;

import ru.table.dto.TableDdl;

public class LsmaQueryExecution {
  public static final String TABLE_NAME = "LSMA_QueryExecution";

  public static final String TABLE__CF1 = "CF1";

  public static final String TABLE__CF1__KIND_MEASURE_ID = "KindMeasureID";
  public static final String TABLE__CF1__STATE = "State";
  public static final String TABLE__CF1__DATE_BEGIN = "DateBegin";
  public static final String TABLE__CF1__DATE_END = "DateEnd";

  public static TableDdl getDdl() {
    return new TableDdl(TABLE_NAME,
                        new String[] {TABLE__CF1});
  }

}
