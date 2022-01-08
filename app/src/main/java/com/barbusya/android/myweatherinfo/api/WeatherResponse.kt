package com.barbusya.android.myweatherinfo.api

import com.barbusya.android.myweatherinfo.LocationItem
import com.barbusya.android.myweatherinfo.WeatherItem
import com.google.gson.annotations.SerializedName

class WeatherResponse {

    @SerializedName("location")
    lateinit var currentLocationItem: LocationItem

    @SerializedName("current")
    lateinit var currentWeatherItems: WeatherItem
}