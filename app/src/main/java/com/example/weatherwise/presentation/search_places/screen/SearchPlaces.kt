package com.example.weatherwise.presentation.search_places.screen

import android.location.Geocoder
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherwise.R
import com.example.weatherwise.common.Constants.API_KEY
import com.example.weatherwise.presentation.search_places.view_model.SearchPlacesViewModel
import com.google.android.gms.location.LocationServices
import com.google.android.libraries.places.api.Places
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.weatherwise.presentation.ui.theme.KamiliDark

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

    Column(modifier = Modifier.fillMaxSize()) {
        Box() {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                uiSettings = uiSettings,
                onMapLongClick = {
                }
            ) {

            }
            Text(text = viewModel.locationAutofill.size.toString())
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = KamiliDark,
                    focusedIndicatorColor = KamiliDark
                ),
                textStyle = TextStyle.Default.copy(fontFamily = fontName),
                value = viewModel.searchText.value,
                onValueChange = {
                    viewModel.searchAction(it)
                    viewModel.searchPlaces(it)
                },
                modifier = Modifier
                    .padding(top = 108.dp, start = 8.dp, end = 8.dp)
                    .fillMaxWidth(),
                maxLines = 1
            )
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
//                items(viewModel.locationAutofill) {
//                    Row(modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(16.dp)
//                        .clickable {
//                            viewModel.searchText.value = it.address
//                            viewModel.locationAutofill.clear()
//                            viewModel.getCoordinates(it)
//                        }) {
//                        Text(it.address)
//                    }
//                }
            }
        }
        
    }
    
}