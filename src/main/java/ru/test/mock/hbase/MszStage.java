package ru.test.mock.hbase;

import lombok.Data;
import ru.test.mock.bz.BzMszStage;
import ru.test.mock.bz.BzMszTransactionStages;

@Data
public class MszStage {
  private String id;
  private Msz msz;
  private BzMszTransactionStages bzMszTransactionStages;
  private BzMszStage bzMszStage;
}
