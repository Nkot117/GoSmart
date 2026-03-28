package com.nkot117.core.domain.usecase.weather

import com.nkot117.core.domain.model.WeatherType
import com.nkot117.core.domain.repository.WeatherInfoRepository
import javax.inject.Inject

class GetCurrentLocationDailyWeatherTypeUseCase @Inject constructor(
    private val weatherInfoRepository: WeatherInfoRepository
) {
    /**
     * 現在地のその日の天気種別を取得するユースケース
     *
     * Repositoryから取得した日次天気情報のweatherCodeをもとに、
     * アプリ内で扱うWeatherTypeを返却する。
     *
     */
    suspend operator fun invoke(): WeatherType {
        // TODO: 端末から現在地の緯度経度を取得し、Repositoryに渡すように修正する
        val dailyWeatherInfo = weatherInfoRepository.getCurrentLocationDailyWeatherInfo(
            latitude = 0.0,
            longitude = 0.0
        )
        return dailyWeatherInfo.weatherCode.toWeatherType()
    }
}

private fun Int.toWeatherType(): WeatherType = when (this) {
    0, 1, 2 -> WeatherType.SUNNY
    else -> WeatherType.RAINY
}
