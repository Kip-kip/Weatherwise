package com.example.weatherwise.presentation.weather_display.state

import com.example.weatherwise.data.model.CurrentWeatherDataEntity
import com.example.weatherwise.data.model.ForecastWeatherDataEntity


data class WeatherState(

    val currentWeatherIsLoading: Boolean = false,
    val currentWeather: CurrentWeatherDataEntity? = null,
    val currentWeatherError: String = "",
    val forecastWeatherIsLoading: Boolean = false,
    val forecastWeather: ForecastWeatherDataEntity? = null,
    val forecastWeatherError: String = "",
)
