package com.zshock.weatherchecker.ui.search

import com.zshock.weatherchecker.domain.model.City
import com.zshock.weatherchecker.ui.base.BasePresenter
import com.zshock.weatherchecker.ui.base.BaseView

interface SearchContract {
    interface View : BaseView {
        fun onCitySearchResponse(response: List<City>)
        fun onRecentCitiesLoaded(response: List<City>)
        fun onCityRemovedFromRecent(cityId: Long)
        fun onCityAddedToRecent(city: City)
    }

    interface Presenter : BasePresenter<View> {
        fun searchFor(queryString: String)
        fun getRecent()
        fun removeCityFromRecents(city: City)
        fun addCityToRecent(city: City)
    }
}
