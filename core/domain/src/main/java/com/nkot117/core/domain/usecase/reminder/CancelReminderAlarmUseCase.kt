package com.nkot117.core.domain.usecase.reminder

import com.nkot117.core.domain.repository.ReminderAlarmScheduler
import javax.inject.Inject

class CancelReminderAlarmUseCase @Inject constructor(
    private val alarmScheduler: ReminderAlarmScheduler,
) {
    /**
     * リマインダーのアラームをキャンセルするユースケース
     */
    operator fun invoke() {
        alarmScheduler.cancel()
    }
}