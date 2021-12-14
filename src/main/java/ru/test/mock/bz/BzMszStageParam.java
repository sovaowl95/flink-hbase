package ru.test.mock.bz;

import lombok.Data;

@Data
public class BzMszStageParam {
  private String id;
  private BzMszStage bzMszStage;
  private String outputId;
  private String name;
}
