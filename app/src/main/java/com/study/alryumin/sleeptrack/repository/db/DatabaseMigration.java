package com.study.alryumin.sleeptrack.repository.db;

import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class DatabaseMigration {
    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `SleepTime` " +
                    "(`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `startAt` INTEGER, " +
                    "`finishAt` INTEGER, `sleepTime` INTEGER NOT NULL)");
        }
    };

    public static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `Target` " +
                    "(`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `startAt` INTEGER, " +
                    "`finishAt` INTEGER, `sleepTime` INTEGER NOT NULL)");
        }
    };

    public static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `AppSettings` " +
                    "(`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `minSleepTime` INTEGER NOT NULL)");
        }
    };
}
