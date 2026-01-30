package com.nkot117.smartgo

import android.app.Application
import com.nkot117.core.notification.NotificationInitializer
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class SmartGoApp : Application() {
    @Inject
    lateinit var initializer: NotificationInitializer

    override fun onCreate() {
        super.onCreate()
        initializer.createNotificationChannel()
    }
}