package com.zshock.weatherchecker.domain.usecase.cityweather

import com.zshock.weatherchecker.data.repository.cityweather.CityWeatherRepository
import com.zshock.weatherchecker.domain.model.CityWeather

class GetCityWeatherUseCase(var cityId: Long, var callback: Callback) {

    var cityWeatherRepository = CityWeatherRepository.getInstance()

    fun execute() {
        cityWeatherRepository.getCityWeather(cityId, object : CityWeatherRepository.GetCityWeatherCallback {
            override fun onResponse(cityWeather: CityWeather) {
                callback.onResponse(cityWeather)
            }

            override fun onError() {
                callback.onError()
            }
        })
    }

    interface Callback {
        fun onResponse(cityWeather: CityWeather)
        fun onError()
    }

}