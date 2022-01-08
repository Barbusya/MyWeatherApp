package com.barbusya.android.myweatherinfo

import com.google.gson.annotations.SerializedName

data class LocationItem(

    @SerializedName("name")
    var city: String = "",

    @SerializedName("localtime")
    var time: String = "",
)
