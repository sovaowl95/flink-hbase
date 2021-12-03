package ru.table.lsdm;

import lombok.experimental.UtilityClass;
import ru.table.dto.TableDdl;

@UtilityClass
public class LsdmTargetMeasureAttributes {
  public static final String TABLE_NAME = "LSDM_TargetMeasureAttributes";

  public static final String TABLE__CF1 = "CF1";
  public static final String TABLE__CF1__ID = "ID";
  public static final String TABLE__CF1__KIND_MEASURE_ID = "KindMeasureID";
  public static final String TABLE__CF1__RIGHT_ID = "RightID";
  public static final String TABLE__CF1__EXECUTION_OUTPUT_DATA_ID = "ExecutionOutputDataID";
  public static final String TABLE__CF1__VALUE = "Value";
  public static final String TABLE__CF1__MEASURE_ATTRIBUTE_TYPE_ID = "MeasureAttributeTypeID";

  public static TableDdl getDdl() {
    return new TableDdl(TABLE_NAME,
                        new String[] {TABLE__CF1});
  }
}
