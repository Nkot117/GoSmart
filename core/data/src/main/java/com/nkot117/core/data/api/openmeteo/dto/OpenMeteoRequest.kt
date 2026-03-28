package com.nkot117.core.data.api.openmeteo.dto

data class OpenMeteoRequest(
    val latitude: Double,
    val longitude: Double,
    val current: List<String> = listOf("weather_code"),
    val timezone: String = "Asia/Tokyo"
)
