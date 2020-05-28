package com.zshock.weatherchecker.domain.usecase.city

import com.zshock.weatherchecker.data.repository.city.CityRepository
import com.zshock.weatherchecker.domain.model.City

class RemoveCityFromRecentUseCase(var city: City, var callback: Callback) {

    private var cityRepository = CityRepository.getInstance()

    fun execute() {
        cityRepository.removeCityFromRecent(city,
            object : CityRepository.RemoveCityFromRecentCallback {
                override fun onCityRemoved(city: City) {
                    callback.onCityRemoved(city)
                }
            })
    }

    interface Callback {
        fun onCityRemoved(city: City)
    }
}