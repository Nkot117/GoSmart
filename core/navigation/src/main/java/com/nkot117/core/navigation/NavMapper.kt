package com.nkot117.core.navigation

import com.nkot117.core.domain.model.DayType
import com.nkot117.core.domain.model.WeatherType

fun DayType.toNav(): NavDayType =
    NavDayType.valueOf(name)

fun NavDayType.toDomain(): DayType =
    DayType.valueOf(name)

fun WeatherType.toNav(): NavWeatherType =
    NavWeatherType.valueOf(name)

fun NavWeatherType.toDomain(): WeatherType =
    WeatherType.valueOf(name)