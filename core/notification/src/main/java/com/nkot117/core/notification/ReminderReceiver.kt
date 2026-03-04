package com.nkot117.core.notification

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.annotation.RequiresPermission
import com.nkot117.core.domain.usecase.reminder.ScheduleNextReminderUseCase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ReminderReceiver : BroadcastReceiver() {
    @Inject
    lateinit var reminderNotifier: ReminderNotifier

    @Inject
    lateinit var scheduleNextReminderUseCase: ScheduleNextReminderUseCase

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun onReceive(context: Context, intent: Intent) {
        // 通知を実施
        reminderNotifier.showReminder()

        // 次リマインダースケジュールを設定
        goAsync().also { pendingResult ->
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    scheduleNextReminderUseCase()
                } finally {
                    pendingResult.finish()
                }
            }
        }
    }
}
