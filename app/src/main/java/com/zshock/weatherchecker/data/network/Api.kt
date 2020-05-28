package com.zshock.weatherchecker.data.network

import com.google.gson.GsonBuilder
import com.google.gson.internal.GsonBuildConfig
import com.zshock.weatherchecker.data.repository.cityweather.CityWeatherDeserializer
import com.zshock.weatherchecker.domain.model.CityWeather
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Api() {

    private var API_BASE_URL = "https://api.openweathermap.org/data/2.5/"
    private var API_KEY = "xxx"
    var client: OpenWeatherClient

    companion object {
        private var INSTANCE: Api? = null

        fun getInstance(): Api {
            if (INSTANCE == null) {
                INSTANCE = Api()
            }
            return INSTANCE!!
        }
    }

    init {
        val httpClient = OkHttpClient.Builder()

        val gsonBuilder = GsonBuilder()
        gsonBuilder.registerTypeAdapter(CityWeather::class.java, CityWeatherDeserializer())

        val builder: Retrofit.Builder = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
        val retrofit: Retrofit = builder.client(httpClient.build()).build()
        client = retrofit.create(OpenWeatherClient::class.java)
    }

    fun getWeatherForCity(cityId: Long): Call<CityWeather> {
        return client.getWeatherForCity(cityId, API_KEY)
    }
}