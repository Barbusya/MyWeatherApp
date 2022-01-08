package com.barbusya.android.myweatherinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class MyWeatherViewModel: ViewModel() {

    val weatherLiveData: LiveData<MutableList<String>>

    init {
        weatherLiveData = MyWeatherFetcher().fetchWeather()
    }
}