package com.example.weatherwise.common

sealed class WeatherWiseUiEvents(){
    data class ShowSnackbar(val message: String) : WeatherWiseUiEvents()
    object OnLocationCheckCleared :WeatherWiseUiEvents()
}
