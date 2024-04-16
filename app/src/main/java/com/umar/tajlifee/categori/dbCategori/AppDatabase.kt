package com.umar.tajlifee.categori.dbCategori

import androidx.room.Database
import androidx.room.RoomDatabase
import com.umar.tajlifee.categori.dbCategori.dao.CategoriDao
import com.umar.tajlifee.categori.dbCategori.dao.Caterori_Detal_DAO
import com.umar.tajlifee.categori.dbCategori.entity.EntityCategoriModel
import com.umar.tajlifee.categori.dbCategori.entity.Entity_Categori_Detal

@Database(entities = [EntityCategoriModel::class, Entity_Categori_Detal::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoriDao
    abstract fun Caterori_Detal_DAO(): Caterori_Detal_DAO

}