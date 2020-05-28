package com.zshock.weatherchecker.domain.usecase.city

import com.zshock.weatherchecker.data.repository.city.CityRepository
import com.zshock.weatherchecker.domain.model.City

class SearchCityUseCase(
    var queryString: String,
    var limit: Long,
    var callback: Callback
) {

    private var cityRepository = CityRepository.getInstance()

    fun execute() {
        cityRepository.searchByName(queryString, limit, 
            object : CityRepository.SearchByNameCallback {
            override fun onResponse(cities: List<City>) {
                callback.onResponse(cities)
            }
        })
    }

    interface Callback {
        fun onResponse(response: List<City>)
    }
}