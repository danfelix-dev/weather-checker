package com.zshock.weatherchecker.data.repository.cityweather

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.zshock.weatherchecker.domain.model.CityWeather
import java.lang.reflect.Type

class CityWeatherDeserializer : JsonDeserializer<CityWeather> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): CityWeather {
        val jsonObject = json?.asJsonObject
        val cw = CityWeather()

        jsonObject?.let { obj ->
            obj.getAsJsonObject("coord")?.let { coord ->
                cw.lat = coord.get("lat").asDouble
                cw.lng = coord.get("lon").asDouble
            }

            obj.getAsJsonObject("main")?.let { main ->
                cw.temperature = main.get("temp").asDouble
                cw.minTemperature = main.get("temp_min").asDouble
                cw.maxTemperature = main.get("temp_max").asDouble
                cw.pressure = main.get("pressure").asDouble
                cw.humidity = main.get("humidity").asInt
            }
        }

        return cw
    }

}