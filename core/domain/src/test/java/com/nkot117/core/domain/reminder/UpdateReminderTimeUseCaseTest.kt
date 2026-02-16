package com.nkot117.core.domain.reminder

import com.nkot117.core.domain.model.Reminder
import com.nkot117.core.domain.usecase.reminder.UpdateReminderTimeUseCase
import com.nkot117.core.test.fake.FakeReminderAlarmScheduler
import com.nkot117.core.test.fake.FakeReminderSettingsRepository
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class UpdateReminderTimeUseCaseTest : FunSpec({
    lateinit var settingsRepository: FakeReminderSettingsRepository
    lateinit var alarmScheduler: FakeReminderAlarmScheduler
    lateinit var useCase: UpdateReminderTimeUseCase

    beforeTest {
        settingsRepository = FakeReminderSettingsRepository()
        alarmScheduler = FakeReminderAlarmScheduler()
        useCase = UpdateReminderTimeUseCase(settingsRepository, alarmScheduler)
    }

    context("リマインダー設定が有効の場合") {
        val hour = 9
        val minute = 30
        val enabled = true

        beforeTest {
            useCase(hour, minute, enabled)
        }

        test("アプリ内部の時間を更新する") {
            settingsRepository.getTime() shouldBe Reminder(hour, minute, enabled)
        }

        test("アラームをスケジュールする") {
            alarmScheduler.scheduledTimes shouldBe listOf(Pair(hour, minute))
            alarmScheduler.cancelCount shouldBe 0
        }
    }


    context("リマインダー設定が無効の場合") {
        val hour = 10
        val minute = 45
        val enabled = false

        beforeTest {
            useCase(hour, minute, enabled)
        }

        test("アプリ内部の時間を更新する") {
            settingsRepository.getTime() shouldBe Reminder(hour, minute, enabled)
        }

        test("アラームをキャンセルする") {
            alarmScheduler.cancelCount shouldBe 1
            alarmScheduler.scheduledTimes shouldBe emptyList()
        }
    }
})