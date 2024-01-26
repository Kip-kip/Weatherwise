package com.example.weatherwise.presentation.favourite_weather_places.multiple_places.screen

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherwise.R
import com.example.weatherwise.common.SimpleAppBar
import com.example.weatherwise.presentation.favourite_weather_places.events.FavouriteWeatherPlacesEvents
import com.example.weatherwise.presentation.favourite_weather_places.multiple_places.view_model.MultipleFavouriteWeatherPlacesViewModel
import com.example.weatherwise.presentation.utility.LocationUtils.centerOnLocation
import com.example.weatherwise.presentation.utility.LocationUtils.getLocationDetails
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import kotlinx.coroutines.flow.receiveAsFlow


@Composable
fun MultipleFavouriteWeatherPlaces(onPopBackStack: () -> Unit,
                                   viewModel: MultipleFavouriteWeatherPlacesViewModel = hiltViewModel()){

    val uiSettings = remember {
        MapUiSettings(zoomControlsEnabled = false)
    }
    val context = LocalContext.current
    val cameraState = CameraPositionState()
    var myCurLat = remember { mutableStateOf(0.0)}
    var myCurLon = remember { mutableStateOf(0.0)}



    LaunchedEffect(key1 = true) {
        viewModel.getCurrentActiveCoordinates(context)
        viewModel.loadLocations()
    }

    LaunchedEffect(key1 = true){
        viewModel.uiEvents.receiveAsFlow().collect(){
            when(it){
                is FavouriteWeatherPlacesEvents.OnLocationLoaded->{
                    myCurLat.value = it.lat
                    myCurLon.value = it.lon
                    cameraState.centerOnLocation(LatLng(it.lat,it.lon))
                }
                else -> {}
            }
        }
    }
Column(modifier = Modifier.fillMaxSize()) {
Box(){
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraState,
        uiSettings = uiSettings,
        onMapLongClick = {
        }
    ) {
        val newLocationList = viewModel.locationList.toMutableList().apply { add(0, LatLng(myCurLat.value,myCurLon.value)) }
        newLocationList.forEachIndexed { index,location ->

            val customIconBitmap = BitmapFactory.decodeResource(context.resources, if(index==0) R.drawable.fav_places else R.drawable.fav_place)
            val scaledIconBitmap = Bitmap.createScaledBitmap(customIconBitmap, if(index==0) 100 else 120, if(index==0) 100 else 120, false)
            val customIcon = BitmapDescriptorFactory.fromBitmap(scaledIconBitmap)

            Marker(
                position = location,
                title = if(index==0) "My Current Location" else getLocationDetails(context,location.latitude,location.longitude),
                snippet = "",
                onInfoWindowLongClick = {
                },
                onClick = {
                    it.showInfoWindow()
                    true
                },
                icon = customIcon
            )
        }

    }
    SimpleAppBar(R.drawable.fav_places, Color.Black, 0.1f) {

    }
}
}
}
