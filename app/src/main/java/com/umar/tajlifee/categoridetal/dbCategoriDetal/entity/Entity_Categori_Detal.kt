package com.umar.tajlifee.categoridetal.dbCategoriDetal.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categoridetal")
data class Entity_Categori_Detal(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "imageResId")
    val imageResId: Int
)