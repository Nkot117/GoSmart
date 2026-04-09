package com.nkot117.core.data.api.service

import com.nkot117.core.data.api.ApiClient
import io.ktor.client.request.parameter

class OpenMeteoApiService(private val apiClient: ApiClient) {
    suspend fun getWeather(latitude: Double, longitude: Double): String =
        apiClient.get("$BASE_URL$FORECAST_PATH") {
            parameter("latitude", latitude)
            parameter("longitude", longitude)
        }

    companion object {
        private const val BASE_URL = "https://api.open-meteo.com"
        private const val FORECAST_PATH = "/v1/forecast"
    }
}
