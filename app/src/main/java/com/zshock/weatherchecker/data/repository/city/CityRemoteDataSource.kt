package com.zshock.weatherchecker.data.repository.city

import com.zshock.weatherchecker.domain.model.City

class CityRemoteDataSource {

    fun downloadAll(callback: DownloadCitiesCallback) {
        // TODO: in case we want to download the city list
    }

    interface DownloadCitiesCallback {
        fun onCitiesDownloaded(cities: List<City>)
        fun onCitiesDownloadFailed()
    }

}
