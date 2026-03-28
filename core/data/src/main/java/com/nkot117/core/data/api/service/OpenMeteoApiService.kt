package com.nkot117.core.data.api.service

import com.nkot117.core.data.api.ApiClient
import com.nkot117.core.data.api.dto.OpenMeteoWeatherResponse
import io.ktor.client.request.parameter

class OpenMeteoApiService(private val apiClient: ApiClient) {
    suspend fun getWeather(latitude: Double, longitude: Double): OpenMeteoWeatherResponse =
        apiClient.get("$BASE_URL$FORECAST_PATH") {
            parameter("latitude", latitude)
            parameter("longitude", longitude)
            parameter("current", "weather_code")
        }

    companion object {
        private const val BASE_URL = "https://api.open-meteo.com"
        private const val FORECAST_PATH = "/v1/forecast"
    }
}
