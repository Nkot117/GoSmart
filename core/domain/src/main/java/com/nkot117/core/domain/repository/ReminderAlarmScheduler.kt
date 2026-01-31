package com.nkot117.core.domain.repository

interface ReminderAlarmScheduler {
    fun scheduleAt(triggerAtMillis: Long)
    fun cancel()
}