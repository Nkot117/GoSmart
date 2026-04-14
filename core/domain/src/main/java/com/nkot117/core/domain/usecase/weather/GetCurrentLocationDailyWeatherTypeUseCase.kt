package com.nkot117.core.domain.usecase.weather

import com.nkot117.core.domain.model.WeatherType
import com.nkot117.core.domain.repository.WeatherInfoRepository
import javax.inject.Inject

class GetCurrentLocationDailyWeatherTypeUseCase @Inject constructor(
    private val weatherInfoRepository: WeatherInfoRepository,
    private val getLocationUseCase: GetLocationUseCase
) {
    /**
     * 現在地のその日の天気種別を取得するユースケース
     *
     * Repositoryから取得した日次天気情報のweatherCodeをもとに、
     * アプリ内で扱うWeatherTypeを返却する。
     *
     */
    suspend operator fun invoke(): WeatherType {
        val location = getLocationUseCase()
        val dailyWeatherInfo = weatherInfoRepository.getCurrentLocationDailyWeatherInfo(
            latitude = location?.latitude ?: 0.0,
            longitude = location?.longitude ?: 0.0
        )
        return dailyWeatherInfo.weatherCode.toWeatherType()
    }
}

private fun Int.toWeatherType(): WeatherType = when (this) {
    0, 1, 2 -> WeatherType.SUNNY
    else -> WeatherType.RAINY
}
