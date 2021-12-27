package ru;


import com.apollographql.apollo.ApolloClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import ru.client.HBaseClientV2;
import ru.config.Hbase;
import ru.migration.MigrationService;
import ru.test.service.bz.BzMszService;
import ru.test.service.bz.BzMszStageParamService;
import ru.test.service.bz.BzMszStageService;
import ru.test.service.bz.BzMszTransitionService;


@Slf4j
public class Main {
  public static final int BUFFER_FLUSH_MAX_SIZE_IN_BYTES = 100_000;
  public static final String HTTP_LOCALHOST_8080_GRAPHQL = "http://localhost:8080/graphql";

  public static void main(String[] args) throws Exception {
    final Configuration configuration = Hbase.createConfiguration();

    final HBaseClientV2 hBaseClientV2 = new HBaseClientV2(configuration);

    MigrationService.migrate(hBaseClientV2);

    final ApolloClient apolloClient = ApolloClient.builder()
                                                  .serverUrl(HTTP_LOCALHOST_8080_GRAPHQL)
                                                  .build();

    final BzMszService bzMszService = new BzMszService(apolloClient);
    final BzMszStageService bzMszStageService = new BzMszStageService(apolloClient);
    final BzMszStageParamService bzMszStageParamService = new BzMszStageParamService(apolloClient);
    final BzMszTransitionService bzMszTransitionService = new BzMszTransitionService(apolloClient);


//    com.graphql.model.CreateMeasureStepMutation

//    final DefaultGraphQLClient defaultGraphQLClient = new DefaultGraphQLClient(HTTP_LOCALHOST_8080_GRAPHQL);
//    defaultGraphQLClient.

    //    final graph.QMeasures measures = graph.QMeasures.builder().build();
//    final var measuresList
//        = measures.request(HTTP_LOCALHOST_8080_GRAPHQL)
//                  .get().getMeasures();
//
//    for (graph.QMeasures.Result.measures v : measuresList) {
//      System.out.println("v = " + v);
//    }


//    final dmn.Query query = dmn..builder()
//                                         .withMeasures()
//                                         .build();


//    query.


//    final HBaseMutationConverter mutationConverter = new MockTableMutationConverter();
//    final MockTableSink mockTableSink = new MockTableSink(MockTable.TABLE_NAME,
//                                                          configuration,
//                                                          mutationConverter,
//                                                          BUFFER_FLUSH_MAX_SIZE_IN_BYTES,
//                                                          BUFFER_FLUSH_MAX_SIZE_IN_BYTES,
//                                                          BUFFER_FLUSH_MAX_SIZE_IN_BYTES); //todo: переделать на бины
//    MainFlink.startFlink(mockTableSink);


//    hBaseClientV2.readAll(MockTable.TABLE_NAME);
//    hBaseClientV2.countAll(MockTable.TABLE_NAME);

//
//    final LsfcTriggerEvent lsfcTriggerEvent =
//        new LsfcTriggerEvent(); //todo: создать запись о том, кто/что начал(о) событие


//    final DmnExecutor dmnExecutor = new DmnExecutor();
//    final Map<String, Object> person
//        = Map.of("пол", "Женский",
//                 "id", 12,
//                 "датаРождения", LocalDate.of(1963, 8, 4));
//
//    //todo: тут очень-очень-очень много данных
//    //  decisionResults
//    //  model
//    //  model.inputs. выглядит так, словно это то, что я ищу
//    final DMNResult dmnResult = dmnExecutor.execute(person);
//    for (DMNDecisionResult decisionResult : dmnResult.getDecisionResults()) {
//      if (DMNDecisionResult.DecisionEvaluationStatus.SUCCEEDED == decisionResult.getEvaluationStatus()) {
//
//
//        final String decisionId = decisionResult.getDecisionId();
//        //executionid  LSFC_Execution
//        //calcoutputparameterid  LSFC_OutputData
//        final boolean result = Boolean.parseBoolean(decisionResult.getResult().toString());
//        //state
//        //message
////
//        //todo: передавать туда DMNDecisionResult?
//        final LsfcOutputData lsfcOutputDataDto
//            = new LsfcOutputData(decisionId,
//                                 null,
//                                 null,
//                                 result,
//                                 null,
//                                 null);
//        final Put put = lsfcOutputDataDto.toPut();
//        hBaseClientV2.put(LsfcOutputData.TABLE_NAME, put);
//      } else {
//        //todo: а что нужно делать?
//        log.error("some error, {}", decisionResult);
//      }
//    }
  }


}
