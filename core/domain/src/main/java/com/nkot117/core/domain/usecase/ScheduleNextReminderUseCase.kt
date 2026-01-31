package com.nkot117.core.domain.usecase

import com.nkot117.core.domain.repository.ReminderAlarmScheduler
import com.nkot117.core.domain.repository.ReminderSettingsRepository
import javax.inject.Inject

class ScheduleNextReminderUseCase @Inject constructor(
    private val settingsRepository: ReminderSettingsRepository,
    private val alarmScheduler: ReminderAlarmScheduler,
) {
    suspend operator fun invoke() {
        val reminderTime = settingsRepository.getTime()
        alarmScheduler.scheduleAt(reminderTime.hour, reminderTime.minute)
    }
}