package com.barbusya.android.myweatherinfo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.barbusya.android.myweatherinfo.api.MyWeatherApi
import com.barbusya.android.myweatherinfo.api.MyWeatherInterceptor
import com.barbusya.android.myweatherinfo.api.WeatherResponse
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val TAG = "MyWeatherFetcher"

class MyWeatherFetcher {

    private val myWeatherApi: MyWeatherApi

    init {
        val client = OkHttpClient.Builder()
            .addInterceptor(MyWeatherInterceptor())
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://api.weatherapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        myWeatherApi =  retrofit.create(MyWeatherApi::class.java)
    }

    fun fetchWeather(): LiveData<MutableList<String>> {
        return fetchWeatherMetadata(myWeatherApi.fetchWeather("Moscow"))
    }

    fun searchWeather(query: String): LiveData<MutableList<String>> {
        return fetchWeatherMetadata(myWeatherApi.searchWeather(query))
    }

    private fun fetchWeatherMetadata(myWeatherRequest: Call<WeatherResponse>):
            LiveData<MutableList<String>> {
        val responseLiveData: MutableLiveData<MutableList<String>> = MutableLiveData()

        myWeatherRequest.enqueue(object : Callback<WeatherResponse> {

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Log.e(TAG, "Failed to fetch weather", t)
            }

            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                Log.d(TAG, "Response received")
                val weatherResponse: WeatherResponse? = response.body()
                val locationItem: LocationItem? = weatherResponse?.currentLocationItem
                val weatherItem: WeatherItem? = weatherResponse?.currentWeatherItems
                responseLiveData.value = mutableListOf<String>(
                    locationItem?.component1().toString(),
                    locationItem?.component2().toString(),
                    weatherItem?.component1().toString(),
                    weatherItem?.component2().toString(),
                )
            }
        })

        return  responseLiveData
    }
}