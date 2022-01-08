package com.barbusya.android.myweatherinfo

import com.google.gson.annotations.SerializedName

data class WeatherItem(

    @SerializedName("temp_c")
    var temperature: Int =  0,

    @SerializedName("wind_kph")
    var wind: Float = 0f,
)
