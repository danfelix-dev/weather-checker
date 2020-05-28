package com.zshock.weatherchecker.ui.search

import com.zshock.weatherchecker.domain.model.City
import com.zshock.weatherchecker.domain.usecase.city.AddCityToRecentUseCase
import com.zshock.weatherchecker.domain.usecase.city.GetRecentCitiesUseCase
import com.zshock.weatherchecker.domain.usecase.city.RemoveCityFromRecentUseCase
import com.zshock.weatherchecker.domain.usecase.city.SearchCityUseCase

class SearchPresenter : SearchContract.Presenter {

    private var mvpView: SearchContract.View? = null

    override fun setView(view: SearchContract.View?) {
        this.mvpView = view
    }

    override fun searchFor(queryString: String) {
        SearchCityUseCase(
            queryString,
            10,
            object :
                SearchCityUseCase.Callback {
                override fun onResponse(response: List<City>) {
                    mvpView?.onCitySearchResponse(response)
                }
            }).execute()
    }

    override fun getRecent() {
        GetRecentCitiesUseCase(object : GetRecentCitiesUseCase.Callback,
            SearchCityUseCase.Callback {
            override fun onResponse(response: List<City>) {
                mvpView?.onRecentCitiesLoaded(response)
            }
        }).execute()
    }

    override fun removeCityFromRecents(city: City) {
        RemoveCityFromRecentUseCase(city, object : RemoveCityFromRecentUseCase.Callback {
            override fun onCityRemoved(city: City) {
                mvpView?.onCityRemovedFromRecent(city.id)
            }
        }).execute()
    }

    override fun addCityToRecent(city: City) {
        AddCityToRecentUseCase(city, object : AddCityToRecentUseCase.Callback {
            override fun onCityAdded(city: City) {
                mvpView?.onCityAddedToRecent(city)
            }
        }).execute()
    }
}