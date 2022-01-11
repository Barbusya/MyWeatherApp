package com.barbusya.android.myweatherinfo

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.barbusya.android.myweatherinfo.api.MyWeatherApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val TAG = "MyWeatherFragment"

class MyWeatherFragment: Fragment() {

    private lateinit var myWeatherViewModel: MyWeatherViewModel
    private lateinit var cityLocation: TextView
    private lateinit var time: TextView
    private lateinit var temperature: TextView
    private lateinit var windSpeed: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

       myWeatherViewModel =
           ViewModelProvider(this).get(MyWeatherViewModel::class.java)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_my_weather, menu)

        val searchItem: MenuItem = menu.findItem(R.id.menu_item_search)
        val searchView = searchItem.actionView as SearchView

        searchView.apply {

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(queryText: String): Boolean {
                    Log.d(TAG, "QueryTextSubmit: $queryText")
                    myWeatherViewModel.fetchWeather(queryText)
                    return true
                }

                override fun onQueryTextChange(queryText: String): Boolean {
                    Log.d(TAG, "QueryTextChange: $queryText")
                    return false
                }
            })

            setOnSearchClickListener {
                searchView.setQuery(myWeatherViewModel.searchTerm, false)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) : Boolean {
        return when (item.itemId) {
            R.id.menu_item_clear -> {
                myWeatherViewModel.fetchWeather("")
                true
            } else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_weather,container, false)

        cityLocation = view.findViewById(R.id.location)
        time = view.findViewById(R.id.time)
        temperature = view.findViewById(R.id.temperature)
        windSpeed = view.findViewById(R.id.wind_speed)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myWeatherViewModel.weatherLiveData.observe(
            viewLifecycleOwner,
            Observer { it->
                cityLocation.setText(it[0]);
                time.setText(it[1]);
                temperature.setText(it[2] + "deg");
                windSpeed.setText(it[3] + "kph");
            })
    }

    companion object {
        fun newInstance() = MyWeatherFragment()
    }
}