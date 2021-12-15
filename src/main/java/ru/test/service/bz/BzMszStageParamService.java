package ru.test.service.bz;

import ecp.zhs.Output;
import lombok.RequiredArgsConstructor;
import ru.test.mock.bz.BzMszStageParam;
import ru.test.service.bz2.BzConfigurationService;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Optional;

@RequiredArgsConstructor
public class BzMszStageParamService {
  //todo: mock. replace with REST
  final ArrayList<BzMszStageParam> mock = new ArrayList<>();

  private BzMszStageParamService() {

  }

  private final BzConfigurationService bzConfigurationService;

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

  private Optional<BzMszStageParam> findByOutputId(final String outputId) {
    //todo:
    final BzMszStageParam value = new BzMszStageParam();
    return Optional.of(value);
  }

  public BzMszStageParam findByKey(String key) {
    //todo: не понимаю, что сюда писать
    return null;
  }
}
