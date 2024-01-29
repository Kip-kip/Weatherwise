package com.example.weatherwise.presentation.weather_display.view_model

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherwise.common.Resource
import com.example.weatherwise.common.WeatherWiseUiEvents
import com.example.weatherwise.domain.use_case.FavouriteWeatherUseCase
import com.example.weatherwise.domain.use_case.WeatherUseCase
import com.example.weatherwise.presentation.utility.LocationUtils.isLocationEnabled
import com.example.weatherwise.presentation.weather_display.events.WeatherDisplayEvents
import com.example.weatherwise.presentation.weather_display.state.WeatherState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class WeatherDisplayViewModel @Inject constructor(
    private val weatherUseCase: WeatherUseCase,
    private val favouriteWeatherUseCase: FavouriteWeatherUseCase
) : ViewModel() {

    private val _uiEvents = Channel<WeatherWiseUiEvents>()
    val uiEvents = _uiEvents
    private val _state = mutableStateOf(WeatherState())
    var state: State<WeatherState> = _state

    private val _myAddress = mutableStateOf("")
    val myAddress = _myAddress

    private val _myCountry = mutableStateOf("")
    val myCountry = _myCountry

    private val _myCity = mutableStateOf("")
    val myCity = _myCity

    private val _lat = mutableStateOf("")
    val lat = _lat
    private val _lon = mutableStateOf("")
    val lon = _lon

    private var _listOfFavTimestamps = listOf("")
    val listOfFavTimestamps = _listOfFavTimestamps


    init {
    }

    @SuppressLint("MissingPermission")
    fun getLocationCurrentCoordinates(context: Context){
        if (isLocationEnabled(context)){
            val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
            var result = fusedLocationProviderClient.getCurrentLocation(Priority.PRIORITY_BALANCED_POWER_ACCURACY,
                CancellationTokenSource().token)
            result.addOnCompleteListener {
                _lat.value = it.result.latitude.toString()
                _lon.value = it.result.longitude.toString()
                getCurrentWeatherData(it.result.latitude,it.result.longitude)
                getForecastWeatherData(it.result.latitude,it.result.longitude)
                getLocationDetails(context,it.result.latitude,it.result.longitude)
            }


        }else{
            sendUiEvent(WeatherWiseUiEvents.ShowSnackbar("It seems like your location is off. Please turn it on"))
        }
    }


    fun getLocationDetails(context: Context,lat: Double,lon: Double) {
        try {
            val geocoder = Geocoder(context, Locale.getDefault())
            val address = geocoder.getFromLocation(lat, lon, 3)
            if (address != null) {
                _myAddress.value = address[0].getAddressLine(0)
                _myCountry.value = address[0].countryName
                _myCity.value = address[0].locality
            }
        }
        catch (e:Exception){

        }
    }

    private fun getCurrentWeatherData(lat:Double,lon:Double) {
        weatherUseCase.getCurrentWeatherData(lat,lon).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _state.value = WeatherState(currentWeatherIsLoading = true)
                }

                is Resource.Success -> {
                    _state.value = WeatherState(
                        currentWeather = result.data ?: null,
                        currentWeatherIsLoading = false
                    )
                }

                is Resource.Error -> {
                    sendUiEvent(
                        WeatherWiseUiEvents.ShowSnackbar(
                            result.message ?: "Error Occurred"
                        )
                    )
                    _state.value = WeatherState(currentWeatherIsLoading = false)
                }

                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    private fun getForecastWeatherData(lat:Double,lon:Double) {
        weatherUseCase.getForecastWeatherData(lat,lon).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _state.value = _state.value.copy(forecastWeatherIsLoading = true)
                }

                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        forecastWeather = result.data ?: null,
                        forecastWeatherIsLoading = false
                    )
                }

                is Resource.Error -> {
                    sendUiEvent(
                        WeatherWiseUiEvents.ShowSnackbar(
                            result.message ?: "An error occurred"
                        )
                    )
                    _state.value = _state.value.copy(forecastWeatherIsLoading = false)
                }

                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    fun receiveUIEvents(event: WeatherDisplayEvents) {
        when (event) {
            is WeatherDisplayEvents.OnMarkAsFavourite -> {
                viewModelScope.launch {
                    favouriteWeatherUseCase.markAsFavourite(event.timeStamp,event.lat,event.lon,event.temp,event.weatherKind)
                }
            }
        }

    }
    fun sendUiEvent(event: WeatherWiseUiEvents) {
        viewModelScope.launch {
            _uiEvents.send(event)
        }
    }
}



