package com.zshock.weatherchecker.data.repository.city

import com.zshock.weatherchecker.helper.SharedPreferencesHelper
import com.zshock.weatherchecker.domain.model.City
import io.realm.Case
import io.realm.Realm
import io.realm.kotlin.where

class CityLocalDataSource {

    fun getAll(callback: LoadCitiesCallback) {
        val realm = Realm.getDefaultInstance()
        val results = realm.where<City>().findAllAsync()
        results.addChangeListener { realmResults ->
            val items = realm.copyFromRealm(realmResults)
            realm.close()
            callback.onCitiesLoaded(items)
        }
    }

    fun getRecent(callback: LoadRecentCitiesCallback) {
        val recentCitiesIds =
            SharedPreferencesHelper.getInstance().getRecentCitiesIds().toTypedArray()
        val realm = Realm.getDefaultInstance()
        val results = realm.where<City>().`in`("id", recentCitiesIds).findAllAsync()
        results.addChangeListener { realmResults ->
            val unsortedItems = realm.copyFromRealm(realmResults)
            realm.close()
            val sortedItems = mutableListOf<City>()
            for (id in recentCitiesIds) {
                val city = unsortedItems.first { it.id == id }
                sortedItems.add(city)
                unsortedItems.remove(city)
            }
            callback.onRecentCitiesLoaded(sortedItems)
        }
    }

    fun searchByName(
        name: String,
        limit: Long,
        callback: SearchCitiesCallback
    ) {
        val realm = Realm.getDefaultInstance()
        val results = realm.where<City>().like("name", "${name}*", Case.INSENSITIVE).limit(limit)
            .findAllAsync()
        results.addChangeListener { realmResult ->
            callback.onSearchCitiesResult(realm.copyFromRealm(realmResult))
            realm.close()
        }
    }

    interface LoadCitiesCallback {
        fun onCitiesLoaded(cities: List<City>)
    }

    interface SearchCitiesCallback {
        fun onSearchCitiesResult(cities: List<City>)
    }

    interface LoadRecentCitiesCallback {
        fun onRecentCitiesLoaded(cities: List<City>)
    }

}
