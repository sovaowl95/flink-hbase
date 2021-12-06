package ru.migration;

import lombok.extern.slf4j.Slf4j;
import ru.client.HBaseClientV2;
import ru.exceptions.MigrationException;
import ru.table.lsdm.LsdmCalculatedAttribute;
import ru.table.lsdm.LsdmLifeEvent;
import ru.table.lsdm.LsdmPotentialRight;
import ru.table.lsdm.LsdmRight;
import ru.table.lsdm.LsdmTargetMeasureAttributes;
import ru.table.lsfc.LsfcAsyncFunctionExecution;
import ru.table.lsfc.LsfcExecution;
import ru.table.lsfc.LsfcExecutionChain;
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
      hBaseClientV2.createTableIfNotExists(LsdmCalculatedAttribute.getDdl());
      hBaseClientV2.createTableIfNotExists(LsdmLifeEvent.getDdl());
      hBaseClientV2.createTableIfNotExists(LsdmPotentialRight.getDdl());
      hBaseClientV2.createTableIfNotExists(LsdmRight.getDdl());
      hBaseClientV2.createTableIfNotExists(LsdmTargetMeasureAttributes.getDdl());

      hBaseClientV2.createTableIfNotExists(LsfcAsyncFunctionExecution.getDdl());
      hBaseClientV2.createTableIfNotExists(LsfcExecution.getDdl());
      hBaseClientV2.createTableIfNotExists(LsfcExecutionChain.getDdl());
      hBaseClientV2.createTableIfNotExists(LsfcInputData.getDdl());
      hBaseClientV2.createTableIfNotExists(LsfcOutputData.getDdl());
      hBaseClientV2.createTableIfNotExists(LsfcTriggerEvent.getDdl());

      hBaseClientV2.createTableIfNotExists(LsmaQueryExecution.getDdl());
      hBaseClientV2.createTableIfNotExists(LsmaQueryExecutionRow.getDdl());

//    TODO:
//    hBaseClientV2.createTableIfNotExists(LsocAsyncFunctionExecution.getDdl());
//    hBaseClientV2.createTableIfNotExists(LsocExecution.getDdl());
//    hBaseClientV2.createTableIfNotExists(LsocExecutionChain.getDdl());
//    hBaseClientV2.createTableIfNotExists(LsocInputData.getDdl());
//    hBaseClientV2.createTableIfNotExists(LsocOutputData.getDdl());
//    hBaseClientV2.createTableIfNotExists(LsocTriggerEvent.getDdl());

      hBaseClientV2.createTableIfNotExists(LstsTimerExecution.getDdl());
      hBaseClientV2.createTableIfNotExists(LstsTimerSchedule.getDdl());
    } catch (IOException e) {
      throw new MigrationException(e);
    }
    log.info("migration - v1... complete");
  }
}
