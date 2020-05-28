package com.zshock.weatherchecker.ui.detail

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.preference.PreferenceManager
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.GoogleMap
import com.google.android.libraries.maps.OnMapReadyCallback
import com.google.android.libraries.maps.model.LatLng
import com.google.android.libraries.maps.model.MarkerOptions
import com.zshock.weatherchecker.R
import com.zshock.weatherchecker.domain.model.City
import com.zshock.weatherchecker.domain.model.CityWeather
import com.zshock.weatherchecker.helper.SharedPreferencesHelper
import com.zshock.weatherchecker.helper.SharedPreferencesHelper.Companion.SP_TEMP_UNIT
import com.zshock.weatherchecker.helper.TemperatureHelper
import com.zshock.weatherchecker.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_city_weather_detail.*

class CityWeatherDetailFragment : BaseFragment(), CityWeatherContract.View, OnMapReadyCallback {

    override val layoutResId: Int
        get() = R.layout.fragment_city_weather_detail

    private lateinit var presenter: CityWeatherPresenter

    private var didFail = false
    private var didLoad = false
    private var curTempK: Double? = null
    private var minTempK: Double? = null
    private var maxTempK: Double? = null
    private var humidity: Int? = null
    private var pressure: Double? = null

    companion object {
        // Arguments
        const val TAG: String = "fragment_detail"
        const val ARGS_CITY_ID = "ARGS_CITY_ID"
        const val ARGS_CITY_COUNTRY_CODE = "ARGS_CITY_COUNTRY_CODE"
        const val ARGS_CITY_NAME = "ARGS_CITY_NAME"
        const val ARGS_CITY_LAT = "ARGS_CITY_LAT"
        const val ARGS_CITY_LNG = "ARGS_CITY_LNG"

        fun newInstance(city: City): CityWeatherDetailFragment {
            val b = Bundle()
            b.putLong(ARGS_CITY_ID, city.id)
            b.putString(ARGS_CITY_COUNTRY_CODE, city.countryCode)
            b.putString(ARGS_CITY_NAME, city.name)
            b.putDouble(ARGS_CITY_LAT, city.lat)
            b.putDouble(ARGS_CITY_LNG, city.lng)
            val frag = CityWeatherDetailFragment()
            frag.arguments = b
            return frag
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        PreferenceManager.getDefaultSharedPreferences(context)
            .registerOnSharedPreferenceChangeListener(preferenceChangeListener)

        presenter = CityWeatherPresenter()
        presenter.setView(this)

        if (savedInstanceState == null) {
            fetchWeather()
        } else {
            restoreSavedState(savedInstanceState)
            when {
                didLoad -> showWeather()
                didFail -> showError()
                else -> fetchWeather()
            }
        }

        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
    }

    private fun fetchWeather() {
        arguments?.let { args ->
            progressBar.visibility = View.VISIBLE
            presenter.getCityWeather(args.getLong(ARGS_CITY_ID))
        }

        progressBar.animate()
    }

    override fun onDestroy() {
        presenter.setView(null)
        PreferenceManager.getDefaultSharedPreferences(context)
            .unregisterOnSharedPreferenceChangeListener(preferenceChangeListener)
        super.onDestroy()
    }

    // GMaps Stuff
    override fun onMapReady(map: GoogleMap?) {
        map?.uiSettings?.isScrollGesturesEnabled = false

        arguments?.let { args ->
            val lat = args.getDouble(ARGS_CITY_LAT)
            val lng = args.getDouble(ARGS_CITY_LNG)
            val name = args.getString(ARGS_CITY_NAME)
            map?.addMarker(MarkerOptions().position(LatLng(lat, lng)).title(name))
            map?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat, lng), 10f))
        }
    }

    // Presenter responses
    override fun onCityWeatherResponse(cityWeather: CityWeather) {
        curTempK = cityWeather.temperature
        minTempK = cityWeather.minTemperature
        maxTempK = cityWeather.maxTemperature
        humidity = cityWeather.humidity
        pressure = cityWeather.pressure
        showWeather()
        didLoad = true
    }

    private fun showWeather() {
        val tempUnit = SharedPreferencesHelper.getInstance().getTemperatureUnit()
        curTempK?.let { currentTemperature.text = TemperatureHelper.toString(it, tempUnit) }
        minTempK?.let { minTemperature.text = TemperatureHelper.toString(it, tempUnit) }
        maxTempK?.let { maxTemperature.text = TemperatureHelper.toString(it, tempUnit) }
        minMaxSeparatorTextView.text = getString(R.string.separator_min_max_temperature)
        humidityTextView.text = getString(R.string.detail_humidity, humidity)
        pressureTextView.text = getString(R.string.detail_pressure, pressure)
        progressBar.visibility = View.GONE
        contentView.visibility = View.VISIBLE
    }

    override fun onCityWeatherLoadError() {
        showError()
        didFail = true
    }

    private fun showError() {
        progressBar.visibility = View.GONE
        contentView.visibility = View.GONE
        errorView.visibility = View.VISIBLE
    }

    // Preferences (Unit) changes
    private var preferenceChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
            if (key == SP_TEMP_UNIT) {
                updateTemperatureUnits(sharedPreferences.getString(key, null))
            }
        }

    private fun updateTemperatureUnits(tempUnit: String?) {
        curTempK?.let { currentTemperature.text = TemperatureHelper.toString(it, tempUnit) }
        minTempK?.let { minTemperature.text = TemperatureHelper.toString(it, tempUnit) }
        maxTempK?.let { maxTemperature.text = TemperatureHelper.toString(it, tempUnit) }
    }

    // Navigation stuff
    override fun getTitle(): String? {
        arguments?.let { args ->
            val name = args.getString(ARGS_CITY_NAME)
            val countryCode = args.getString(ARGS_CITY_COUNTRY_CODE)
            return "$name, $countryCode"
        }

        return null
    }

    // Instance
    private val INS_DID_LOAD: String = "INS_DID_LOAD"
    private val INS_DID_FAIL: String = "INS_DID_FAIL"
    private val INS_CUR_TEMP: String = "INS_CUR_TEMP"
    private val INS_MIN_TEMP: String = "INS_MIN_TEMP"
    private val INS_MAX_TEMP: String = "INS_MAX_TEMP"
    private val INS_HUMIDITY: String = "INS_HUMIDITY"
    private val INS_PRESSURE: String = "INS_PRESSURE"

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(INS_DID_LOAD, didLoad)
        if (didLoad) {
            curTempK?.let { outState.putDouble(INS_CUR_TEMP, it) }
            minTempK?.let { outState.putDouble(INS_MIN_TEMP, it) }
            maxTempK?.let { outState.putDouble(INS_MAX_TEMP, it) }
            humidity?.let { outState.putInt(INS_HUMIDITY, it) }
            pressure?.let { outState.putDouble(INS_PRESSURE, it) }
        } else {
            outState.putBoolean(INS_DID_FAIL, didFail)
        }
    }

    private fun restoreSavedState(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            didLoad = it.getBoolean(INS_DID_LOAD)
            if (didLoad) {
                curTempK = it.getDouble(INS_CUR_TEMP)
                minTempK = it.getDouble(INS_MIN_TEMP)
                maxTempK = it.getDouble(INS_MAX_TEMP)
                humidity = it.getInt(INS_HUMIDITY)
                pressure = it.getDouble(INS_PRESSURE)
            } else {
                didFail = it.getBoolean(INS_DID_FAIL)
            }
        }
    }

}