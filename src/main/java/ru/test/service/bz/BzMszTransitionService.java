package ru.test.service.bz;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import ru.MszTransitionQuery;
import ru.test.mock.bz.BzMszTransition;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class BzMszTransitionService {
  private static final Map<String, BzMszTransition> MAP_OUTPUT_ID = new HashMap<>();
  private final ApolloClient apolloClient;

  public BzMszTransitionService(ApolloClient apolloClient) {
    this.apolloClient = apolloClient;
    updateConfig();
  }

  @Nonnull
  public Collection<BzMszTransition> findAll() {
    return MAP_OUTPUT_ID.values();
  }

  private void updateConfig() {
    final ApolloCall.Callback<MszTransitionQuery.Data> callback = new ApolloCall.Callback<>() {
      @Override
      public void onResponse(@NotNull Response<MszTransitionQuery.Data> response) {
        if (response.getData() != null && response.getData().getTransitionsForMeasure() != null) {
          final var transitionsForMeasure
              = response.getData().getTransitionsForMeasure();
          for (MszTransitionQuery.GetTransitionsForMeasure stepsForMeasure : transitionsForMeasure) {
            final BzMszTransition bzMszTransition = new BzMszTransition();
            bzMszTransition.setId(stepsForMeasure.id());
            bzMszTransition.setOutputId(String.valueOf(stepsForMeasure.decisionId()));
            bzMszTransition.setFromBzMszStageId(String.valueOf(stepsForMeasure.fromStepId()));
            bzMszTransition.setToBzMszStageId(String.valueOf(stepsForMeasure.toStepId()));
            MAP_OUTPUT_ID.put(bzMszTransition.getOutputId(), bzMszTransition);
          }
        }
      }

      @Override
      public void onFailure(@NotNull ApolloException e) {
        log.info("e = " + e);
      }
    };

    apolloClient.query(ru.MszTransitionQuery.builder().build())
                .enqueue(callback);
  }
}
