package ru.test.mock.bz;

import lombok.Data;

@Data
public class BzMszTransactionStages {
  private BzMszStage from;
  private BzMszStage to;
  private String outputId;
}
