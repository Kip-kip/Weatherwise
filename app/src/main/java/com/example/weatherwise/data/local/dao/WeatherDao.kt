package com.example.weatherwise.data.local.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.weatherwise.data.model.CurrentWeatherDataEntity
import com.example.weatherwise.data.model.FavouriteLocationEntity
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

    @Query("SELECT * FROM forecastweatherdataentity WHERE room_id = 1")
    fun getForecastWeatherById(): ForecastWeatherDataEntity?

    @Update
    fun updateForecastWeather(forecastWeatherDataEntity: ForecastWeatherDataEntity)

    //    Fav places
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavouriteLocation(favouriteLocation: FavouriteLocationEntity)

    @Query("SELECT * FROM favouritelocationentity")
    fun getFavouriteLocations(): List<FavouriteLocationEntity>
}
