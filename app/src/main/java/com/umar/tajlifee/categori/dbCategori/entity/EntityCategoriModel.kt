package com.umar.tajlifee.categori.dbCategori.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "categori")
data class EntityCategoriModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "imageResId")
    val imageResId: Int
)

@Entity(tableName = "categoridetal")
data class Entity_Categori_Detal(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "detal_id")
    val detal_id: Long = 0,
    @ColumnInfo(name = "detal_name")
    val detal_name: String,
    @ColumnInfo(name = "detal_imageResId")
    val detal_imageResId: Int
)