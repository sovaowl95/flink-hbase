package ru.test.tasks;

import ecp.zhs.Output;
import lombok.RequiredArgsConstructor;
import ru.test.mock.bz.BzMsz;
import ru.test.mock.bz.BzMszStage;
import ru.test.mock.bz.BzMszStageParam;
import ru.test.mock.hbase.Msz;
import ru.test.mock.hbase.MszStage;
import ru.test.mock.hbase.MszStageParam;
import ru.test.service.bz.BzMszService;
import ru.test.service.bz.BzMszStageParamService;
import ru.test.service.bz.BzMszStageService;
import ru.test.service.hbase.MszService;
import ru.test.service.hbase.MszStageParamService;
import ru.test.service.hbase.MszStageService;

import java.util.Optional;
import java.util.UUID;

//todo: сделать всё Optional, чтобы зафорсить людей проверять и обрабатывать ошибки
//todo: сделать везде общение через id
@RequiredArgsConstructor
public class ParamChangeTask {
  private final BzMszService bzMszService;
  private final BzMszStageService bzMszStageService;
  private final BzMszStageParamService bzMszStageParamService;

  private final MszService mszService;
  private final MszStageService mszStageService;
  private final MszStageParamService mszStageParamService;

  public void execute(final Output output) {
    final String outputId = output.getOutputId();
    final String personId = ""; //todo:
    final Object value = output.getValue();

    final Optional<BzMszStageParam> bzMszStageParamOptional = bzMszStageParamService.findByOutputId(outputId);
    if (bzMszStageParamOptional.isEmpty()) {
      return; //todo: обновить конфигурацию и повторить?
    }
    final BzMszStageParam bzMszStageParam = bzMszStageParamOptional.get();
    //todo: check

    final BzMszStage bzMszStage = bzMszStageService.findById(bzMszStageParam.getBzMszStage());
    //todo: странный переход. уточнить. правильно ли я понял
    final BzMsz bzMsz = bzMszService.findBy(bzMszStage.getBzMsz());

    final Msz msz = mszService.findByMszAndPerson(bzMsz, personId);

    final MszStage mszStage = mszStageService.findByMsz(msz);
    //todo: является ли загруженный этап - последним?


    //todo: есть ли смысл перепроверять перед записью? вдруг не изменилось значение


    //todo: в блок-схеме было обновить. как я понял, лучше создать новое
    // при PUT будут перезаписаны старые значения

    final MszStageParam mszStageParam = new MszStageParam();
    mszStageParam.setId(UUID.randomUUID().toString()); //todo:?
    mszStageParam.setMszStage(mszStage); //todo: id
    mszStageParam.setBzMszStageParam(bzMszStageParam);
    mszStageParam.setValue(value);
    mszStageParamService.save(mszStageParam);
  }
}
