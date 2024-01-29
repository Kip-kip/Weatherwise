package com.example.weatherwise.domain.use_case

import com.example.weatherwise.common.Resource
import com.example.weatherwise.data.model.CurrentWeatherDataEntity
import com.example.weatherwise.data.model.ForecastWeatherDataEntity
import com.example.weatherwise.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class WeatherUseCase @Inject constructor(
    private val repository: WeatherRepository,
) {


    fun getCurrentWeatherData(lat:Double,lon:Double): Flow<Resource<CurrentWeatherDataEntity>> = flow {
        try {
            emit(Resource.Loading(true))
            repository.getCurrentWeather(lat,lon).collect{currentWeather->
                emit(Resource.Success(currentWeather))
            }
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch(e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }

    }

    fun getForecastWeatherData(lat:Double,lon:Double): Flow<Resource<ForecastWeatherDataEntity>> = flow {
        try {
            emit(Resource.Loading(true))
            repository.getForecastWeather(lat,lon).collect{forecastWeather->
                emit(Resource.Success(forecastWeather))
            }
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch(e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }

    }



    }


