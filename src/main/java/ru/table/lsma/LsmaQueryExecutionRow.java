package ru.table.lsma;

import lombok.experimental.UtilityClass;
import ru.table.dto.TableDdl;

@UtilityClass
public class LsmaQueryExecutionRow {
  public static final String TABLE_NAME = "LSMA_QueryExecutionRow";

  public static final String TABLE__CF1 = "CF1";
  public static final String TABLE__CF1__ID = "ID";
  public static final String TABLE__CF1__RIGHT_ID = "RightID";
  public static final String TABLE__CF1__RECIPIENT_ID = "RecipientID";
  public static final String TABLE__CF1__QUERY_EXECUTION_ID = "QueryExecutionID";
  public static final String TABLE__CF1__EXECUTION_OUTPUT_DATA_ID = "ExecutionOutputDataID";

  public static TableDdl getDdl() {
    return new TableDdl(TABLE_NAME,
                        new String[] {TABLE__CF1});
  }

}
