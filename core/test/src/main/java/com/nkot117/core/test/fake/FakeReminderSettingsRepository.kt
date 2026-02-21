package com.nkot117.core.test.fake

import com.nkot117.core.domain.model.Reminder
import com.nkot117.core.domain.repository.ReminderSettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull

class FakeReminderSettingsRepository : ReminderSettingsRepository {
    /**
     * リマインダーの時間設定を保存するための内部状態
     */
    private val reminderTimeFlow = MutableStateFlow<Reminder?>(null)

    fun seedReminder(reminder: Reminder) {
        reminderTimeFlow.value = reminder
    }

    override suspend fun getTime(): Reminder =
        reminderTimeFlow.value ?: Reminder(hour = 8, minute = 0, enabled = false)

    override fun observeTime(): Flow<Reminder> = reminderTimeFlow.filterNotNull()

    override suspend fun saveTime(time: Reminder) {
        reminderTimeFlow.value = time
    }
}
