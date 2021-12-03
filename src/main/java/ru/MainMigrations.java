package ru;

import ru.client.HBaseClientV2;
import ru.migration.MigrationV1;
import ru.migration.MigrationV2;

class MainMigrations {
  static void initMigrations(HBaseClientV2 hBaseClientV2) throws Exception {
    MigrationV1 migrationV1 = new MigrationV1();
    migrationV1.migrate(hBaseClientV2);

    MigrationV2 migrationV2 = new MigrationV2();
    migrationV2.migrate(hBaseClientV2);
  }

}
