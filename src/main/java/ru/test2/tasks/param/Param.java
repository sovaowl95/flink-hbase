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
import ru.test.service.bz.BzMszStageParamService;
import ru.test.service.hbase.MszStageParamService;
import ru.test.service.hbase.MszStageService;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
public class Param {
  private final MszStageService mszStageService;
  private final MszStageParamService mszStageParamService;

  private final BzMszStageParamService bzMszStageParamService;


  public List<MszStageParam> updateParams(final Map<String, Output> outputsMap,
                                          @Nonnull final Msz msz,
                                          @Nonnull final Set<String> changedOutputs,
                                          final boolean force) {
    var savable = new ArrayList<MszStageParam>();
    final Optional<MszStage> mszStageOptional = mszStageService.findByMsz(msz);
    //if(mszStageOptional.isPresent())//todo:
    final MszStage mszStage = mszStageOptional.get();
//    final List<MszStageParam> allParams = mszStageParamService.findAllByMszStage(mszStage);

    for (BzMszStageParam bzMszStageParam : bzMszStageParamService.getAll()) {
      final String outputId = bzMszStageParam.getOutputId();
      final Output output = outputsMap.get(outputId);

      if (force || changedOutputs.contains(outputId)) {
        if (output == null) {
          throw new RuntimeException("Output not found");
        }

        if (output != null /* && output.valueChanged */) { //todo:
          final Any anyValue = output.getValue();
          final Object valueUnpacked = unpack(anyValue, bzMszStageParam.getType());
          //в протобафе есть wrappers.proto

          final MszStageParam mszStageParam = new MszStageParam();
          mszStageParam.setId(UUID.randomUUID().toString());
          mszStageParam.setMszStageId(mszStage.getId());
          mszStageParam.setBzMszStageParamId(bzMszStageParam.getId());
          mszStageParam.setValue(valueUnpacked);

          savable.add(mszStageParam);
        }
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
