package ru.test.tasks;

import ecp.zhs.Output;
import ru.test.service.bz.BzMszService;
import ru.test.service.bz.BzMszStageParamService;
import ru.test.service.bz.BzMszStageService;
import ru.test.service.bz.BzMszTransitionStagesService;
import ru.test.service.hbase.MszService;
import ru.test.service.hbase.MszStageParamService;
import ru.test.service.hbase.MszStageService;

public class AggregationTask implements Runnable {
  private final Output output;

  public AggregationTask(Output output) {
    this.output = output;
  }

  @Override
  public void run() {
//    final BzMszService bzMszService = new BzMszService();
//    final BzMszStageService bzMszStageService = new BzMszStageService();
//    final BzMszStageParamService bzMszStageParamService = new BzMszStageParamService();
//    final BzMszTransitionStagesService bzMszTransitionStagesService = new BzMszTransitionStagesService();

    final MszService mszService = new MszService();
    final MszStageService mszStageService = new MszStageService();
    final MszStageParamService mszStageParamService = new MszStageParamService();

//    final ParamChangeTask paramChangeTask
//        = new ParamChangeTask(bzMszService, bzMszStageService, bzMszStageParamService,
//                              mszService, mszStageService, mszStageParamService);
//    paramChangeTask.execute(output);
//
//    final TransactionTask transactionTask
//        = new TransactionTask(bzMszService, bzMszStageService, bzMszStageParamService, bzMszTransitionStagesService,
//                              mszService, mszStageService, mszStageParamService);
//    transactionTask.execute(output);
  }
}
