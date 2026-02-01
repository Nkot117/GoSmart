package com.nkot117.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nkot117.core.domain.usecase.CancelReminderUseCase
import com.nkot117.core.domain.usecase.GetReminderTimeUseCase
import com.nkot117.core.domain.usecase.UpdateReminderTimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getReminderTimeUseCase: GetReminderTimeUseCase,
    private val updateReminderTimeUseCase: UpdateReminderTimeUseCase,
    private val cancelReminderUseCase: CancelReminderUseCase,
) : ViewModel() {

    /**
     * UiState
     */
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    fun fetchReminderSettings() {
        viewModelScope.launch {
            val reminder = getReminderTimeUseCase()
            _uiState.update {
                it.copy(
                    reminder = reminder
                )
            }
        }
    }

    fun setReminderEnabled(enabled: Boolean) {
        _uiState.update {
            it.copy(
                reminder = it.reminder.copy(
                    enabled = enabled
                )
            )
        }
    }

    fun setReminderTime(hour: Int, minute: Int) {
        _uiState.update {
            it.copy(
                reminder = it.reminder.copy(
                    hour = hour,
                    minute = minute
                )
            )
        }
    }

    fun saveSettings() {
        val state = uiState.value
        viewModelScope.launch {
            updateReminderTimeUseCase(
                hour = state.reminder.hour,
                minute = state.reminder.minute,
                enabled = state.reminder.enabled
            )

            if (!state.reminder.enabled) {
                cancelReminderUseCase()
            }
        }
    }
}