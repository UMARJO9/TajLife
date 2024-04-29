package com.umar.tajlifee.categori.dbCategory.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.umar.tajlifee.categori.dbCategory.entity.EntityCategoriModel
import com.umar.tajlifee.categori.dbCategory.entity.EntityInformation

@Dao
interface CategoriDao {
    @Insert
    suspend fun insertCategory(category: EntityCategoriModel)

    @Query("SELECT * FROM category WHERE title LIKE '%' || :searchText || '%' ")
    suspend fun searchCategories(searchText: String): List<EntityCategoriModel>

    @Query("SELECT * FROM category WHERE title LIKE '%' || :searchText || '%' ")
    suspend fun searchCategoriess(searchText: String): List<EntityCategoriModel>

    @Query("SELECT title FROM category WHERE detail_id = :detailId")
    suspend fun getTitleByDetailId(detailId: Int): String


    @Query("SELECT * FROM category WHERE is_start = :isStart")
    suspend fun getCategoriesWithIsStart(isStart: Int): List<EntityCategoriModel>

    @Query("SELECT * FROM category WHERE parent_id = :parentId")
    suspend fun getCategoriesByParentId(parentId: Int): List<EntityCategoriModel>

    @Query("SELECT * FROM category WHERE title LIKE '%' || :searchText || '%' AND parent_id = 1")
    suspend fun searchCategoriesparent1(searchText: String): List<EntityCategoriModel>

    @Query("SELECT * FROM category WHERE title LIKE '%' || :searchText || '%' AND parent_id = 2")
    suspend fun searchCategoriesparent2(searchText: String): List<EntityCategoriModel>

    @Query("SELECT * FROM category WHERE title LIKE '%' || :searchText || '%' AND parent_id = 3")
    suspend fun searchCategoriesparent3(searchText: String): List<EntityCategoriModel>

    @Query("SELECT * FROM category WHERE title LIKE '%' || :searchText || '%' AND parent_id = 4")
    suspend fun searchCategoriesparent4(searchText: String): List<EntityCategoriModel>

    @Query("SELECT * FROM category WHERE title LIKE '%' || :searchText || '%' AND parent_id = 5")
    suspend fun searchCategoriesparent5(searchText: String): List<EntityCategoriModel>

    @Query("SELECT COUNT(*) FROM category")
    suspend fun getCategoriesCount(): Int

    @Query("DELETE FROM category")
    suspend fun deleteAllCategories()

}

@Dao
interface InformationDao {
    @Insert
    suspend fun insertCategorys(category: EntityInformation)

    @Query("SELECT * FROM information WHERE detaili_id = :detailId")
    suspend fun getInformationByDetailId(detailId: Int): EntityInformation?


}
