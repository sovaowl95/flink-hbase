package ru.test.tasks;

import ecp.zhs.Output;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.test.mock.bz.BzMsz;
import ru.test.mock.bz.BzMszStage;
import ru.test.mock.bz.BzMszStageParam;
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
import java.util.HashMap;
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
    final Map<String, String> keyValueMap = new HashMap<>(); //todo: extract from output. кстати... сейчас этого нет?

    //todo: if (result == true)

    final var bzMszTransactionStagesOptional = bzMszTransactionStagesService.findByOutput(output);
    if (bzMszTransactionStagesOptional.isEmpty()) {
      log.info("bzMszTransactionStages is null");
      return; //todo: обновить конфигурацию и повторить?
    }
    final BzMszTransactionStages bzMszTransactionStages = bzMszTransactionStagesOptional.get();

    final Optional<BzMszStage> bzMszStageOptional
        = bzMszStageService.findByBzMszTransactionStages(bzMszTransactionStages);
    if (bzMszStageOptional.isEmpty()) {
      log.info("BzMszStage is null");
      return;
    }
    final BzMszStage bzMszStage = bzMszStageOptional.get();

    //todo: странный переход. уточнить. правильно ли я понял
    final Optional<BzMsz> bzMszOptional = bzMszService.findByBzMszStage(bzMszStage);
    if (bzMszOptional.isEmpty()) {
      log.info("BzMsz is null");
      return;
    }
    final BzMsz bzMsz = bzMszOptional.get();

    final Optional<Msz> mszOptional = mszService.findByMszAndPerson(bzMsz, personId);
    if (mszOptional.isEmpty()) {
      log.info("Msz is null");
      return;
    }
    final Msz msz = mszOptional.get();

    final Optional<MszStage> mszStageOptional = mszStageService.findByMsz(msz);
    if (mszStageOptional.isEmpty()) {
      log.info("MszStage is null");
      return;
    }
    final MszStage mszStage = mszStageOptional.get();


    /**
     * проверка:
     * текущий этап == начальной точке
     */
//    final String fromId = bzMszTransactionStages.getFromBzMszStageId();
//    final Optional<BzMszStage> bzMszStageOptionalFrom = bzMszStageService.findByBzMszStageId(fromId);
//    //todo: очень кривая логика. нужно что-то делать с этим. кажется нужно менять блок-схему
//    final BzMszStage from = bzMszStageOptionalFrom.orElse(null);
//
//    //not yet created
//    if (from == null) {
//      if (mszStageOptional.isPresent()) {
//        throw new RuntimeException("current stage not equal stage in db");
//      } else {
//        //todo: create
//      }
//    } else {
//      //created
//      final MszStage mszStage = mszStageOptional.orElseThrow(() -> new RuntimeException(""));
//      final BzMszStage currentBzMszStage = mszStage.getBzMszStage();
//
//      if (!from.equals(currentBzMszStage)) {
//        throw new RuntimeException("stages not equals");
//      }
//    }


    /**
     * создать новый этап и его параметры
     */
    final MszStage newMszStage = mszStageService.createMszStage(bzMszTransactionStages,
                                                                bzMszStage,
                                                                msz);
    mszStageService.save(newMszStage);

    var list = new ArrayList<MszStageParam>();
    for (Map.Entry<String, String> entry : keyValueMap.entrySet()) {
      //todo: а что тут должно быть вместо OutputId? не понятна эта структура для карты
      var bzMszStageParamOptional = bzMszStageParamService.findByOutputId(entry.getKey());
      final BzMszStageParam bzMszStageParam = bzMszStageParamOptional.get(); //todo: optional?

      final var mszStageParam = new MszStageParam();
      mszStageParam.setId(UUID.randomUUID().toString());
      mszStageParam.setMszStageId(newMszStage.getId());
      mszStageParam.setBzMszStageParamId(bzMszStageParam.getId());
      mszStageParam.setValue(entry.getValue());

      list.add(mszStageParam);
    }
    mszStageParamService.saveAll(list);
  }


}
