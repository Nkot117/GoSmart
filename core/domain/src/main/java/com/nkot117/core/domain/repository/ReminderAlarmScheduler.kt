package com.nkot117.core.domain.repository

interface ReminderAlarmScheduler {
    fun scheduleAt(hour: Int, minute: Int)

    fun cancel()
}
