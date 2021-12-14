package ru.test.tasks;

import ecp.zhs.Output;
import lombok.RequiredArgsConstructor;
import ru.test.mock.bz.BzMsz;
import ru.test.mock.bz.BzMszStage;
import ru.test.mock.bz.BzMszTransactionStages;
import ru.test.mock.hbase.Msz;
import ru.test.mock.hbase.MszStage;
import ru.test.mock.hbase.MszStageParam;
import ru.test.service.bz.BzMszService;
import ru.test.service.bz.BzMszStageService;
import ru.test.service.bz.BzMszTransactionStagesService;
import ru.test.service.hbase.MszService;
import ru.test.service.hbase.MszStageParamService;
import ru.test.service.hbase.MszStageService;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class TransactionTask {
  private final BzMszService bzMszService;
  private final BzMszStageService bzMszStageService;
  private final BzMszTransactionStagesService bzMszTransactionStagesService;

  private final MszService mszService;
  private final MszStageService mszStageService;
  private final MszStageParamService mszStageParamService;

  public void execute(final Output output) {
    final String outputId = output.getOutputId();
    final String personId = ""; //todo:
    final Object value = output.getValue();
    final Map<String, String> keyValueMap; //todo: extract from output

    //todo: if (result == true)

    final Optional<BzMszTransactionStages> bzMszTransactionStagesOptional
        = bzMszTransactionStagesService.findByOutputId(outputId);
    if (bzMszTransactionStagesOptional.isEmpty()) {
      return; //todo: обновить конфигурацию и повторить?
    }

    final BzMszTransactionStages bzMszTransactionStages = bzMszTransactionStagesOptional.get();

    final BzMszStage bzMszStage = bzMszStageService.findByTransactionId(bzMszTransactionStages);
    //todo: странный переход. уточнить. правильно ли я понял
    final BzMsz bzMsz = bzMszService.findBy(bzMszStage.getBzMsz());

    final Optional<Msz> mszOptional = mszService.findByMszAndPerson(bzMsz, personId);
    final Msz msz = mszOptional.get();

    final Optional<MszStage> mszStageOptional = mszStageService.todo();

    final BzMszStage from = bzMszTransactionStages.getFrom();

    //todo: очень кривая логика. нужно что-то делать с этим. кажется нужно менять блок-схему
    //not yet created
    if (from == null) {
      if (mszStageOptional.isPresent()) {
        throw new RuntimeException("current stage not equal stage in db");
      } else {
        //todo: create
      }
    } else {
      //created
      final MszStage mszStage = mszStageOptional.orElseThrow(() -> new RuntimeException(""));
      final BzMszStage currentBzMszStage = mszStage.getBzMszStage();

      if (!from.equals(currentBzMszStage)) {
        throw new RuntimeException("stages not equals");
      }
    }


    final MszStage mszStage = new MszStage();
    mszStage.setId(UUID.randomUUID().toString());
    mszStage.setMsz(msz);
    mszStage.setBzMszTransactionStages(bzMszTransactionStages);
    mszStage.setBzMszStage(bzMszStage);
    mszStageService.save(mszStage);


    var list = new ArrayList<MszStageParam>();
    for (Map.Entry<String, String> entry : keyValueMap.entrySet()) {
      var bzMszStageParam = bzMszStageService.findByKey(entry.getKey());

      final var mszStageParam = new MszStageParam();
      mszStageParam.setId(UUID.randomUUID().toString());
      mszStageParam.setMszStage(mszStage);
      mszStageParam.setBzMszStageParam(bzMszStageParam);
      mszStageParam.setValue(entry.getValue());

      list.add(mszStageParam);
    }
    mszStageParamService.saveAll(list);
  }
}
