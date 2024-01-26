package com.example.weatherwise.presentation.search_places.view_model

import android.location.Geocoder
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherwise.domain.model.AutocompleteResult
import com.example.weatherwise.presentation.favourite_weather_places.events.FavouriteWeatherPlacesEvents
import com.example.weatherwise.presentation.search_places.events.SearchPlacesEvents
import com.example.weatherwise.presentation.utility.LocationUtils.centerOnLocation
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchPlacesViewModel @Inject constructor(
//    private val weatherUseCase: WeatherUseCase
) : ViewModel() {

    lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var placesClient: PlacesClient
    lateinit var geoCoder: Geocoder

    private val _uiEvents = Channel<SearchPlacesEvents>()
    val uiEvents = _uiEvents

    private val _searchText = mutableStateOf("")
    val searchText = _searchText

    val locationAutofill = mutableStateListOf<AutocompleteResult>()
    private var job: Job? = null

    var currentLatLong = mutableStateOf(LatLng(0.0, 0.0))



    fun searchAction(text:String){
        _searchText.value = text
    }

    fun searchPlaces(query: String) {
        job?.cancel()
        locationAutofill.clear()
        job = viewModelScope.launch {
            val request = FindAutocompletePredictionsRequest.builder().setQuery(query).build()
            placesClient.findAutocompletePredictions(request).addOnSuccessListener { response ->
                locationAutofill += response.autocompletePredictions.map {
                    println("Murda")
                    AutocompleteResult(
                        it.getFullText(null).toString(), it.placeId
                    )
                }
            }.addOnFailureListener {
                it.printStackTrace()
                println(it.cause)
                println(it.message)
            }
        }
    }

    fun getCoordinates(result: AutocompleteResult) {
        val placeFields = listOf(Place.Field.LAT_LNG)
        val request = FetchPlaceRequest.newInstance(result.placeId, placeFields)
        placesClient.fetchPlace(request).addOnSuccessListener {
            if (it != null) {
                currentLatLong.value = it.place.latLng!!
                sendUiEvent(SearchPlacesEvents.OnPinLocation(it.place.latLng))
            }
        }.addOnFailureListener {
            it.printStackTrace()
        }
    }
    fun sendUiEvent(event: SearchPlacesEvents) {
        viewModelScope.launch {
            _uiEvents.send(event)
        }
    }
}



