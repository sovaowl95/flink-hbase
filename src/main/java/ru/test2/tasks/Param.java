package ru.test2.tasks;

import com.google.protobuf.Any;
import ecp.zhs.Output;
import lombok.RequiredArgsConstructor;
import ru.test.mock.bz.BzMszStageParam;
import ru.test.mock.hbase.Msz;
import ru.test.mock.hbase.MszStage;
import ru.test.mock.hbase.MszStageParam;
import ru.test.service.bz.BzMszStageParamService;
import ru.test.service.hbase.MszStageParamService;
import ru.test.service.hbase.MszStageService;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class Param {
  private final MszStageService mszStageService;
  private final MszStageParamService mszStageParamService;

  private final BzMszStageParamService bzMszStageParamService;


  public void updateParams(final Map<String, Output> outputsMap,
                           @Nonnull final Msz msz,
                           final boolean force) {
    //todo: force

    //todo: можно ли как-то оптимизировать?
    final Optional<MszStage> mszStageOptional = mszStageService.findByMsz(msz);
    //if(mszStageOptional.isPresent())//todo:
    final MszStage mszStage = mszStageOptional.get();
//    final List<MszStageParam> allParams = mszStageParamService.findAllByMszStage(mszStage);

    for (BzMszStageParam bzMszStageParam : bzMszStageParamService.getAll()) {
      final String outputId = bzMszStageParam.getOutputId();
      final Output output = outputsMap.get(outputId);

      //todo: в схеме не предусмотрено отсутствие этого параметра
      //todo: если output == null, то это ошибка. прерываем весь пайп.
      if (output != null /* && output.valueChanged */) { //todo:
        final Any value = output.getValue();
        //todo: нужно как-то понять тип этого value. вероятно по связи Msz с BzMsz ->
        // bzMszStageParam.getType()
        //в протобафе есть wrappers.proto

        final MszStageParam mszStageParam = new MszStageParam();
        mszStageParam.setId(UUID.randomUUID().toString());
        mszStageParam.setMszStageId(mszStage.getId());
        mszStageParam.setBzMszStageParamId(bzMszStageParam.getId());
        mszStageParam.setValue(value);
        mszStageParamService.save(mszStageParam); //todo: batch
        //todo: собрать все сущности в одну коллекцию, потом сохранить батчем
      }
    }
  }
}
