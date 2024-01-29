package com.example.weatherwise.presentation
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.weatherwise.common.Routes
import com.example.weatherwise.presentation.favourite_weather.screen.FavouriteWeather
import com.example.weatherwise.presentation.favourite_weather_places.multiple_places.screen.MultipleFavouriteWeatherPlaces
import com.example.weatherwise.presentation.favourite_weather_places.single_place.screen.SingleFavouriteWeatherPlace
import com.example.weatherwise.presentation.home.screen.WelcomeScreen
import com.example.weatherwise.presentation.home.screen.components.BottomNav
import com.example.weatherwise.presentation.search_places.screen.SearchPlaces
import com.example.weatherwise.presentation.utility.LocationUtils.hasLocationPermission
import com.example.weatherwise.presentation.utility.LocationUtils.isLocationEnabled
import com.example.weatherwise.presentation.ui.theme.KamiliDark
import com.weatherwise.presentation.ui.theme.WeatherWiseTheme
import com.example.weatherwise.presentation.weather_display.screen.WeatherDisplayScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherWiseTheme {
                val navController = rememberNavController()
                var isPermitted = false
                var showBottomBar by rememberSaveable { mutableStateOf(true) }
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val fontName = FontFamily(Font(com.example.weatherwise.R.font.raleway_regular))
                if (hasLocationPermission(this) && isLocationEnabled(this)){
                    isPermitted = true
                }

                Scaffold(
                    bottomBar = {
                        if (showBottomBar) {
                            BottomNav(
                                navController = navController,
                                fontName = fontName,
                                modifier = Modifier
                                    .background(KamiliDark)
                                    .fillMaxWidth()
                                    .padding(5.dp)

                            )
                        }
                    }

                ) {
                        NavHost(
                            navController = navController,
                            startDestination = if (isPermitted) Routes.WEATHER_SCREEN else Routes.WELCOME_SCREEN
                        ) {
                            composable(Routes.WELCOME_SCREEN) {
                                WelcomeScreen(navController, onPopBackStack = {
                                    navController.popBackStack()
                                }
                                )
                            }
                            composable(Routes.WEATHER_SCREEN) {
                                WeatherDisplayScreen(
                                )
                            }
                            composable(Routes.FAVOURITE_WEATHER) {
                                FavouriteWeather(navController,onPopBackStack = {
                                    navController.popBackStack()
                                }, OnFavPlaceClicked = {lat,lon ->
                                    navController.navigate(Routes.SINGLE_FAVOURIRE_WEATHER_PLACE+"/$lat/$lon")
                                }
                                )
                            }
                            composable(Routes.SINGLE_FAVOURIRE_WEATHER_PLACE+ "/{lat}/{lon}",
                                arguments = listOf(
                                navArgument(name = "lat") {
                                    type = NavType.FloatType
                                    defaultValue = -1
                                }, navArgument(name = "lon") {
                                        type = NavType.FloatType
                                        defaultValue = -1f // Example default value for lon
                                    }
                            )) {
                                SingleFavouriteWeatherPlace(onPopBackStack = {
                                    navController.popBackStack()
                                }
                                )
                            }
                            composable(Routes.MULTIPLE_FAVOURIRE_WEATHER_PLACES) {
                                MultipleFavouriteWeatherPlaces(onPopBackStack = {
                                    navController.popBackStack()
                                }
                                )
                            }
                            composable(Routes.SEARCH_PLACES) {
                                SearchPlaces(onPopBackStack = {
                                    navController.popBackStack()
                                }
                                )
                            }

                            }

                        }



                    }


            }
        }
    }




