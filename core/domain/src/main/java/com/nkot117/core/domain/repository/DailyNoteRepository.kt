package com.nkot117.core.domain.repository

import com.nkot117.core.domain.model.DailyNote
import java.time.LocalDate

interface DailyNoteRepository {
    suspend fun getByDate(date: LocalDate): DailyNote?
    suspend fun deleteByDate(date: LocalDate)
    suspend fun update(note: DailyNote)
}