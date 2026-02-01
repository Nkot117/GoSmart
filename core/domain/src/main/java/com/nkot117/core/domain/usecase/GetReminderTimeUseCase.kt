package com.nkot117.core.domain.usecase

import com.nkot117.core.domain.model.Reminder
import com.nkot117.core.domain.repository.ReminderSettingsRepository
import javax.inject.Inject

class GetReminderTimeUseCase @Inject constructor(
    private val settingsRepository: ReminderSettingsRepository,
) {
    suspend operator fun invoke(): Reminder {
        return settingsRepository.getTime()
    }
}