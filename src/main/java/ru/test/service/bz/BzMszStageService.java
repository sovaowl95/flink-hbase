package ru.test.service.bz;

import ru.test.mock.Constants;
import ru.test.mock.bz.BzMszStage;
import ru.test.mock.bz.BzMszStageParam;
import ru.test.mock.bz.BzMszTransactionStages;

import java.util.ArrayList;
import java.util.Optional;

public class BzMszStageService {
  private final ArrayList<BzMszStage> mock = new ArrayList<>();

  public BzMszStageService() {
    BzMszStage el;

    el = new BzMszStage();
    el.setId(Constants.BzMszStage_ID_1);
    el.setBzMszId(Constants.BzMsz_ID_1);
    mock.add(el);

    el = new BzMszStage();
    el.setId(Constants.BzMszStage_ID_2);
    el.setBzMszId(Constants.BzMsz_ID_2);
    mock.add(el);

    el = new BzMszStage();
    el.setId(Constants.BzMszStage_ID_3);
    el.setBzMszId(Constants.BzMsz_ID_3);
    mock.add(el);
  }

  public Optional<BzMszStage> findByBzMszStageId(BzMszStageParam bzMszStageParam) {
    final String targetId = bzMszStageParam.getBzMszStageId();

    for (BzMszStage bzMszStage : mock) {
      if (bzMszStage.getId().equals(targetId)) {
        return Optional.of(bzMszStage);
      }
    }
    return Optional.empty();
  }

  public Optional<BzMszStage> findByBzMszTransactionStages(BzMszTransactionStages bzMszTransactionStages) {
    final String targetId = bzMszTransactionStages.getFromBzMszStageId();

    for (BzMszStage bzMszStage : mock) {
      if (bzMszStage.getId().equals(targetId)) {
        return Optional.of(bzMszStage);
      }
    }
    return Optional.empty();
  }
}
