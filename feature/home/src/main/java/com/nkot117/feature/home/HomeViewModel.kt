package com.nkot117.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nkot117.core.common.toLocalDate
import com.nkot117.core.domain.model.DayType
import com.nkot117.core.domain.model.WeatherType
import com.nkot117.core.domain.usecase.dailynote.GetDailyNoteUseCase
import com.nkot117.core.domain.usecase.dailynote.SaveDailyNoteUseCase
import com.nkot117.core.domain.usecase.items.GetItemsToBringUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getItemsToBringUseCase: GetItemsToBringUseCase,
    private val getDailyNoteUseCase: GetDailyNoteUseCase,
    private val saveDailyNoteUseCase: SaveDailyNoteUseCase
) : ViewModel() {
    /**
     * UiState
     */
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun getChecklist() {
        viewModelScope.launch {
            val state = uiState.value
            val items = getItemsToBringUseCase(
                dayType = state.dayType,
                weatherType = state.weatherType,
                date = state.date
            )
            _uiState.update { it.copy(preview = items) }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun observeDailyNote() {
        viewModelScope.launch {
            uiState
                .map { it.date }
                .distinctUntilChanged()
                .flatMapLatest { date -> getDailyNoteUseCase(date) }
                .collect { note ->
                    _uiState.update { it.copy(dailyNote = note?.text.orEmpty()) }
                }
        }
    }

    fun setDayType(dayType: DayType) {
        _uiState.update { it.copy(dayType = dayType) }
    }

    fun setDate(selectedDate: Long) {
        val localDate = selectedDate.toLocalDate()
        _uiState.update { it.copy(date = localDate) }
    }

    fun setWeatherType(weatherType: WeatherType) {
        _uiState.update { it.copy(weatherType = weatherType) }
    }

    fun saveDailyNote(note: String) {
        viewModelScope.launch {
            val date = _uiState.value.date
            saveDailyNoteUseCase(date, note)
            _uiState.update { it.copy(dailyNote = note) }
        }
    }
}
