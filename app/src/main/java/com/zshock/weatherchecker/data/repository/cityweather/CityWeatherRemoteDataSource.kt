package com.zshock.weatherchecker.data.repository.cityweather

import com.zshock.weatherchecker.data.network.Api
import com.zshock.weatherchecker.domain.model.CityWeather
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CityWeatherRemoteDataSource : CityWeatherDataSource {
    override fun getCityWeather(
        cityId: Long,
        callback: CityWeatherDataSource.FetchCityWeatherCallback
    ) {
        Api.getInstance().getWeatherForCity(cityId).enqueue(object : Callback<CityWeather> {
            override fun onResponse(call: Call<CityWeather>, response: Response<CityWeather>) {
                if (response.body() != null) {
                    callback.onCityWeatherFetched(response.body()!!)
                } else {
                    callback.onCityWeatherFetchFailed()
                }
            }

            override fun onFailure(call: Call<CityWeather>, t: Throwable) {
                callback.onCityWeatherFetchFailed()
            }
        })
    }

}