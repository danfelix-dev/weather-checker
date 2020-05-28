package com.zshock.weatherchecker.helper

import android.content.Context
import androidx.preference.PreferenceManager
import com.zshock.weatherchecker.WcApp

class SharedPreferencesHelper {

    companion object {
        private var instance: SharedPreferencesHelper? = null
        const val SP_DEFAULT = "SP_DEFAULT"
        const val SP_RECENT_CITIES = "SP_RECENT_CITIES"
        const val SP_TEMP_UNIT = "SP_TEMP_UNIT"

        fun getInstance(): SharedPreferencesHelper {
            if (instance == null) {
                instance =
                    SharedPreferencesHelper()
            }
            return instance!!
        }
    }

    fun getRecentCitiesIds(): List<Long> {
        val sp = WcApp.context.getSharedPreferences(SP_DEFAULT, Context.MODE_PRIVATE)
        val citiesString = sp.getString(SP_RECENT_CITIES, null)
        if (citiesString.isNullOrEmpty()) {
            return listOf()
        } else {
            return citiesString.split(",").map { it.toLong() }
        }
    }

    fun addRecentCity(id: Long) {
        val currentIds = getRecentCitiesIds().toMutableList()
        if (!currentIds.contains(id)) {
            currentIds.add(0, id)
            while (currentIds.size > 5) {
                currentIds.removeAt(5)
            }
        }

        val sp = WcApp.context.getSharedPreferences(SP_DEFAULT, Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString(SP_RECENT_CITIES, currentIds.joinToString(","))
        editor.apply()
    }

    fun removeRecentCity(id: Long) {
        val currentIds = getRecentCitiesIds().toMutableList()
        currentIds.remove(id)
        val sp = WcApp.context.getSharedPreferences(SP_DEFAULT, Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString(SP_RECENT_CITIES, currentIds.joinToString(","))
        editor.apply()
    }

    fun getTemperatureUnit(): String? {
        val defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(WcApp.context)
        return defaultSharedPreferences.getString(SP_TEMP_UNIT, null)
    }

}