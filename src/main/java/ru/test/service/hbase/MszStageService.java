package ru.test.service.hbase;

import ru.test.mock.Constants;
import ru.test.mock.bz.BzMszStage;
import ru.test.mock.bz.BzMszTransition;
import ru.test.mock.hbase.Msz;
import ru.test.mock.hbase.MszStage;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public class MszStageService {
  //todo: mock. replace with REST
  private final ArrayList<MszStage> mock = new ArrayList<>();

  public MszStageService() {
    MszStage mszStage;

    mszStage = new MszStage();
    mszStage.setId(Constants.MSZ_STAGE_ID_1);
    mszStage.setMszId(Constants.MSZ_ID_1);
    mszStage.setBzMszTransactionStagesId(Constants.BZ_MSZ_TRANSACTION_ID_1);
    mszStage.setBzMszStageId(Constants.BZ_MSZ_STAGE_ID_1);
    mock.add(mszStage);

    mszStage = new MszStage();
    mszStage.setId(Constants.MSZ_STAGE_ID_2);
    mszStage.setMszId(Constants.MSZ_ID_1);
    mszStage.setBzMszTransactionStagesId(Constants.BZ_MSZ_TRANSACTION_ID_2);
    mszStage.setBzMszStageId(Constants.BZ_MSZ_STAGE_ID_2);
    mock.add(mszStage);

    mszStage = new MszStage();
    mszStage.setId(Constants.MSZ_STAGE_ID_3);
    mszStage.setMszId(Constants.MSZ_ID_1);
    mszStage.setBzMszTransactionStagesId(Constants.BZ_MSZ_TRANSACTION_ID_3);
    mszStage.setBzMszStageId(Constants.BZ_MSZ_STAGE_ID_3);
    mock.add(mszStage);
  }

  public Optional<MszStage> findByMsz(Msz msz) {
    final String targetId = msz.getId();

    for (MszStage mszStage : mock) {
      if (mszStage.getMszId().equals(targetId)) {
        return Optional.of(mszStage);
      }
    }

    return Optional.empty();
  }

  //todo: refactoring
  public MszStage createMszStage(final BzMszTransition bzMszTransition,
                                 final BzMszStage bzMszStage,
                                 final Msz msz) {
    final MszStage mszStage = new MszStage();
    mszStage.setId(UUID.randomUUID().toString());
    mszStage.setMszId(msz.getId());
    mszStage.setBzMszTransactionStagesId(bzMszTransition.getId());
    mszStage.setBzMszStageId(bzMszStage.getId());
    return mszStage;
  }

  public void save(MszStage mszStage) {
    //todo:
  }

  public Optional<MszStage> findByMszOptional(Msz msz) {
    return null;
  }
}
