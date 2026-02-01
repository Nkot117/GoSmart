package com.nkot117.core.domain.repository

import com.nkot117.core.domain.model.Reminder
import kotlinx.coroutines.flow.Flow

interface ReminderSettingsRepository {
    suspend fun getTime(): Reminder
    fun observeTime(): Flow<Reminder>
    suspend fun saveTime(time: Reminder)
}