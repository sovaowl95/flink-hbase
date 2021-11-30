package ru.table;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class MockTable {
  public static final String TABLE_NAME = "mock_table";

  @RequiredArgsConstructor
  @Getter
  public enum ColumnFamilies {
    CF_FIRST("mock_table_column_family_1"),
    CF_SECOND("mock_table_column_family_2"),
    CF_THIRD("mock_table_column_family_3"),
    ;

    private final String name;

    public static String[] getColumnFamilies() {
      final MockTable.ColumnFamilies[] values = MockTable.ColumnFamilies.values();
      final String[] result = new String[values.length];
      for (int i = 0; i < values.length; i++) {
        result[i] = values[i].getName();
      }
      return result;
    }
  }
}
