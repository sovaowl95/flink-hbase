package ru.test.service.bz;

import ecp.zhs.Output;
import ru.test.mock.Constants;
import ru.test.mock.bz.BzMszStageParam;
import ru.test.service.bz2.BzConfigurationService;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Optional;

public class BzMszStageParamService {
  //todo: mock. replace with REST
  final ArrayList<BzMszStageParam> mock = new ArrayList<>();
  private final BzConfigurationService bzConfigurationService = new BzConfigurationService();

  public BzMszStageParamService() {
    BzMszStageParam bzMszStageParam;

    bzMszStageParam = new BzMszStageParam();
    bzMszStageParam.setOutputId(Constants.OUTPUT_ID_1);
    bzMszStageParam.setBzMszStageId(Constants.BZ_MSZ_STAGE_ID_1);
    bzMszStageParam.setName("name1");
    mock.add(bzMszStageParam);

    bzMszStageParam = new BzMszStageParam();
    bzMszStageParam.setOutputId(Constants.OUTPUT_ID_1);
    bzMszStageParam.setBzMszStageId(Constants.BZ_MSZ_STAGE_ID_1);
    bzMszStageParam.setName("name2");
    mock.add(bzMszStageParam);

    bzMszStageParam = new BzMszStageParam();
    bzMszStageParam.setOutputId(Constants.OUTPUT_ID_3);
    bzMszStageParam.setBzMszStageId(Constants.BZ_MSZ_STAGE_ID_2);
    bzMszStageParam.setName("name3");
    mock.add(bzMszStageParam);

    bzMszStageParam = new BzMszStageParam();
    bzMszStageParam.setOutputId(Constants.OUTPUT_ID_3);
    bzMszStageParam.setBzMszStageId(Constants.BZ_MSZ_STAGE_ID_3);
    bzMszStageParam.setName("name4");
    mock.add(bzMszStageParam);
  }


  @Nonnull
  public Optional<BzMszStageParam> findByOutput(Output output) {
    final String outputId = output.getOutputId();
    int tries = 3;
    while (tries-- > 0) {
      final Optional<BzMszStageParam> bzMszStageParamOptional = findByOutputId(outputId);
      if (bzMszStageParamOptional.isPresent()) {
        return bzMszStageParamOptional;
      }
      bzConfigurationService.updateConfig();
      //todo: обновить конфигурацию и повторить?
      //todo: или можно как-то reject, чтобы обработать позже?
      //todo: например, если еще не обновился конфиг (не успел), а уже прилетают задачи

    }
    return Optional.empty();
  }

  public Optional<BzMszStageParam> findByOutputId(final String outputId) {
    for (BzMszStageParam bzMszStageParam : mock) {
      if (bzMszStageParam.getOutputId().equals(outputId)) {
        return Optional.of(bzMszStageParam);
      }
    }

    return Optional.empty();
  }
}
