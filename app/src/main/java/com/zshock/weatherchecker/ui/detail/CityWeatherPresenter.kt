package com.zshock.weatherchecker.ui.detail

import android.content.SharedPreferences
import androidx.preference.Preference
import com.zshock.weatherchecker.domain.model.CityWeather
import com.zshock.weatherchecker.domain.usecase.cityweather.GetCityWeatherUseCase

class CityWeatherPresenter : CityWeatherContract.Presenter {

    var mvpView: CityWeatherContract.View? = null

    override fun setView(view: CityWeatherContract.View?) {
        mvpView = view
    }

    override fun getCityWeather(cityId: Long) {
        GetCityWeatherUseCase(cityId, object: GetCityWeatherUseCase.Callback {
            override fun onResponse(cityWeather: CityWeather) {
                mvpView?.onCityWeatherResponse(cityWeather)
            }

            override fun onError() {
                mvpView?.onCityWeatherLoadError()
            }
        }).execute()
    }

}