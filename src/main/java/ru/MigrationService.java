package ru;

import ru.client.HBaseClientV2;
import ru.migration.MigrationV1;

class MigrationService {
  static void migrate(HBaseClientV2 hBaseClientV2) {
    MigrationV1 migrationV1 = new MigrationV1();
    migrationV1.migrate(hBaseClientV2);
  }

}
