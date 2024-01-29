package com.example.weatherwise.presentation.favourite_weather.view_model

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherwise.data.local.dao.WeatherDao
import com.example.weatherwise.data.model.FavouriteWeatherDetailsEntity
import com.example.weatherwise.data.model.ForecastWeather
import com.example.weatherwise.domain.use_case.FavouriteWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteWeatherViewModel @Inject constructor(
    private val favouriteWeatherUseCase: FavouriteWeatherUseCase
):ViewModel() {

    init {
        getFavouriteWeatherDetails()
    }
    private val _favouriteWeatherList = mutableStateListOf<FavouriteWeatherDetailsEntity>()
    val favouriteWeatherList = _favouriteWeatherList

    private val _lat = mutableStateOf(0.0)
    val lat = _lat

    private val _lon = mutableStateOf(0.0)
    val lon = _lon


    private fun getFavouriteWeatherDetails() {
        viewModelScope.launch(){
            val favouriteWeatherDetails = favouriteWeatherUseCase.getFavouriteWeatherDetails()
            favouriteWeatherDetails.collect{
                _favouriteWeatherList.clear()
                _favouriteWeatherList.addAll(it)
            }
        }
    }

     fun deleteFavouriteWeather(timeStamp:String){
         viewModelScope.launch() {
             favouriteWeatherUseCase.deleteFavouriteWeather(timeStamp)
         }
    }


}