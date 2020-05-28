package com.zshock.weatherchecker.data.repository.cityweather

import com.zshock.weatherchecker.domain.model.CityWeather

class CityWeatherLocalDataSource: CityWeatherDataSource {
    override fun getCityWeather(
        cityId: Long,
        callback: CityWeatherDataSource.FetchCityWeatherCallback
    ) {
        // TODO: Some kind of cache
    }
}