package ru.test.service.bz;

import ecp.zhs.Output;
import ru.test.mock.Constants;
import ru.test.mock.bz.BzMszTransactionStages;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Optional;

public class BzMszTransactionStagesService {
  //todo: mock. replace with REST
  private final ArrayList<BzMszTransactionStages> mock = new ArrayList<>();

  public BzMszTransactionStagesService() {
    BzMszTransactionStages el;

    //null -> назначена
    el = new BzMszTransactionStages();
    el.setFromBzMszStageId(Constants.BzMszTransactionStages_Null);
    el.setToBzMszStageId(Constants.BzMszTransactionStages_Appointed);
    el.setOutputId(Constants.OUTPUT_ID_1);
    mock.add(el);

    //назначена -> приостановлена
    el = new BzMszTransactionStages();
    el.setFromBzMszStageId(Constants.BzMszTransactionStages_Appointed);
    el.setToBzMszStageId(Constants.BzMszTransactionStages_Stopped);
    el.setOutputId(Constants.OUTPUT_ID_2);
    mock.add(el);

    //приостановлен -> отменена
    el = new BzMszTransactionStages();
    el.setFromBzMszStageId(Constants.BzMszTransactionStages_Stopped);
    el.setToBzMszStageId(Constants.BzMszTransactionStages_Canceled);
    el.setOutputId(Constants.OUTPUT_ID_3);
    mock.add(el);
  }

  @Nonnull
  public Optional<BzMszTransactionStages> findByOutput(Output output) {
    final String outputId = output.getOutputId();

    for (BzMszTransactionStages bzMszTransactionStages : mock) {
      if (bzMszTransactionStages.getOutputId().equals(outputId)) {
        return Optional.of(bzMszTransactionStages);
      }
    }

    return Optional.empty();
  }
}
