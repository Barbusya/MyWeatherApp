package com.barbusya.android.myweatherinfo.api

import retrofit2.Call
import retrofit2.http.GET

interface MyWeatherApi {

    @GET("v1/current.json?key= 971bcc646b804390ba3105555220401 &q=London&aqi=no")
    fun fetchWeather(): Call<WeatherResponse>
}