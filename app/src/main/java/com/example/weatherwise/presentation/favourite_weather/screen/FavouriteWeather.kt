package com.example.weatherwise.presentation.favourite_weather.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weatherwise.R
import com.example.weatherwise.common.SimpleAppBar
import com.example.weatherwise.presentation.favourite_weather.screen.components.WeatherItem
import com.example.weatherwise.presentation.favourite_weather.view_model.FavouriteWeatherViewModel


@Composable
fun FavouriteWeather(navController: NavController,
                     onPopBackStack: () -> Unit,
                     viewModel: FavouriteWeatherViewModel = hiltViewModel(),
                     OnFavPlaceClicked:(lat:Double,lon:Double)->Unit
) {

    val fontName = FontFamily(Font(R.font.raleway_regular))

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        SimpleAppBar(R.drawable.favourites, Color.Black, 0.1f) {
            onPopBackStack()
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier.fillMaxSize()
//        columns = GridCells.Fixed(2)
        ) {

            viewModel.forecastWeatherList?.let {
                items(it) { weatherList ->
                    val city = viewModel.myCity.value
                    val country =viewModel.myCountry.value
                    val lat = viewModel.lat.value
                    val lon = viewModel.lon.value
                    WeatherItem(navController,weatherList,city,country,lat,lon){lat,lon ->
                        OnFavPlaceClicked(lat,lon)
                    }
                }
            }
        }




    }
}