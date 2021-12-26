package ru.test.service.bz;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import ecp.zhs.Output;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import ru.MszStageParamQuery;
import ru.test.mock.bz.BzMszStageParam;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class BzMszStageParamService {
  private static final Map<String, BzMszStageParam> MAP = new HashMap<>();
  private final ApolloClient apolloClient;

  public BzMszStageParamService(ApolloClient apolloClient) {
    this.apolloClient = apolloClient;
    updateConfig();
  }

  @Nonnull
  public Optional<BzMszStageParam> findByOutput(Output output) {
    return findByOutputId(output.getOutputId());
  }

  public Optional<BzMszStageParam> findByOutputId(final String outputId) {
    return Optional.of(MAP.get(outputId));
  }

  private void updateConfig() {
    final ApolloCall.Callback<MszStageParamQuery.Data> callback = new ApolloCall.Callback<>() {
      @Override
      public void onResponse(@NotNull Response<MszStageParamQuery.Data> response) {
        if (response.getData() != null && response.getData().getMeasureStepParameters() != null) {
          final var measureStepParameters = response.getData().getMeasureStepParameters();
          for (var measureStepParameter : measureStepParameters) {
            final BzMszStageParam bzMszStageParam = new BzMszStageParam();
            bzMszStageParam.setId(measureStepParameter.id());
            bzMszStageParam.setOutputId(String.valueOf(measureStepParameter.decisionId()));
            bzMszStageParam.setBzMszStageId(String.valueOf(measureStepParameter.measureStepId()));
            bzMszStageParam.setTitle(measureStepParameter.title());
            bzMszStageParam.setType(measureStepParameter.code());

            MAP.put(bzMszStageParam.getOutputId(), bzMszStageParam);
          }
        }
      }

      @Override
      public void onFailure(@NotNull ApolloException e) {
        log.info("e = " + e);
      }
    };

    apolloClient.query(ru.MszStageParamQuery.builder().build())
                .enqueue(callback);
  }

  @Nonnull
  public Collection<BzMszStageParam> getAll() {
    return MAP.values();
  }
}
