package com.umar.tajlifee.categoridetal.dbCategoriDetal.dao
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.umar.tajlifee.categoridetal.dbCategoriDetal.entity.Entity_Categori_Detal


@Dao
interface Caterori_Detal_DAO {
    @Insert
    suspend fun insertCategory(category: Entity_Categori_Detal)

    @Query("SELECT * FROM categoridetal WHERE name LIKE '%' || :searchText || '%'")
    suspend fun searchCategories(searchText: String): List<Entity_Categori_Detal>

    @Query("SELECT * FROM categoridetal")
    suspend fun getAllCategories(): List<Entity_Categori_Detal>

    @Query("SELECT COUNT(*) FROM categoridetal")
    suspend fun getCategoriesCount(): Int

    @Query("DELETE FROM categoridetal")
    suspend fun deleteAllCategories()
}