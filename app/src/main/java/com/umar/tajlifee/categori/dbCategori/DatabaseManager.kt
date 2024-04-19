package com.umar.tajlifee.categori.dbCategori

import android.content.Context
import androidx.room.Room

object DatabaseManager {
    private var instance: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        synchronized(this) {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "category.db"
                ).createFromAsset("db/category.db").build()
            }
            return instance!!
        }
    }
}