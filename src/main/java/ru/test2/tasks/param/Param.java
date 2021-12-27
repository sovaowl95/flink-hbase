package ru.test2.tasks.param;

import com.google.protobuf.Any;
import com.google.protobuf.BoolValue;
import com.google.protobuf.BytesValue;
import com.google.protobuf.Int64Value;
import com.google.protobuf.StringValue;
import ecp.zhs.Output;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import ru.test.mock.bz.BzMszStageParam;
import ru.test.mock.hbase.Msz;
import ru.test.mock.hbase.MszStage;
import ru.test.mock.hbase.MszStageParam;
import ru.test.service.bz.BzMszParamService;
import ru.test.service.hbase.MszStageParamService;
import ru.test.service.hbase.MszStageService;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
public class Param {
  private final MszStageService mszStageService;
  private final MszStageParamService mszStageParamService;

  private final BzMszParamService bzMszParamService;


  //force - флаг, который указывает на то, что все параметры должны быть обновлены
  public List<MszStageParam> updateParams(@Nonnull final Map<String, Output> outputsMap,
                                          @Nonnull final Msz msz,
                                          @Nonnull final Set<String> changedOutputs,
                                          final boolean force) {
    var savable = new ArrayList<MszStageParam>();

    final Optional<MszStage> mszStageOptional = mszStageService.findByMszId(msz.getId());
    if (mszStageOptional.isEmpty()) {
      throw new RuntimeException("msz without stage!");
    }
    final MszStage mszStage = mszStageOptional.get();

    //получить все параметры этого MSZ
    for (BzMszStageParam bzMszStageParam : bzMszParamService.getAllByMszStage(mszStage.getId())) {
      final String outputId = bzMszStageParam.getOutputId();
      final Output output = outputsMap.get(outputId);

      if (force && output == null) {
        throw new RuntimeException("Output not found, but must be present");
      }

      //если значение не поменялось, то всё ок
      if (output == null) {
        continue;
      }

      //если значение изменено или это НАСИЛЬНОЕ обновление :D
      if (changedOutputs.contains(outputId) || force) {
        final Object valueUnpacked = unpack(output.getValue(),
                                            bzMszStageParam.getType());

        final MszStageParam mszStageParam = mszStageParamService.create(mszStage.getId(),
                                                                        bzMszStageParam,
                                                                        valueUnpacked);
        savable.add(mszStageParam);
      }
    }

    return savable;
  }

  @SneakyThrows
  private Object unpack(final Any anyValue,
                        final String type) {
    switch (type) {
      case "String": {
        return anyValue.unpack(StringValue.class);
      }
      case "Integer": {
        return anyValue.unpack(Int64Value.class);
      }
      case "Boolean": {
        return anyValue.unpack(BoolValue.class);
      }
      case "Bytes": {
        return anyValue.unpack(BytesValue.class);
      }
      default:
        throw new RuntimeException("unknown type");
    }
  }
}
