package com.zshock.weatherchecker.data.repository.city

import com.zshock.weatherchecker.domain.model.City
import com.zshock.weatherchecker.helper.SharedPreferencesHelper

class CityRepository(
    var localDataSource: CityLocalDataSource,
    var remoteDataSource: CityRemoteDataSource
) {

    companion object {
        private var INSTANCE: CityRepository? = null

        fun getInstance(): CityRepository {
            if (INSTANCE == null) {
                INSTANCE =
                    CityRepository(
                        CityLocalDataSource(),
                        CityRemoteDataSource()
                    )
            }
            return INSTANCE!!
        }
    }

    fun searchByName(name: String, limit: Long, callback: SearchByNameCallback) {
        localDataSource.searchByName(name, limit,
            object : CityLocalDataSource.SearchCitiesCallback {
                override fun onSearchCitiesResult(cities: List<City>) {
                    callback.onResponse(cities)
                }
            })
    }

    fun getRecent(callback: GetRecentCitiesCallback) {
        localDataSource.getRecent(
            object : CityLocalDataSource.LoadRecentCitiesCallback {
            override fun onRecentCitiesLoaded(cities: List<City>) {
                callback.onResponse(cities)
            }
        })
    }

    fun removeCityFromRecent(city: City, callback: RemoveCityFromRecentCallback) {
        SharedPreferencesHelper.getInstance().removeRecentCity(city.id)
        callback.onCityRemoved(city)
    }

    fun addCityToRecent(city: City, callback: AddCityToRecentCallback) {
        SharedPreferencesHelper.getInstance().addRecentCity(city.id)
        callback.onCityAdded(city)
    }

    interface SearchByNameCallback {
        fun onResponse(cities: List<City>)
    }

    interface GetRecentCitiesCallback {
        fun onResponse(cities: List<City>)
    }

    interface AddCityToRecentCallback {
        fun onCityAdded(city: City)
    }

    interface RemoveCityFromRecentCallback {
        fun onCityRemoved(city: City)
    }
}
