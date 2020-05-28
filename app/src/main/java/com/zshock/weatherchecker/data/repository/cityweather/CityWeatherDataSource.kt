package com.zshock.weatherchecker.data.repository.cityweather

import com.zshock.weatherchecker.domain.model.CityWeather

interface CityWeatherDataSource {

    fun getCityWeather(cityId: Long, callback: FetchCityWeatherCallback)

    interface FetchCityWeatherCallback {
        fun onCityWeatherFetched(cityWeather: CityWeather)
        fun onCityWeatherFetchFailed()
    }

}
