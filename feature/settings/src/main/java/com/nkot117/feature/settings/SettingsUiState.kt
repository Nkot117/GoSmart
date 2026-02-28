package com.nkot117.feature.settings

import com.nkot117.core.domain.model.Reminder

data class SettingsUiState(val reminder: Reminder = Reminder(9, 0, false))

data class SettingsActions(
    val onBack: () -> Unit = {},
    val onTapOssLicenses: () -> Unit = {},
    val onSettingSave: () -> Unit = {},
    val onToggleReminder: (Boolean) -> Unit = {},
    val onChangeReminderTime: (Int, Int) -> Unit = { _, _ -> }
)
