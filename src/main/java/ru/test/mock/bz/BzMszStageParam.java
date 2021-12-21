package ru.test.mock.bz;

import lombok.Data;

@Data
public class BzMszStageParam {
  private String id;
  private String outputId;//key
  private String bzMszStageId;
  private String name;

  private String type;
}
