package com.umar.tajlifee.categori.dbCategori.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.umar.tajlifee.categori.dbCategori.entity.EntityCategoriModel
import com.umar.tajlifee.categori.dbCategori.entity.Entity_Categori_Detal

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
@Dao
interface Caterori_Detal_DAO {
    @Insert
    suspend fun insertCategory(category: Entity_Categori_Detal)

    @Query("SELECT * FROM categoridetal WHERE detal_name LIKE '%' || :searchText || '%'")
    suspend fun searchCategories(searchText: String): List<Entity_Categori_Detal>

    @Query("SELECT * FROM categoridetal")
    suspend fun getAllCategories(): List<Entity_Categori_Detal>

    @Query("SELECT COUNT(*) FROM categoridetal")
    suspend fun getCategoriesCount(): Int

    @Query("DELETE FROM categoridetal")
    suspend fun deleteAllCategories()
}