package com.example.weatherwise.presentation.search_places.screen

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Geocoder
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherwise.R
import com.example.weatherwise.common.Constants.API_KEY
import com.example.weatherwise.presentation.favourite_weather_places.events.FavouriteWeatherPlacesEvents
import com.example.weatherwise.presentation.search_places.events.SearchPlacesEvents
import com.example.weatherwise.presentation.search_places.view_model.SearchPlacesViewModel
import com.google.android.gms.location.LocationServices
import com.google.android.libraries.places.api.Places
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.example.weatherwise.presentation.ui.theme.KamiliDark
import com.example.weatherwise.presentation.utility.LocationUtils.centerOnLocation
import com.example.weatherwise.presentation.utility.LocationUtils.getLocationDetails
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.Marker
import kotlinx.coroutines.flow.receiveAsFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchPlaces(onPopBackStack: () -> Unit,viewModel: SearchPlacesViewModel = hiltViewModel()){
    val fontName = FontFamily(Font(R.font.raleway_regular))
    val uiSettings = remember {
        MapUiSettings(zoomControlsEnabled = false)
    }
    val context = LocalContext.current


    viewModel.fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    Places.initialize(context, API_KEY)
    viewModel.placesClient = Places.createClient(context)
    viewModel.geoCoder = Geocoder(context)
    val cameraState = CameraPositionState()


    LaunchedEffect(key1 = true){
        viewModel.uiEvents.receiveAsFlow().collect(){
            when(it){
                is SearchPlacesEvents.OnPinLocation -> {it
                    cameraState.centerOnLocation(it.latLng)
                }
                else -> {}
            }
        }
    }

    Column() {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                uiSettings = uiSettings,
                cameraPositionState = cameraState,
                onMapClick = {

                }){
                val customIconBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.fav_places)
                val scaledIconBitmap = Bitmap.createScaledBitmap(customIconBitmap, 100 ,  100 , false)
                val customIcon = BitmapDescriptorFactory.fromBitmap(scaledIconBitmap)

                Marker(
                    position = viewModel.currentLatLong.value,
                    title = getLocationDetails(context,viewModel.currentLatLong.value.latitude,viewModel.currentLatLong.value.longitude),
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


            Surface(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(8.dp)
                    .fillMaxWidth(),
                color = Color.White,
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AnimatedVisibility(
                        viewModel.locationAutofill.isNotEmpty(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(viewModel.locationAutofill) {
                                Row(modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .clickable() {
                                        viewModel.searchText.value = it.address
                                        viewModel.locationAutofill.clear()
                                        viewModel.getCoordinates(it)
                                    }) {
                                    Text(it.address)
                                }
                            }
                        }
                        Spacer(Modifier.height(16.dp))
                    }
                    OutlinedTextField(
                        value = viewModel.searchText.value, onValueChange = {
                            viewModel.searchAction(it)
                            viewModel.searchPlaces(it)
                        }, modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                }
            }
        }
    }

}