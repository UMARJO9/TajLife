package com.umar.tajlifee.categori.dbCategori.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.umar.tajlifee.categori.dbCategori.entity.EntityCategoriModel

@Dao
interface CategoriDao {
    @Insert
    suspend fun insertCategory(category: EntityCategoriModel)

    @Query("SELECT * FROM categori WHERE name LIKE '%' || :searchText || '%'")
    suspend fun searchCategories(searchText: String): List<EntityCategoriModel>

    @Query("SELECT * FROM categori")
    suspend fun getAllCategories(): List<EntityCategoriModel>

    @Query("SELECT COUNT(*) FROM categori")
    suspend fun getCategoriesCount(): Int

    @Query("DELETE FROM categori")
    suspend fun deleteAllCategories()
}