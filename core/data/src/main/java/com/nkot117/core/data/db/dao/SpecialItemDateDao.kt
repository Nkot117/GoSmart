package com.nkot117.core.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nkot117.core.data.db.entity.ItemEntity
import com.nkot117.core.data.db.entity.SpecialItemDatesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SpecialItemDateDao {
    @Query("SELECT itemId FROM special_item_dates WHERE date = :date")
    fun getItemIdsOnDate(date: String): Flow<List<Long>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: SpecialItemDatesEntity)

    @Query(
        """
        SELECT i.* FROM items i
        INNER JOIN special_item_dates s ON s.itemId = i.id
        WHERE s.date = :date
        ORDER BY i.id DESC
    """
    )
    fun getItemsByDate(date: String): Flow<List<ItemEntity>>
}