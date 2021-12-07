package ru.migration;

import lombok.extern.slf4j.Slf4j;
import ru.client.HBaseClientV2;
import ru.exceptions.MigrationException;
import ru.table.lsdm.LsdmCalculatedAttribute;
import ru.table.lsdm.LsdmLifeEvent;
import ru.table.lsdm.LsdmPotentialRight;
import ru.table.lsdm.LsdmRight;
import ru.table.lsdm.LsdmTargetMeasureAttributes;
//import ru.table.lsfc.LsfcAsyncFunctionExecution;
//import ru.table.lsfc.LsfcExecution;
//import ru.table.lsfc.LsfcExecutionChain;
import ru.table.lsfc.LsfcInputData;
import ru.table.lsfc.LsfcOutputData;
import ru.table.lsfc.LsfcTriggerEvent;
import ru.table.lsma.LsmaQueryExecution;
import ru.table.lsma.LsmaQueryExecutionRow;
import ru.table.lsts.LstsTimerExecution;
import ru.table.lsts.LstsTimerSchedule;

import java.io.IOException;

@Slf4j
public class MigrationV1 {
  public void migrate(HBaseClientV2 hBaseClientV2) {
    log.info("migration - v1...");
    try {
      //todo: migration history table?
//      hBaseClientV2.createTableIfNotExists(LsdmCalculatedAttribute.TABLE_DDL);
//      hBaseClientV2.createTableIfNotExists(LsdmLifeEvent.TABLE_DDL);
//      hBaseClientV2.createTableIfNotExists(LsdmPotentialRight.TABLE_DDL);
//      hBaseClientV2.createTableIfNotExists(LsdmRight.TABLE_DDL);
//      hBaseClientV2.createTableIfNotExists(LsdmTargetMeasureAttributes.TABLE_DDL);
//
//      hBaseClientV2.createTableIfNotExists(LsfcAsyncFunctionExecution.TABLE_DDL);
//      hBaseClientV2.createTableIfNotExists(LsfcExecution.TABLE_DDL);
//      hBaseClientV2.createTableIfNotExists(LsfcExecutionChain.TABLE_DDL);
      hBaseClientV2.createTableIfNotExists(LsfcInputData.TABLE_DDL);
      hBaseClientV2.createTableIfNotExists(LsfcOutputData.TABLE_DDL);
      hBaseClientV2.createTableIfNotExists(LsfcTriggerEvent.TABLE_DDL);
//
//      hBaseClientV2.createTableIfNotExists(LsmaQueryExecution.TABLE_DDL);
//      hBaseClientV2.createTableIfNotExists(LsmaQueryExecutionRow.TABLE_DDL);

//    TODO:
//    hBaseClientV2.createTableIfNotExists(LsocAsyncFunctionExecution.TABLE_DDL);
//    hBaseClientV2.createTableIfNotExists(LsocExecution.TABLE_DDL);
//    hBaseClientV2.createTableIfNotExists(LsocExecutionChain.TABLE_DDL);
//    hBaseClientV2.createTableIfNotExists(LsocInputData.TABLE_DDL);
//    hBaseClientV2.createTableIfNotExists(LsocOutputData.TABLE_DDL);
//    hBaseClientV2.createTableIfNotExists(LsocTriggerEvent.TABLE_DDL);

//      hBaseClientV2.createTableIfNotExists(LstsTimerExecution.TABLE_DDL);
//      hBaseClientV2.createTableIfNotExists(LstsTimerSchedule.TABLE_DDL);
    } catch (IOException e) {
      throw new MigrationException(e);
    }
    log.info("migration - v1... complete");
  }
}
