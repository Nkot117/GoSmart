package com.nkot117.core.domain.usecase

import com.nkot117.core.domain.repository.ReminderAlarmScheduler
import javax.inject.Inject

class CancelReminderUseCase @Inject constructor(
    private val alarmScheduler: ReminderAlarmScheduler,
) {
    operator fun invoke() {
        alarmScheduler.cancel()
    }
}