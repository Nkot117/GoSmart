package com.nkot117.core.domain.repository

import com.nkot117.core.domain.model.DailyNote
import java.time.LocalDate
import kotlinx.coroutines.flow.Flow

interface DailyNoteRepository {
    fun getByDate(date: LocalDate): Flow<DailyNote?>

    suspend fun deleteByDate(date: LocalDate)

    suspend fun update(note: DailyNote)
}
