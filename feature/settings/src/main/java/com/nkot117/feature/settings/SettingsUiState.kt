package com.nkot117.feature.settings

import com.nkot117.core.domain.model.Reminder

data class SettingsUiState(val reminder: Reminder = Reminder(9, 0, false))
