package ru.test.service.bz;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import ru.MszQuery;
import ru.test.mock.bz.BzMsz;
import ru.test.mock.bz.BzMszStage;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class BzMszService {
  private static final Map<String, BzMsz> MAP = new HashMap<>();
  private final ApolloClient apolloClient;

  public BzMszService(ApolloClient apolloClient) {
    this.apolloClient = apolloClient;
    updateConfig();
  }

  public Optional<BzMsz> findByBzMszStage(BzMszStage bzMszStage) {
    final String targetId = bzMszStage.getId();
    return Optional.of(MAP.get(targetId));
  }

  private void updateConfig() {
    final ApolloCall.Callback<MszQuery.Data> callback = new ApolloCall.Callback<>() {
      @Override
      public void onResponse(@NotNull Response<MszQuery.Data> response) {
        if (response.getData() != null && response.getData().measures() != null) {
          for (MszQuery.Measure measure : response.getData().measures()) {
            final BzMsz bzMsz = new BzMsz();
            bzMsz.setId(measure.id());
            MAP.put(bzMsz.getId(), bzMsz);
          }
        }
      }

      @Override
      public void onFailure(@NotNull ApolloException e) {
        log.info("e = " + e);
      }
    };

    apolloClient.query(ru.MszQuery.builder().build())
                .enqueue(callback);
  }
}
