package ru.table;

public final class HvacTable {
  public static final String TABLE_NAME = "hvac";

  //todo: enum
  public static final String CF_FIRST = "columnFamily1";
  public static final String CF_SECOND = "columnFamily2";
  public static final String CF_THIRD = "columnFamily3";


  public static String[] getColumnFamilies() {
    return new String[] {CF_FIRST, CF_SECOND, CF_THIRD};
  }
}
