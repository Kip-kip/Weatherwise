package com.example.weatherwise.presentation.weather_display.events

sealed class WeatherDisplayEvents{
    data class OnMarkAsFavourite(val timeStamp: String, val lat: Double?, val lon:Double?,val temp:Double,val weatherKind:String): WeatherDisplayEvents()
}
