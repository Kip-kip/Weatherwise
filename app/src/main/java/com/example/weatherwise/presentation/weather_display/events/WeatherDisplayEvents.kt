package com.example.weatherwise.presentation.weather_display.events

sealed class WeatherDisplayEvents{
    data class OnMarkAsFavourite(val listId: Int, val lat: Double?, val lon:Double?): WeatherDisplayEvents()
}
