package ru.test.mock.hbase;

import lombok.Data;
import ru.test.mock.bz.BzMszStageParam;

@Data
public class MszStageParam {
  private String id;
  private MszStage mszStage;
  private BzMszStageParam bzMszStageParam;
  private Object value;
}
