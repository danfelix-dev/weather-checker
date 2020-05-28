package com.zshock.weatherchecker.domain.usecase.city

import com.zshock.weatherchecker.data.repository.city.CityRepository
import com.zshock.weatherchecker.domain.model.City

class AddCityToRecentUseCase(var city: City, var callback: Callback) {

    private var cityRepository = CityRepository.getInstance()

    fun execute() {
        cityRepository.addCityToRecent(city,
            object : CityRepository.AddCityToRecentCallback {
            override fun onCityAdded(city: City) {
                callback.onCityAdded(city)
            }
        })
    }

    interface Callback {
        fun onCityAdded(city: City)
    }
}