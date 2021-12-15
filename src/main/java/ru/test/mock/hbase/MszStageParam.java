package ru.test.mock.hbase;

import lombok.Data;

@Data
public class MszStageParam {
  private String id;
  private String mszStageId;
  private String bzMszStageParamId;
  private Object value;
}
