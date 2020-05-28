package com.zshock.weatherchecker.data.repository.city

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.zshock.weatherchecker.domain.model.City
import java.lang.reflect.Type

// This is the class I used to parse the data from OpenWeather and into the db
class CityDeserializer: JsonDeserializer<City> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): City {
        val jsonObject = json?.asJsonObject
        val city = City()

        jsonObject?.let { obj ->
            city.id = obj.get("id").asLong
            city.name = obj.get("name").asString
            city.countryCode = obj.get("countryCode").asString
            city.lat = obj.get("lat").asDouble
            city.lng = obj.get("lng").asDouble
        }

        return city
    }

}