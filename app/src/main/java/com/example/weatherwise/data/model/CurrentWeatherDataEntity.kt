package com.example.weatherwise.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.weatherwise.data.model.type_converters.WeatherConverter

@Entity
data class CurrentWeatherDataEntity(
    @field:TypeConverters(WeatherConverter::class)
    val coord: Coord,
    @field:TypeConverters(WeatherConverter::class)
    val weather: List<Weather>,
    val base: String,
    @field:TypeConverters(WeatherConverter::class)
    val main: Main,
    val visibility: Int,
    @field:TypeConverters(WeatherConverter::class)
    val wind: Wind,
    @field:TypeConverters(WeatherConverter::class)
    val rain: Rain?,
    @field:TypeConverters(WeatherConverter::class)
    val clouds: Clouds,
    val dt: Long,
    @field:TypeConverters(WeatherConverter::class)
    val sys: Sys,
    val timezone: Int,
    val id: Int,
    val name: String,
    val cod: Int,
    @ColumnInfo(name = "last_updated") var last_updated: Long,
    @PrimaryKey(autoGenerate = true)
    val room_id: Long = 0,
)

data class Coord(
    val lon: Double,
    val lat: Double
)

data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class Main(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Int,
    val humidity: Int
)

data class Wind(
    val speed: Double,
    val deg: Int
)

data class Rain(
    val `1h`: Double
)

data class Clouds(
    val all: Int
)

data class Sys(
    val type: Int,
    val id: Int,
    val country: String,
    val sunrise: Long,
    val sunset: Long
)