package com.example.weatherwise.data.repository

import android.accounts.NetworkErrorException
import com.example.weatherwise.data.model.City
import com.example.weatherwise.data.model.Clouds
import com.example.weatherwise.data.model.Coord
import com.example.weatherwise.data.model.CurrentWeatherDataEntity
import com.example.weatherwise.data.model.FavouriteWeatherDetailsEntity
import com.example.weatherwise.data.model.ForecastWeather
import com.example.weatherwise.data.model.ForecastWeatherDataEntity
import com.example.weatherwise.data.model.ForecastWeatherMain
import com.example.weatherwise.data.model.ForecastWeatherSys
import com.example.weatherwise.data.model.ForecastWeatherWind
import com.example.weatherwise.data.model.Main
import com.example.weatherwise.data.model.Rain
import com.example.weatherwise.data.model.Sys
import com.example.weatherwise.data.model.Weather
import com.example.weatherwise.data.model.Wind
import com.example.weatherwise.data.repository.Utility.getFakeCurrentWeather
import com.example.weatherwise.data.repository.Utility.getFakeFavouriteWeather
import com.example.weatherwise.data.repository.Utility.getFakeForecastWeather
import com.example.weatherwise.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeWeatherRepositoryImpl : WeatherRepository {

    private var fakeCurrentWeather: CurrentWeatherDataEntity? = null
    private var fakeForecastWeather: ForecastWeatherDataEntity? = null
    private var fakeFavouriteWeather: List<FavouriteWeatherDetailsEntity>? = null
    private var networkError: Boolean = false
    private var httpError: Boolean = false
    class HttpErrorException(statusCode: Int, message: String) : RuntimeException(message)


    fun simulateNetworkError(value: Boolean) {
        networkError = value
    }
    fun simulateHTTPError(value: Boolean) {
        httpError = value
    }
    fun setFakeCurrentWeather() {
        fakeCurrentWeather = getFakeCurrentWeather()
    }

    fun setFakeForecastWeather() {
        fakeForecastWeather = getFakeForecastWeather()
    }
    fun setFakeFavouriteWeather() {
        fakeFavouriteWeather = getFakeFavouriteWeather()
    }


    override suspend fun getCurrentWeather(
        lat: Double,
        lon: Double
    ): Flow<CurrentWeatherDataEntity> {
        return if (networkError) {
            throw NetworkErrorException("Simulated Network Error")
        } else if(httpError){
            throw HttpErrorException(404,"Simulated Resource Not Found")
        }else{
            return flow {
                fakeCurrentWeather?.let {
                    emit(it)
                }
            }
        }
    }

    override suspend fun getForecastWeather(
        lat: Double,
        lon: Double
    ): Flow<ForecastWeatherDataEntity> {
        return if (networkError) {
            throw NetworkErrorException("Simulated Network Error")
        } else if(httpError){
            throw HttpErrorException(404,"Simulated Resource Not Found")
        }else{
            return flow {
                fakeForecastWeather?.let {
                    emit(it)
                }
            }
        }
    }

    override suspend fun updateForecastWeatherFavoriteStatus(timeStamp: String) {
    //tested in dao
    }

    override suspend fun insertFavouriteWeatherDetails(
        timeStamp: String,
        lat: Double,
        lon: Double,
        temp: Double,
        weatherKind: String
    ) {
        //tested in dao
    }

    override suspend fun getFavouriteWeatherDetails(): Flow<List<FavouriteWeatherDetailsEntity>> {
        return flow {
            fakeFavouriteWeather?.let { emit(it) }
        }
    }

    override suspend fun deleteFavouriteWeatherDetails(timeStamp: String) {
        //tested in dao
    }


}