package ru;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Put;
import org.kie.dmn.api.core.DMNDecisionResult;
import org.kie.dmn.api.core.DMNResult;
import ru.client.HBaseClientV2;
import ru.dmn.DmnExecutor;
import ru.table.lsfc.LsfcOutputData;
import ru.table.lsfc.LsfcTriggerEvent;

import java.time.LocalDate;
import java.util.Map;

@Slf4j
public class Main {
  public static final int BUFFER_FLUSH_MAX_SIZE_IN_BYTES = 100_000;

  public static void main(String[] args) throws Exception {
    final Configuration configuration = createConfiguration();

    final HBaseClientV2 hBaseClientV2 = new HBaseClientV2(configuration);

    MigrationService.migrate(hBaseClientV2);


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


    final DmnExecutor dmnExecutor = new DmnExecutor();
    final Map<String, Object> person
        = Map.of("пол", "Женский",
                 "id", 12,
                 "датаРождения", LocalDate.of(1963, 8, 4));

    //todo: тут очень-очень-очень много данных
    //  decisionResults
    //  model
    //  model.inputs. выглядит так, словно это то, что я ищу
    final DMNResult dmnResult = dmnExecutor.execute(person);
    for (DMNDecisionResult decisionResult : dmnResult.getDecisionResults()) {
      if (DMNDecisionResult.DecisionEvaluationStatus.SUCCEEDED == decisionResult.getEvaluationStatus()) {


        final String decisionId = decisionResult.getDecisionId();
        //executionid  LSFC_Execution
        //calcoutputparameterid  LSFC_OutputData
        final boolean result = Boolean.parseBoolean(decisionResult.getResult().toString());
        //state
        //message
//
        //todo: передавать туда DMNDecisionResult?
        final LsfcOutputData lsfcOutputDataDto
            = new LsfcOutputData(decisionId,
                                 null,
                                 null,
                                 result,
                                 null,
                                 null);
        final Put put = lsfcOutputDataDto.toPut();
        hBaseClientV2.put(LsfcOutputData.TABLE_NAME, put);
      } else {
        //todo: а что нужно делать?
        log.error("some error, {}", decisionResult);
      }
    }
  }

//  private LsfcExecution

  private static Configuration createConfiguration() {
    log.info("createConfiguration...");
    final Configuration configuration = HBaseConfiguration.create();
    configuration.set("hbase.zookeeper.quorum", "localhost");
    configuration.set("hbase.zookeeper.property.clientPort", "2181");

    log.info("createConfiguration... complete");
    return configuration;
  }
}
