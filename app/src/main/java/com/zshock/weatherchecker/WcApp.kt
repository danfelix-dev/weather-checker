package com.zshock.weatherchecker

import android.app.Application
import android.content.Context
import androidx.preference.PreferenceManager
import com.google.android.libraries.maps.MapsInitializer
import io.realm.Realm
import io.realm.RealmConfiguration

class WcApp: Application() {

    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = this.applicationContext

        // Initialize our default preferences
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        Realm.init(this)
        // I Made this realm compacted db based off the city list posted on the OpenWeather site
        // http://bulk.openweathermap.org/sample/city.list.json.gz
        val realmConfiguration = RealmConfiguration.Builder()
            .assetFile("default.realm")
            .build()
        Realm.setDefaultConfiguration(realmConfiguration)
    }
}