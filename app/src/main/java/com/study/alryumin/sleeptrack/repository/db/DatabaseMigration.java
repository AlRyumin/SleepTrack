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
}
