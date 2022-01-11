package com.barbusya.android.myweatherinfo

import android.app.Application
import androidx.lifecycle.*

class MyWeatherViewModel(private val app: Application) : AndroidViewModel(app) {

    val weatherLiveData: LiveData<MutableList<String>>

    private val myWeatherFetcher = MyWeatherFetcher()
    private val mutableSearchTerm = MutableLiveData<String>()

    val searchTerm: String
        get() = mutableSearchTerm.value ?: ""

    init {
        mutableSearchTerm.value = QueryPreferences.getStoredQuery(app)

        weatherLiveData =
            Transformations.switchMap(mutableSearchTerm) { searchTerm ->
                if (searchTerm.isBlank()) {
                    myWeatherFetcher.fetchWeather()
                }else {
                    myWeatherFetcher.searchWeather(searchTerm)
                }
        }
    }

    fun fetchWeather(query: String ="") {
        QueryPreferences.setStoredQuery(app, query)
        mutableSearchTerm.value = query
    }
}