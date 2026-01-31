package com.nkot117.core.domain.repository

import com.nkot117.core.domain.model.ReminderTime

interface ReminderSettingsRepository {
    suspend fun getTime(): ReminderTime
    fun observeTime(): ReminderTime
    suspend fun saveTime(time: ReminderTime)
}