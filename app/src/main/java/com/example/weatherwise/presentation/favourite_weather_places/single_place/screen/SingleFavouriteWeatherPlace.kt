package com.example.weatherwise.presentation.favourite_weather_places.single_place.screen

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherwise.R
import com.example.weatherwise.presentation.favourite_weather_places.events.FavouriteWeatherPlacesEvents
import com.example.weatherwise.presentation.favourite_weather_places.single_place.view_model.SingleFavouriteWeatherPlaceViewModel
import com.example.weatherwise.presentation.utility.LocationUtils
import com.example.weatherwise.presentation.utility.LocationUtils.centerOnLocation
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import kotlinx.coroutines.flow.receiveAsFlow


@Composable
fun SingleFavouriteWeatherPlace(onPopBackStack: () -> Unit, viewModel: SingleFavouriteWeatherPlaceViewModel = hiltViewModel()){

    val uiSettings = remember {
        MapUiSettings(zoomControlsEnabled = false)
    }

    val context = LocalContext.current

    val cameraState = CameraPositionState()

//    val customIconBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.fav_place)
//    val scaledIconBitmap = Bitmap.createScaledBitmap(customIconBitmap, 120, 120, false)
//    val customIcon = BitmapDescriptorFactory.fromBitmap(scaledIconBitmap)


    LaunchedEffect(key1 = true){
        cameraState.centerOnLocation(LatLng(viewModel.lat,viewModel.lon))
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraState,
        uiSettings = uiSettings,
        onMapLongClick = {
        }
    ) {
            Marker(
                position = LatLng(viewModel.lat,viewModel.lon),
                title = LocationUtils.getLocationDetails(context,viewModel.lat,viewModel.lon),
                    snippet = "",
                    onInfoWindowLongClick = {
//
                    },
                    onClick = {
                        it.showInfoWindow()
                        true
                    },
                    icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)
                )


    }

}
