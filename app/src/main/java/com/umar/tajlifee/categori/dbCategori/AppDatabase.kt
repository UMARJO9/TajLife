package com.umar.tajlifee.categori.dbCategori

import androidx.room.Database
import androidx.room.RoomDatabase
import com.umar.tajlifee.categori.dbCategori.dao.CategoriDao
import com.umar.tajlifee.categori.dbCategori.entity.EntityCategoriModel

@Database(entities = [EntityCategoriModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoriDao

}