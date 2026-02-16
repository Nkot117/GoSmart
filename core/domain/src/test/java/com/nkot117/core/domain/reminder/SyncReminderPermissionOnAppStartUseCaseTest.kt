package com.nkot117.core.domain.reminder

import com.nkot117.core.domain.model.Reminder
import com.nkot117.core.domain.usecase.reminder.SyncReminderPermissionOnAppStartUseCase
import com.nkot117.core.test.fake.FakeReminderAlarmScheduler
import com.nkot117.core.test.fake.FakeReminderSettingsRepository
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class SyncReminderPermissionOnAppStartUseCaseTest : FunSpec({
    lateinit var settingsRepository: FakeReminderSettingsRepository
    lateinit var alarmScheduler: FakeReminderAlarmScheduler
    lateinit var useCase: SyncReminderPermissionOnAppStartUseCase

    beforeTest {
        settingsRepository = FakeReminderSettingsRepository()
        alarmScheduler = FakeReminderAlarmScheduler()
        useCase = SyncReminderPermissionOnAppStartUseCase(settingsRepository, alarmScheduler)
        settingsRepository.seedReminder(Reminder(hour = 8, minute = 0, enabled = true))
    }

    context("リマインダー権限が付与されていない場合") {
        test("リマインダー設定が無効化され、アラームがキャンセルされること") {
            // Act
            useCase(permissionGranted = false)

            // Assert
            val current = settingsRepository.getTime()
            current.enabled shouldBe false
            alarmScheduler.cancelCount shouldBe 1
        }
    }


    context("リマインダー権限が付与されている場合") {
        test("リマインダー設定が変更されず、アラームもキャンセルされないこと") {
            // Act
            useCase(permissionGranted = true)

            // Assert
            val current = settingsRepository.getTime()
            current.enabled shouldBe true
            alarmScheduler.cancelCount shouldBe 0
        }
    }
})