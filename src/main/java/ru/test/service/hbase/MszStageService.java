package ru.test.service.hbase;

import ru.test.mock.bz.BzMszStage;
import ru.test.mock.bz.BzMszTransactionStages;
import ru.test.mock.hbase.Msz;
import ru.test.mock.hbase.MszStage;

import java.util.UUID;

public class MszStageService {
  public MszStage findByMsz(Msz msz) {
    return null;
  }

  public void save(MszStage mszStage) {
    //todo:
  }

  public MszStage createMszStage(final BzMszTransactionStages bzMszTransactionStages,
                                 final BzMszStage bzMszStage,
                                 final Msz msz) {
    final MszStage mszStage = new MszStage();
    mszStage.setId(UUID.randomUUID().toString());
    mszStage.setMsz(msz);
    mszStage.setBzMszTransactionStages(bzMszTransactionStages);
    mszStage.setBzMszStage(bzMszStage);
    save(mszStage);
    return mszStage;
  }
}
