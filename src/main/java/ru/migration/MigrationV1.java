//package ru.migration;
//
//import lombok.extern.slf4j.Slf4j;
//import ru.client.HBaseClientV2;
//import ru.table.HvacTable;
//
//@Slf4j
//public class MigrationV1 {
//  public void migrate(HBaseClientV2 hBaseClientV2) throws Exception {
//    log.info("migration - v1...");
//    //todo: migration history table?
//    hBaseClientV2.createTableIfNotExists(HvacTable.TABLE_NAME, HvacTable.getColumnFamilies());
//    log.info("migration - v1... complete");
//  }
//}
