package com.zshock.weatherchecker.ui.detail

import com.zshock.weatherchecker.domain.model.CityWeather
import com.zshock.weatherchecker.ui.base.BasePresenter
import com.zshock.weatherchecker.ui.base.BaseView

interface CityWeatherContract {
    interface View : BaseView {
        fun onCityWeatherResponse(cityWeather: CityWeather)
        fun onCityWeatherLoadError()
    }

    interface Presenter : BasePresenter<View> {
        fun getCityWeather(cityId: Long)
    }
}