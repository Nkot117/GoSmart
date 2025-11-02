package com.nkot117.core.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nkot117.core.data.db.entity.SpecialItemDatesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SpecialItemDateDao {
    @Query("SELECT itemId FROM special_item_dates WHERE date = :date")
    fun getItemIdsOnDate(date: String): Flow<List<Long>>

    @Query("SELECT date FROM special_item_dates WHERE itemId = :itemId ORDER BY date")
    fun getDatesForItem(itemId: Long): Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: SpecialItemDatesEntity
    )

    @Query("DELETE FROM special_item_dates WHERE itemId = :itemId AND date = :date")
    suspend fun deleteByItemAndDate(itemId: Long, date: String)
}