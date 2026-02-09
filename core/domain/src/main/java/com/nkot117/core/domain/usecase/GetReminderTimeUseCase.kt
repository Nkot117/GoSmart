package com.nkot117.core.domain.usecase

import com.nkot117.core.domain.model.Reminder
import com.nkot117.core.domain.repository.ReminderSettingsRepository
import javax.inject.Inject

class GetReminderTimeUseCase @Inject constructor(
    private val settingsRepository: ReminderSettingsRepository,
) {
    /**
     * リマインダーの設定時間を取得するユースケース
     *
     * @return 保存されているリマインダーの時間設定。未設定の場合はデフォルト値を返す。
     */
    suspend operator fun invoke(): Reminder {
        return settingsRepository.getTime()
    }
}