package com.nkot117.feature.home

import com.nkot117.core.domain.model.DayType
import com.nkot117.core.domain.model.Item
import com.nkot117.core.domain.model.WeatherType
import java.time.LocalDate
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class HomeUiState(
    val date: LocalDate = LocalDate.now(),
    val dayType: DayType = DayType.WORKDAY,
    val weatherType: WeatherType = WeatherType.SUNNY,
    val preview: ImmutableList<Item> = persistentListOf(),
    val dailyNote: String = ""
)
