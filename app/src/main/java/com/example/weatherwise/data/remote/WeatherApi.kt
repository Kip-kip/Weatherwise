package com.example.weatherwise.data.remote

import com.example.weatherwise.data.model.CurrentWeatherDataEntity
import com.example.weatherwise.data.model.ForecastWeatherDataEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(@Query("lat") lat: Double,@Query("lon") lon: Double,@Query("appid") appid: String): CurrentWeatherDataEntity


    @GET("data/2.5/forecast")
    suspend fun getForecastWeather(@Query("lat") lat: Double,@Query("lon") lon: Double,@Query("appid") appid: String): ForecastWeatherDataEntity

}