package com.umar.tajlifee.categori.dbCategori

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val migration_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE IF NOT EXISTS `categoridetal_new` (`detal_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `detal_name` TEXT NOT NULL, `detal_imageResId` INTEGER NOT NULL)")
        val cursor =
            database.query("SELECT name FROM sqlite_master WHERE type='table' AND name='categoridetal'")
        val tableExists = cursor.count > 0
        cursor.close()
        if (tableExists) {
            database.execSQL("INSERT INTO `categoridetal_new` (`detal_id`, `detal_name`, `detal_imageResId`) SELECT `detal_id`, `detal_name`, `detal_imageResId` FROM `categoridetal`")
        }
        database.execSQL("DROP TABLE IF EXISTS `categoridetal`")
        database.execSQL("ALTER TABLE `categoridetal_new` RENAME TO `categoridetal`")
    }
}
