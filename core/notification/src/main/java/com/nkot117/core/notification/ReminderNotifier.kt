package com.nkot117.core.notification

import android.Manifest
import android.R
import android.app.PendingIntent
import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ReminderNotifier @Inject constructor(@param:ApplicationContext private val context: Context) {
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun showReminder() {
        val intent = context.packageManager
            .getLaunchIntentForPackage(context.packageName)

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_dialog_info)
            .setContentTitle("外出前チェックの時間です")
            .setContentText("持ち物を確認しましょう！")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification)
    }

    companion object {
        const val CHANNEL_ID = "reminder_channel"
        const val NOTIFICATION_ID = 1
    }
}
