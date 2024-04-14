package com.umar.tajlifee.categoridetal.dbCategoriDetal

import androidx.room.Database
import androidx.room.RoomDatabase
import com.umar.tajlifee.categoridetal.dbCategoriDetal.dao.Caterori_Detal_DAO
import com.umar.tajlifee.categoridetal.dbCategoriDetal.entity.Entity_Categori_Detal

@Database(entities = [Entity_Categori_Detal::class], version = 1)
abstract class Data_Base_Detal : RoomDatabase() {
    abstract fun Caterori_Detal_DAO(): Caterori_Detal_DAO

}