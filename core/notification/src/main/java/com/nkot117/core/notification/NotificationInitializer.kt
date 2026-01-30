package com.nkot117.core.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class NotificationInitializer @Inject constructor(
    @param:ApplicationContext private val context: Context,
) {
    fun createNotificationChannel() {
        val channel =
            NotificationChannel(
                NotificationPublisher.CHANNEL_ID,
                "Daily Reminder",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "description"
            }

        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channel)
    }
}