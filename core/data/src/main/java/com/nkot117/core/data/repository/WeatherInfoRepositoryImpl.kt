package com.nkot117.core.data.repository

import com.nkot117.core.data.api.openmeteo.OpenMeteoApiService
import com.nkot117.core.data.api.openmeteo.dto.OpenMeteoRequest
import com.nkot117.core.data.di.IODispatcher
import com.nkot117.core.domain.model.DailyWeatherInfo
import com.nkot117.core.domain.repository.WeatherInfoRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher

class WeatherInfoRepositoryImpl @Inject constructor(
    private val apiService: OpenMeteoApiService,
    @param:IODispatcher private val io: CoroutineDispatcher
) : WeatherInfoRepository {
    override suspend fun getCurrentLocationDailyWeatherInfo(
        latitude: Double,
        longitude: Double
    ): DailyWeatherInfo {
        val requestParams = OpenMeteoRequest(
            latitude = latitude,
            longitude = longitude,
            forecastDays = 1,
            daily = listOf("weather_code"),
            timezone = "Asia/Tokyo"
        )

        val response = apiService.getDailyWeatherInfo(requestParams)

        return DailyWeatherInfo(
            weatherCode = response.daily.weatherCode.firstOrNull() ?: 0
        )
    }
}
