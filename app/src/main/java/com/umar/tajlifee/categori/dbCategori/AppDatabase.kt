package com.umar.tajlifee.categori.dbCategori

import androidx.room.Database
import androidx.room.RoomDatabase
import com.umar.tajlifee.categori.dbCategori.dao.CategoriDao
import com.umar.tajlifee.categori.dbCategori.dao.InformationDao
import com.umar.tajlifee.categori.dbCategori.entity.EntityCategoriModel
import com.umar.tajlifee.categori.dbCategori.entity.EntityInformation
@Database(entities = [EntityCategoriModel::class,EntityInformation::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoriDao
    abstract fun InformationDao(): InformationDao

}