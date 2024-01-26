package com.example.weatherwise.presentation.favourite_weather_places.events

sealed class FavouriteWeatherPlacesEvents {
    data class OnLocationLoaded(val lat: Double,val lon:Double): FavouriteWeatherPlacesEvents()
}


