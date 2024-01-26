package com.example.weatherwise.presentation.favourite_weather_places.single_place.view_model

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherwise.presentation.favourite_weather_places.events.FavouriteWeatherPlacesEvents
import com.example.weatherwise.presentation.utility.LocationUtils
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

class SingleFavouriteWeatherPlaceViewModel(savedStateHandle: SavedStateHandle):ViewModel() {


    private val _uiEvents = Channel<FavouriteWeatherPlacesEvents>()
    val uiEvents = _uiEvents

    val lat: Double = savedStateHandle.get<Double>("lat")!!
    val lon: Double = savedStateHandle.get<Double>("lon")!!


}