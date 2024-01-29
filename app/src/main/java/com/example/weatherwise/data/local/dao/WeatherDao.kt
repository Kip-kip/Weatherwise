package com.example.weatherwise.data.local.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.weatherwise.data.model.CurrentWeatherDataEntity
import com.example.weatherwise.data.model.FavouriteWeatherDetailsEntity
import com.example.weatherwise.data.model.ForecastWeatherDataEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface WeatherDao {
//    current
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertCurrentWeather(currentWeather: CurrentWeatherDataEntity)

    @Query("SELECT * FROM currentweatherdataentity")
    fun getCurrentWeather(): Flow<CurrentWeatherDataEntity>

    @Query("DELETE FROM currentweatherdataentity")
    fun deleteCurrentWeather()

//    forecast
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertForecastWeather(forecastWeather: ForecastWeatherDataEntity)

    @Query("SELECT * FROM forecastweatherdataentity")
    fun getForecastWeather(): Flow<ForecastWeatherDataEntity>

    @Query("DELETE FROM forecastweatherdataentity")
    fun deleteForecastWeather()

    @Query("SELECT * FROM forecastweatherdataentity LIMIT 1")
    fun getForecastWeatherById(): ForecastWeatherDataEntity?

    @Update
    fun updateForecastWeather(forecastWeatherDataEntity: ForecastWeatherDataEntity)

    //    Fav places
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavouriteWeatherDetails(favouriteLocation: FavouriteWeatherDetailsEntity)

    @Query("DELETE FROM favouriteweatherdetailsentity WHERE  timeStamp = :timeStamp")
    fun deleteFavouriteWeatherDetails(timeStamp: String)

    @Query("SELECT * FROM favouriteweatherdetailsentity ORDER BY timeStamp DESC ")
    fun getFavouriteWeatherDetails(): Flow<List<FavouriteWeatherDetailsEntity>>
}
