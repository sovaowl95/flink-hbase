//package ru.test.service.bz;
//
//import com.apollographql.apollo.ApolloCall;
//import com.apollographql.apollo.ApolloClient;
//import com.apollographql.apollo.api.Response;
//import com.apollographql.apollo.exception.ApolloException;
//import lombok.extern.slf4j.Slf4j;
//import org.jetbrains.annotations.NotNull;
//import ru.MszStageQuery;
//import ru.test.mock.bz.BzMszStage;
//import ru.test.mock.bz.BzMszStageParam;
//import ru.test.mock.bz.BzMszTransition;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Optional;
//
//@Slf4j
//public class BzMszStageService {
//  private static final Map<String, BzMszStage> MAP_ID = new HashMap<>();
//  private static final Map<String, BzMszStage> MAP_STAGE_ID = new HashMap<>();
//  private final ApolloClient apolloClient;
//
//  public BzMszStageService(ApolloClient apolloClient) {
//    this.apolloClient = apolloClient;
//    updateConfig();
//  }
//
//  public Optional<BzMszStage> findByBzMszStageId(BzMszStageParam bzMszStageParam) {
//    final String bzMszStageId = bzMszStageParam.getBzMszStageId();
//    return Optional.of(MAP_STAGE_ID.get(bzMszStageId));
//  }
//
//
//  public Optional<BzMszStage> findByBzMszTransactionStages(BzMszTransition bzMszTransition) {
//    final String targetId = bzMszTransition.getFromBzMszStageId();
//    return Optional.of(MAP_ID.get(targetId));
//
//  }
//
//  private void updateConfig() {
//    final ApolloCall.Callback<MszStageQuery.Data> callback = new ApolloCall.Callback<>() {
//      @Override
//      public void onResponse(@NotNull Response<MszStageQuery.Data> response) {
//        if (response.getData() != null && response.getData().getStepsForMeasure() != null) {
//          for (MszStageQuery.GetStepsForMeasure getStepsForMeasure : response.getData().getStepsForMeasure()) {
//            final BzMszStage bzMszStage = new BzMszStage();
//            bzMszStage.setId(getStepsForMeasure.id());
//            bzMszStage.setBzMszId(String.valueOf(getStepsForMeasure.measureId()));
//            MAP_ID.put(bzMszStage.getId(), bzMszStage);
//            MAP_STAGE_ID.put(bzMszStage.getBzMszId(), bzMszStage);
//          }
//        }
//      }
//
//      @Override
//      public void onFailure(@NotNull ApolloException e) {
//        log.info("e = " + e);
//      }
//    };
//
//    apolloClient.query(ru.MszStageQuery.builder().build())
//                .enqueue(callback);
//  }
//}
