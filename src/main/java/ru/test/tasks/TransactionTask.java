package ru.test.tasks;

import ecp.zhs.Output;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.test.mock.bz.BzMsz;
import ru.test.mock.bz.BzMszStage;
import ru.test.mock.bz.BzMszTransactionStages;
import ru.test.mock.hbase.Msz;
import ru.test.mock.hbase.MszStage;
import ru.test.mock.hbase.MszStageParam;
import ru.test.service.bz.BzMszService;
import ru.test.service.bz.BzMszStageParamService;
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
@Slf4j
public class TransactionTask {
  private final BzMszService bzMszService;
  private final BzMszStageService bzMszStageService;
  private final BzMszStageParamService bzMszStageParamService;
  private final BzMszTransactionStagesService bzMszTransactionStagesService;

  private final MszService mszService;
  private final MszStageService mszStageService;
  private final MszStageParamService mszStageParamService;

  public void execute(final Output output) {
    final String personId = ""; //todo:
//    final Object value = output.getValue(); todo: а зачем оно вообще нужно?...
    final Map<String, String> keyValueMap; //todo: extract from output. кстати... сейчас этого нет?

    //todo: if (result == true)

    final var bzMszTransactionStagesOptional = bzMszTransactionStagesService.findByOutput(output);
    if (bzMszTransactionStagesOptional.isEmpty()) {
      log.info("bzMszTransactionStages is null");
      return; //todo: обновить конфигурацию и повторить?
    }
    final BzMszTransactionStages bzMszTransactionStages = bzMszTransactionStagesOptional.get();

    final Optional<BzMszStage> bzMszStageOptional
        = bzMszStageService.findByBzMszTransactionStages(bzMszTransactionStages);
    if(bzMszStageOptional.isEmpty()){
      log.info("BzMszStage is null");
      return;
    }
    final BzMszStage bzMszStage = bzMszStageOptional.get();

    //todo: странный переход. уточнить. правильно ли я понял
    final BzMsz bzMsz = bzMszService.findByBzMszStage(bzMszStage);

    final Msz msz = mszService.findByMszAndPerson(bzMsz, personId);

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


    final MszStage mszStage = mszStageService.createMszStage(bzMszTransactionStages,
                                                             bzMszStage,
                                                             msz);

    var list = new ArrayList<MszStageParam>();
    for (Map.Entry<String, String> entry : keyValueMap.entrySet()) {
      //todo: а что тут должно быть вместо OutputId? не понятна эта структура для карты
      var bzMszStageParam = bzMszStageParamService.findByOutputId(entry.getKey());

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
