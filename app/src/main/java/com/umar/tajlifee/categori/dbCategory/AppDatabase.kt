package com.umar.tajlifee.categori.dbCategory

import androidx.room.Database
import androidx.room.RoomDatabase
import com.umar.tajlifee.categori.dbCategory.dao.CategoriDao
import com.umar.tajlifee.categori.dbCategory.dao.InformationDao
import com.umar.tajlifee.categori.dbCategory.entity.EntityCategoriModel
import com.umar.tajlifee.categori.dbCategory.entity.EntityInformation
@Database(entities = [EntityCategoriModel::class,EntityInformation::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoriDao
    abstract fun InformationDao(): InformationDao

}