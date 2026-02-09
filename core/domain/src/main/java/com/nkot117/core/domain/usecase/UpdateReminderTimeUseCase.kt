package com.nkot117.core.domain.usecase

import com.nkot117.core.domain.model.Reminder
import com.nkot117.core.domain.repository.ReminderAlarmScheduler
import com.nkot117.core.domain.repository.ReminderSettingsRepository
import javax.inject.Inject

class UpdateReminderTimeUseCase @Inject constructor(
    private val settingsRepository: ReminderSettingsRepository,
    private val alarmScheduler: ReminderAlarmScheduler,
) {
    /**
     * リマインダーの設定時間を更新するユースケース
     *
     * 指定された時間と有効状態でリマインダー設定を保存し、
     * アラームスケジューラーに新しい時間でのスケジュールを行う。
     *
     * @param hour リマインダーの時（0-23）
     * @param minute リマインダーの分（0-59）
     * @param enabled リマインダーが有効かどうか
     */
    suspend operator fun invoke(hour: Int, minute: Int, enabled: Boolean) {
        val reminderTime = Reminder(hour, minute, enabled)

        settingsRepository.saveTime(reminderTime)
        alarmScheduler.scheduleAt(hour, minute)
    }
}