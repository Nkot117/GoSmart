package com.nkot117.core.domain.usecase.reminder

import com.nkot117.core.domain.repository.ReminderAlarmScheduler
import com.nkot117.core.domain.repository.ReminderSettingsRepository
import javax.inject.Inject

class SyncReminderPermissionOnAppStartUseCase @Inject constructor(
    private val settingsRepository: ReminderSettingsRepository,
    private val alarmScheduler: ReminderAlarmScheduler,
) {
    /**
     * アプリ起動時にリマインダー権限の状態を同期するユースケース
     *
     * リマインダー権限が付与されていない場合に、
     * 保存されているリマインダー設定を確認し、もし有効になっていれば
     * 無効化してアラームをキャンセルする。
     *
     * @param permissionGranted リマインダー権限が付与されているかどうか
     */
    suspend operator fun invoke(permissionGranted: Boolean) {
        if (permissionGranted) return

        val current = settingsRepository.getTime()
        if (!current.enabled) return

        settingsRepository.saveTime(current.copy(enabled = false))
        alarmScheduler.cancel()
    }
}