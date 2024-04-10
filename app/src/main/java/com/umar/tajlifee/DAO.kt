package com.umar.tajlifee

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.umar.tajlifee.categori.model.CategoryEntity

@Dao
interface CategoryDao {
    @Insert
    suspend fun insertCategory(category: CategoryEntity)

    @Query("SELECT * FROM categories WHERE name LIKE '%' || :searchText || '%'")
    suspend fun searchCategories(searchText: String): List<CategoryEntity>

    @Query("SELECT * FROM categories")
    suspend fun getAllCategories(): List<CategoryEntity>

    @Query("SELECT COUNT(*) FROM categories")
    suspend fun getCategoriesCount(): Int

    @Query("DELETE FROM categories")
    suspend fun deleteAllCategories()
}