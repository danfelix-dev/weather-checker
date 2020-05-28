package com.zshock.weatherchecker.data.repository.cityweather

import com.zshock.weatherchecker.domain.model.CityWeather

class CityWeatherRepository(
    var localDataSource: CityWeatherLocalDataSource,
    var remoteDataSource: CityWeatherRemoteDataSource
) {
    companion object {
        private var INSTANCE: CityWeatherRepository? = null

        fun getInstance(): CityWeatherRepository {
            if (INSTANCE == null) {
                INSTANCE =
                    CityWeatherRepository(
                        CityWeatherLocalDataSource(),
                        CityWeatherRemoteDataSource()
                    )
            }
            return INSTANCE!!
        }
    }

    fun getCityWeather(cityId: Long, callback: GetCityWeatherCallback) {
        remoteDataSource.getCityWeather(cityId,
            object : CityWeatherDataSource.FetchCityWeatherCallback {
                override fun onCityWeatherFetched(cityWeather: CityWeather) {
                    callback.onResponse(cityWeather)
                }

                override fun onCityWeatherFetchFailed() {
                    callback.onError()
                }
            })
    }

    interface GetCityWeatherCallback {
        fun onResponse(cityWeather: CityWeather)
        fun onError()
    }
}