package com.umar.tajlifee.categori.dbCategory.entity

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
    @ColumnInfo(name = "detail_id")
    val detail_id: Int,
    @ColumnInfo(name = "image_url")
    val image_url: String,
)
@Entity(tableName = "information")
data class EntityInformation(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "images_url")
    val images_url: String ,
    @ColumnInfo(name = "information")
    val information: String,
    @ColumnInfo(name = "detaili_id")
    val detaili_id: Int,
)