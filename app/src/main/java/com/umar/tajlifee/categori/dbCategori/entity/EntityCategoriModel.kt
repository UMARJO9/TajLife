package com.umar.tajlifee.categori.dbCategori.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "category")
data class EntityCategoriModel(
    @PrimaryKey val id: Int,
    val title: String,
    @ColumnInfo(name = "is_start")
    val is_Start: Int = 0,
    @ColumnInfo(name = "parent_id")
    val parent_id: Int = 0,
    @ColumnInfo(name = "type")
    val type: Int,
    @ColumnInfo(name = "detail_id")
    val detail_id: Int,
    @ColumnInfo(name = "image_url")
    val image_url: String
)
