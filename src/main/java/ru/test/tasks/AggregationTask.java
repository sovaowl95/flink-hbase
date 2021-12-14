package ru.test.tasks;

import ecp.zhs.Output;

public class AggregationTask implements Runnable {
  private final Output output;

  public AggregationTask(Output output) {
    this.output = output;
  }

  @Override
  public void run() {
    final ParamChangeTask paramChangeTask = new ParamChangeTask();
    paramChangeTask.execute(output);

    final TransactionTask transactionTask = new TransactionTask();
    transactionTask.execute(output);
  }
}
