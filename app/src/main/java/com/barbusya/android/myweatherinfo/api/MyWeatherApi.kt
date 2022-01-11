package com.barbusya.android.myweatherinfo.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MyWeatherApi {

    @GET("v1/current.json?")
    fun fetchWeather(@Query("q") query: String): Call<WeatherResponse>

    @GET("v1/current.json?")
    fun searchWeather(@Query("q") query: String) : Call<WeatherResponse>
}