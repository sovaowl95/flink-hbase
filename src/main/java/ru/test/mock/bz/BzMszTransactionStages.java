package ru.test.mock.bz;

import lombok.Data;

@Data
public class BzMszTransactionStages {
  private String outputId; //key
  private String fromBzMszStageId;
  private String toBzMszStageId;
}
