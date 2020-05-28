package com.zshock.weatherchecker.domain.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class City : RealmObject() {
    @PrimaryKey
    var id: Long = 0
    var name: String = ""
    var lng: Double = 0.0
    var lat: Double = 0.0
    var countryCode: String? = null
}