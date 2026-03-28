package com.nkot117.core.domain.repository

import com.nkot117.core.domain.model.DailyWeatherInfo

interface WeatherInfoRepository {
    suspend fun getCurrentLocationDailyWeatherInfo(
        latitude: Double,
        longitude: Double
    ): DailyWeatherInfo
}
