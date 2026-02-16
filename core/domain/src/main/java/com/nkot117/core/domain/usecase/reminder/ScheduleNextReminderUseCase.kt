package com.nkot117.core.domain.usecase.reminder

import com.nkot117.core.domain.repository.ReminderAlarmScheduler
import com.nkot117.core.domain.repository.ReminderSettingsRepository
import javax.inject.Inject

class ScheduleNextReminderUseCase @Inject constructor(
    private val settingsRepository: ReminderSettingsRepository,
    private val alarmScheduler: ReminderAlarmScheduler,
) {
    /**
     * 次回のリマインダーをスケジュールするユースケース
     *
     * 保存されているリマインダーの時間設定を取得し、
     * その時間に基づいて次回のリマインダーをスケジュールする。
     */
    suspend operator fun invoke() {
        val reminderTime = settingsRepository.getTime()

        if (reminderTime.enabled) {
            alarmScheduler.scheduleAt(reminderTime.hour, reminderTime.minute)
        } else {
            alarmScheduler.cancel()
        }
    }
}