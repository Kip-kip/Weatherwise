package com.example.weatherwise.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherwise.data.local.dao.WeatherDao
import com.example.weatherwise.data.model.CurrentWeatherDataEntity
import com.example.weatherwise.data.model.FavouriteLocationEntity
import com.example.weatherwise.data.model.ForecastWeatherDataEntity

@Database(
    version = 26 ,
    entities = [CurrentWeatherDataEntity::class, ForecastWeatherDataEntity::class, FavouriteLocationEntity::class]
)
//@TypeConverters(WeatherConverter::class)
abstract class WeatherwiseDatabase: RoomDatabase() {
    abstract val WeatherDao : WeatherDao

}
