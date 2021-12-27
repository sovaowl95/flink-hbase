package ru.migration;

import ru.client.HBaseClientV2;

public class MigrationService {
  public static void migrate(HBaseClientV2 hBaseClientV2) {
    MigrationV1 migrationV1 = new MigrationV1();
    migrationV1.migrate(hBaseClientV2);
  }

}
