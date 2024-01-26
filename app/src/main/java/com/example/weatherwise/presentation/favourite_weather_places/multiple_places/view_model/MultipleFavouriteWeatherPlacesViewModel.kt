package com.example.weatherwise.presentation.favourite_weather_places.multiple_places.view_model

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherwise.data.local.dao.WeatherDao
import com.example.weatherwise.presentation.favourite_weather_places.events.FavouriteWeatherPlacesEvents
import com.example.weatherwise.presentation.utility.LocationUtils
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationTokenSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MultipleFavouriteWeatherPlacesViewModel @Inject constructor(
    private val weatherDao: WeatherDao

) : ViewModel() {



    private val _uiEvents = Channel<FavouriteWeatherPlacesEvents>()
    val uiEvents = _uiEvents

    private val _locationList = mutableStateListOf<LatLng>()
    val locationList = _locationList


    @SuppressLint("MissingPermission")
    fun getCurrentActiveCoordinates(context: Context) {
        if (LocationUtils.isLocationEnabled(context)) {
            val fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(context)
            var result = fusedLocationProviderClient.getCurrentLocation(
                Priority.PRIORITY_BALANCED_POWER_ACCURACY,
                CancellationTokenSource().token
            )
            result.addOnCompleteListener {
                sendUiEvent(
                    FavouriteWeatherPlacesEvents.OnLocationLoaded(
                        it.result.latitude,
                        it.result.longitude
                    )
                )
            }

        } else {
            Toast.makeText(
                context,
                "It seems like your location is off. Please turn it on",
                Toast.LENGTH_LONG
            ).show()
        }


    }

    fun sendUiEvent(event: FavouriteWeatherPlacesEvents) {
        viewModelScope.launch {
            _uiEvents.send(event)
        }
    }


     fun loadLocations() {
         viewModelScope.launch {
             withContext(Dispatchers.IO) {
                 weatherDao.getFavouriteLocations().onEach {
                     _locationList.add(LatLng(it.lat, it.lon))
                 }
             }
         }
     }

    }


