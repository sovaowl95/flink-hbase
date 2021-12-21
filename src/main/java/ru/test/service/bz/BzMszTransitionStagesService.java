package ru.test.service.bz;

import ecp.zhs.Output;
import ru.test.mock.Constants;
import ru.test.mock.bz.BzMszTransition;
import ru.test.mock.hbase.MszStage;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BzMszTransitionStagesService {
  //todo: mock. replace with REST
  private final ArrayList<BzMszTransition> mock = new ArrayList<>();

  public BzMszTransitionStagesService() {
    BzMszTransition el;

    //null -> назначена
    el = new BzMszTransition();
    el.setId(Constants.BZ_MSZ_TRANSACTION_ID_1);
    el.setFromBzMszStageId(Constants.BzMszTransactionStages_Null);
    el.setToBzMszStageId(Constants.BzMszTransactionStages_Appointed);
    el.setOutputId(Constants.OUTPUT_ID_1);
    mock.add(el);

    //назначена -> приостановлена
    el = new BzMszTransition();
    el.setId(Constants.BZ_MSZ_TRANSACTION_ID_2);
    el.setFromBzMszStageId(Constants.BzMszTransactionStages_Appointed);
    el.setToBzMszStageId(Constants.BzMszTransactionStages_Stopped);
    el.setOutputId(Constants.OUTPUT_ID_2);
    mock.add(el);

    //приостановлен -> отменена
    el = new BzMszTransition();
    el.setId(Constants.BZ_MSZ_TRANSACTION_ID_3);
    el.setFromBzMszStageId(Constants.BzMszTransactionStages_Stopped);
    el.setToBzMszStageId(Constants.BzMszTransactionStages_Canceled);
    el.setOutputId(Constants.OUTPUT_ID_3);
    mock.add(el);
  }

  @Nonnull
  public Optional<BzMszTransition> findByOutput(Output output) {
    final String outputId = output.getOutputId();

    for (BzMszTransition bzMszTransition : mock) {
      if (bzMszTransition.getOutputId().equals(outputId)) {
        return Optional.of(bzMszTransition);
      }
    }

    return Optional.empty();
  }

  public List<BzMszTransition> findByMszStage(MszStage mszStage) {
    return null; //todo:
  }
}
