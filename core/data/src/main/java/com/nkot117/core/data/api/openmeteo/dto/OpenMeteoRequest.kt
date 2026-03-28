package com.nkot117.core.data.api.openmeteo.dto

data class OpenMeteoRequest(
    val latitude: Double,
    val longitude: Double,
    val forecastDays: Int = 1,
    val daily: List<String> = listOf("weather_code"),
    val timezone: String = "Asia/Tokyo"
)
