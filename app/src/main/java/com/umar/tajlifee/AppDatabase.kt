package com.umar.tajlifee

import androidx.room.Database
import androidx.room.RoomDatabase
import com.umar.tajlifee.categori.model.CategoryEntity

@Database(entities = [CategoryEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao

}