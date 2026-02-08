package com.nkot117.core.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.nkot117.core.data.db.entity.DailyNoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyNoteDao {

    @Query("SELECT * FROM daily_notes WHERE date = :date LIMIT 1")
    fun getByDate(date: String): Flow<DailyNoteEntity?>

    @Upsert
    suspend fun update(note: DailyNoteEntity)

    @Query("DELETE FROM daily_notes WHERE date = :date")
    suspend fun deleteByDate(date: String)
}