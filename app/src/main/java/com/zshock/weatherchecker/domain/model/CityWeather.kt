package com.zshock.weatherchecker.domain.model

class CityWeather {
    // Weather Data
    var temperature = 0.0
    var minTemperature = 0.0
    var maxTemperature = 0.0
    var pressure = 0.0
    var humidity: Int = 0

    // Coords
    var lat: Double = 0.0
    var lng: Double = 0.0
}