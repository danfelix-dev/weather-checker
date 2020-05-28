package com.zshock.weatherchecker.data.network

import com.zshock.weatherchecker.domain.model.CityWeather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherClient {
    @GET("weather")
    fun getWeatherForCity(
        @Query("id") cityId: Long,
        @Query("appid") apiKey: String
    ): Call<CityWeather>
}