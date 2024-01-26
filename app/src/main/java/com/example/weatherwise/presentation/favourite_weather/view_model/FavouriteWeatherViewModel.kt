package com.example.weatherwise.presentation.favourite_weather.view_model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherwise.data.local.dao.WeatherDao
import com.example.weatherwise.data.model.ForecastWeather
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteWeatherViewModel @Inject constructor(
    private val weatherDao: WeatherDao
):ViewModel() {

    init {
        getLocalForecastData()
    }
    private val _forecastWeatherList = mutableStateListOf<ForecastWeather>()
    val forecastWeatherList = _forecastWeatherList

    private val _myCountry = mutableStateOf("")
    val myCountry = _myCountry

    private val _myCity = mutableStateOf("")
    val myCity = _myCity

    private val _lat = mutableStateOf(0.0)
    val lat = _lat

    private val _lon = mutableStateOf(0.0)
    val lon = _lon


    private fun getLocalForecastData() {
        val forecastData = weatherDao.getForecastWeather()
        viewModelScope.launch {
            forecastData.collect{
                _forecastWeatherList.clear()
                _forecastWeatherList.addAll(it.list.filter { it.favourite })
                _myCountry.value = it.city.country
                _myCity.value = it.city.name
                _lat.value = it.city.coord.lat
                _lon.value = it.city.coord.lon
            }
        }
    }


}