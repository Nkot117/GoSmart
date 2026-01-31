package com.nkot117.core.notification.di

import com.nkot117.core.domain.repository.ReminderAlarmScheduler
import com.nkot117.core.notification.ReminderAlarmSchedulerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

class ReminderAlarmModule {
    @Module
    @InstallIn(SingletonComponent::class)
    abstract class ReminderAlarmModule {

        @Binds
        @Singleton
        abstract fun bindReminderAlarmScheduler(
            impl: ReminderAlarmSchedulerImpl,
        ): ReminderAlarmScheduler
    }
}