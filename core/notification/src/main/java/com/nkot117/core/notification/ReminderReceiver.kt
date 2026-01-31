package com.nkot117.core.notification

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.annotation.RequiresPermission
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ReminderReceiver : BroadcastReceiver() {
    @Inject
    lateinit var reminderNotifier: ReminderNotifier
    lateinit var reminderScheduler: ReminderScheduler

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun onReceive(context: Context, intent: Intent) {
        // 通知を実施
        reminderNotifier.showReminder()

        // 次リマインダースケジュールを設定
        reminderScheduler.scheduleNextReminder(8, 0)
    }
}