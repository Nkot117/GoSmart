package com.nkot117.core.domain.reminder

import com.nkot117.core.domain.model.Reminder
import com.nkot117.core.domain.usecase.reminder.ScheduleNextReminderUseCase
import com.nkot117.core.test.fake.FakeReminderAlarmScheduler
import com.nkot117.core.test.fake.FakeReminderSettingsRepository
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class ScheduleNextReminderUseCaseTest : FunSpec({
    lateinit var settingsRepository: FakeReminderSettingsRepository
    lateinit var alarmScheduler: FakeReminderAlarmScheduler
    lateinit var useCase: ScheduleNextReminderUseCase

    beforeTest {
        settingsRepository = FakeReminderSettingsRepository()
        alarmScheduler = FakeReminderAlarmScheduler()
        useCase = ScheduleNextReminderUseCase(
            settingsRepository = settingsRepository,
            alarmScheduler = alarmScheduler,
        )
    }

    test("リマインダー設定が有効の場合、次のリマインダーをスケジュールする") {
        // Arrange
        settingsRepository.seedReminder(
            Reminder(
                enabled = true,
                hour = 8,
                minute = 30,
            )
        )

        // Act
        useCase()

        // Assert
        alarmScheduler.scheduledTimes shouldBe listOf(Pair(8, 30))
        alarmScheduler.cancelCount shouldBe 0
    }

    test("リマインダー設定が無効の場合、アラームをキャンセルする") {
        // Arrange
        settingsRepository.seedReminder(
            Reminder(
                enabled = false,
                hour = 8,
                minute = 30,
            )
        )

        // Act
        useCase()

        // Assert
        alarmScheduler.scheduledTimes shouldBe emptyList()
        alarmScheduler.cancelCount shouldBe 1
    }
})