package com.example.weatherwise.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherwise.data.local.dao.WeatherDao
import com.example.weatherwise.data.model.CurrentWeatherDataEntity
import com.example.weatherwise.data.model.FavouriteWeatherDetailsEntity
import com.example.weatherwise.data.model.ForecastWeatherDataEntity

@Database(
    version = 34 ,
    entities = [CurrentWeatherDataEntity::class, ForecastWeatherDataEntity::class, FavouriteWeatherDetailsEntity::class]
)
//@TypeConverters(WeatherConverter::class)
abstract class WeatherwiseDatabase: RoomDatabase() {
    abstract val WeatherDao : WeatherDao

}
