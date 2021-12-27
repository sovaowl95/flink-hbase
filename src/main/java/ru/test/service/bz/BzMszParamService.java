package ru.test.service.bz;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import ru.MszStageParamQuery;
import ru.test.mock.bz.BzMszStageParam;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class BzMszParamService {
  private static final Map<String, BzMszStageParam> MAP_OUTPUT_ID = new HashMap<>();
  private static final Map<String, List<BzMszStageParam>> MAP_TO_BZ_MSZ_STAGE_ID = new HashMap<>();
  private final ApolloClient apolloClient;

  public BzMszParamService(final ApolloClient apolloClient) {
    this.apolloClient = apolloClient;
    updateConfig();
  }

  public List<BzMszStageParam> getAllByMszStage(final String mszStageParamId) {
    return MAP_TO_BZ_MSZ_STAGE_ID.get(mszStageParamId);
  }

//  @Nonnull
//  public Collection<BzMszStageParam> getAll() {
//    return MAP_OUTPUT_ID.values();
//  }

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

            MAP_OUTPUT_ID.put(bzMszStageParam.getOutputId(), bzMszStageParam);

            MAP_TO_BZ_MSZ_STAGE_ID.computeIfAbsent(bzMszStageParam.getBzMszStageId(), k -> new ArrayList<>());
            MAP_TO_BZ_MSZ_STAGE_ID.get(bzMszStageParam.getBzMszStageId()).add(bzMszStageParam);
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
}
