package ru.test2.mock.bz;

import lombok.Data;

@Data
public class BzMszStageParam {
  private String id;
  private String outputId;//key
  private String bzMszStageId;
  private String title;

  private String type;
}
