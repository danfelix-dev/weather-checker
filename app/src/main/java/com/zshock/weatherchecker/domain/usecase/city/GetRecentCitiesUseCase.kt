package com.zshock.weatherchecker.domain.usecase.city

import com.zshock.weatherchecker.data.repository.city.CityRepository
import com.zshock.weatherchecker.domain.model.City

class GetRecentCitiesUseCase(var callback: Callback) {

    private var cityRepository = CityRepository.getInstance()

    fun execute() {
        cityRepository.getRecent(
            object : CityRepository.GetRecentCitiesCallback {
            override fun onResponse(cities: List<City>) {
                callback.onResponse(cities)
            }
        })
    }

    interface Callback {
        fun onResponse(response: List<City>)
    }
}