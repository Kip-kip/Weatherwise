package com.example.weatherwise.domain.use_case
import com.example.weatherwise.data.local.dao.WeatherDao
import com.example.weatherwise.data.model.FavouriteWeatherDetailsEntity
import com.example.weatherwise.domain.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject


class FavouriteWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository,
) {

    suspend fun markAsFavourite(timeStamp:String, lat: Double?, lon: Double?,temp:Double,weatherKind:String){
        repository.updateForecastWeatherFavoriteStatus(timeStamp)
        if (lat != null && lon != null) {
            repository.insertFavouriteWeatherDetails(timeStamp, lat, lon, temp,weatherKind)
        }
    }

    suspend fun getFavouriteWeatherDetails(): Flow<List<FavouriteWeatherDetailsEntity>> {
        return repository.getFavouriteWeatherDetails()
    }

    suspend fun deleteFavouriteWeather(timeStamp: String){
        repository.deleteFavouriteWeatherDetails(timeStamp)
    }

}


