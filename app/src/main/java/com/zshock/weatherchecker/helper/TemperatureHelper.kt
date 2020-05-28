package com.zshock.weatherchecker.helper

class TemperatureHelper {

    companion object {

        fun toString(temperature: Double, tempUnit: String?): String {
            return when (tempUnit) {
                "metric" -> toCelsius(temperature)
                "imperial" -> toFahrenheit(temperature)
                else -> toKelvin(temperature)
            }
        }

        private fun toCelsius(kelvin: Double): String {
            val celsius: Double = kelvin - 273.15
            return String.format("%.0f°C", celsius)
        }

        private fun toFahrenheit(kelvin: Double): String {
            val fahrenheit: Double = (kelvin - 273.15) * 9 / 5 + 32
            return String.format("%.0f°F", fahrenheit)
        }

        private fun toKelvin(kelvin: Double): String {
            return String.format("%.0fK", kelvin)
        }

    }
}