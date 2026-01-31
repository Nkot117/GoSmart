package com.nkot117.core.domain.repository

import com.nkot117.core.domain.model.ReminderTime
import kotlinx.coroutines.flow.Flow

interface ReminderSettingsRepository {
    suspend fun getTime(): ReminderTime
    fun observeTime(): Flow<ReminderTime>
    suspend fun saveTime(time: ReminderTime)
}