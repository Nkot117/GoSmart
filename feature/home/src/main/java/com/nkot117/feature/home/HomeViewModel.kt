package com.nkot117.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nkot117.core.domain.usecase.GenerateChecklistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val generateChecklistUseCase: GenerateChecklistUseCase
) : ViewModel() {
    /**
     * UiState
     */
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun getChecklist() {
        viewModelScope.launch {
            val state = uiState.value
            val items = generateChecklistUseCase(
                isWorkday = state.isWorkday,
                isRain = state.isRain,
                date = state.date
            )
            _uiState.update { it.copy(preview = items) }
        }
    }

    fun setWorkday(isWorkday: Boolean) {
        _uiState.update { it.copy(isWorkday = isWorkday) }
    }

    fun setRain(isRain: Boolean) {
        _uiState.update { it.copy(isRain = isRain) }
    }

    fun setDate(date: LocalDate) {
        _uiState.update { it.copy(date = date) }
    }
}