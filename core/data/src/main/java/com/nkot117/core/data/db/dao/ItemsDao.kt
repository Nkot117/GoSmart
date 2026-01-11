package com.nkot117.core.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nkot117.core.data.db.entity.ItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemsDao {

    @Query("SELECT * FROM items ORDER BY name")
    fun getAll(): Flow<List<ItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ItemEntity): Long

    @Query("DELETE FROM items WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT * FROM items WHERE category = :category ORDER BY id DESC")
    fun getByCategory(category: String): Flow<List<ItemEntity>>
}