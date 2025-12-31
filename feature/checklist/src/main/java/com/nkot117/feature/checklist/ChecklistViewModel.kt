package com.nkot117.feature.checklist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nkot117.core.domain.model.DayType
import com.nkot117.core.domain.model.WeatherType
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
class ChecklistViewModel @Inject constructor(
    private val generateChecklistUseCase: GenerateChecklistUseCase,
) : ViewModel() {

    /**
     * UiState
     */
    private val _uiState = MutableStateFlow(ChecklistUiState())
    val uiState: StateFlow<ChecklistUiState> = _uiState.asStateFlow()

    fun getChecklist(dayType: DayType, weatherType: WeatherType, date: LocalDate) {
        viewModelScope.launch {
            val items = generateChecklistUseCase(
                dayType = dayType,
                weatherType = weatherType,
                date = date
            )

            val checklist = items.map {
                ChecklistItem(
                    title = it.name,
                    checked = false
                )
            }

            _uiState.update { it.copy(checklist = checklist) }
        }
    }

}