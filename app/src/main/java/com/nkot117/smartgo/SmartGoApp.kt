package com.nkot117.smartgo

import android.app.Application
import com.nkot117.core.notification.ReminderChannelInitializer
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class SmartGoApp : Application() {
    @Inject
    lateinit var reminderChannelInitializer: ReminderChannelInitializer

    override fun onCreate() {
        super.onCreate()
        reminderChannelInitializer.createReminderChannel()
    }
}
