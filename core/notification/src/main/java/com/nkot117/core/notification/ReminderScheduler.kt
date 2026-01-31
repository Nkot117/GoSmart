package com.nkot117.core.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.nkot117.core.common.DateTimeUtil
import com.nkot117.core.common.toEpochMillis
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ReminderScheduler @Inject constructor(
    @param:ApplicationContext private val context: Context,
) {
    fun scheduleNextReminder(hour: Int, minute: Int) {
        val now = DateTimeUtil.nowLocalDateTime()
        var nextSchedule = now
            .withHour(hour)
            .withMinute(minute)
            .withSecond(0)
            .withNano(0)

        if (nextSchedule.isBefore(now)) {
            nextSchedule = nextSchedule.plusDays(1)
        }

        val triggerAtMillis = nextSchedule.toEpochMillis()
        scheduleReminderAt(triggerAtMillis)
    }

    fun cancelReminder() {
        val intent = Intent(context, ReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            REQUEST_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }

    private fun scheduleReminderAt(triggerAtMillis: Long) {
        val intent = Intent(context, ReminderReceiver::class.java)

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            REQUEST_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmManager.setAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerAtMillis,
            pendingIntent
        )
    }

    companion object {
        private const val REQUEST_CODE = 1001
    }
}