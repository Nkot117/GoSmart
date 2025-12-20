package com.nkot117.feature.home

import com.nkot117.core.domain.model.DayType
import com.nkot117.core.domain.model.Item
import com.nkot117.core.domain.model.WeatherType
import java.time.LocalDate

data class HomeUiState(
    val date: LocalDate = LocalDate.now(),
    val dayType: DayType,
    val weatherType: WeatherType,
    val preview: List<Item> = emptyList(),
)
