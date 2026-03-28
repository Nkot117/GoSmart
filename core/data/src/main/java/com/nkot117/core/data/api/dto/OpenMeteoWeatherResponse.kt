package com.nkot117.core.data.api.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OpenMeteoWeatherResponse(
    val latitude: Double,
    val longitude: Double,
    @SerialName("generationtime_ms")
    val generationTimeMs: Double,
    @SerialName("utc_offset_seconds")
    val utcOffsetSeconds: Int,
    val timezone: String,
    @SerialName("timezone_abbreviation")
    val timezoneAbbreviation: String,
    @SerialName("current_units")
    val currentUnits: CurrentUnits,
    val current: Current
)

@Serializable
data class CurrentUnits(
    val time: String,
    @SerialName("weathercode")
    val weatherCode: String
)

@Serializable
data class Current(
    val time: String,
    @SerialName("weathercode")
    val weatherCode: Int
)
