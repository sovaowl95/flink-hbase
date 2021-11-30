package ru.migration;

import lombok.extern.slf4j.Slf4j;
import ru.client.HBaseClientV2;
import ru.table.MockTable;

@Slf4j
public class MigrationV2 {
  public void migrate(HBaseClientV2 hBaseClientV2) throws Exception {
    log.info("migration - v2...");
    //todo: migration history table?
    hBaseClientV2.createTableIfNotExists(MockTable.TABLE_NAME,
                                         MockTable.ColumnFamilies.getColumnFamilies());
    log.info("migration - v2... complete");
  }
}
