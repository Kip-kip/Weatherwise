package com.example.weatherwise.domain.repository

import com.example.weatherwise.data.model.CurrentWeatherDataEntity
import com.example.weatherwise.data.model.ForecastWeatherDataEntity
import kotlinx.coroutines.flow.Flow


interface WeatherRepository {
    suspend fun getCurrentWeather(lat:Double,lon:Double):  Flow<CurrentWeatherDataEntity>
    suspend fun getForecastWeather(lat:Double,lon:Double): Flow<ForecastWeatherDataEntity>
    suspend fun updateForecastWeatherFavoriteStatus(listId:Int)
    suspend fun insertFavouritePlace(lat: Double,lon: Double)

}