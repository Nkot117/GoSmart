package com.nkot117.core.test.fake

import com.nkot117.core.domain.repository.ReminderAlarmScheduler

class FakeReminderAlarmScheduler : ReminderAlarmScheduler {

    /**
     * スケジュール設定履歴を保持するためのリスト
     */
    val scheduledTimes = mutableListOf<Pair<Int, Int>>()

    /**
     * スケジュールキャンセルの回数をカウントするための変数
     */
    var cancelCount = 0

    override fun scheduleAt(hour: Int, minute: Int) {
        scheduledTimes.add(hour to minute)
    }

    override fun cancel() {
        cancelCount++
    }
}
