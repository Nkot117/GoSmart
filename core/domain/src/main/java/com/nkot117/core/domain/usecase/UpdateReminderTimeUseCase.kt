package com.nkot117.core.domain.usecase

import com.nkot117.core.domain.model.ReminderTime
import com.nkot117.core.domain.repository.ReminderAlarmScheduler
import com.nkot117.core.domain.repository.ReminderSettingsRepository
import javax.inject.Inject

class UpdateReminderTimeUseCase @Inject constructor(
    private val settingsRepository: ReminderSettingsRepository,
    private val alarmScheduler: ReminderAlarmScheduler,
) {
    suspend operator fun invoke(hour: Int, minute: Int) {
        val reminderTime = ReminderTime(hour, minute)

        settingsRepository.saveTime(reminderTime)
        alarmScheduler.scheduleAt(hour, minute)
    }
}