package com.example.weatherwise.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.weatherwise.data.model.type_converters.WeatherConverter

@Entity
data class ForecastWeatherDataEntity(
    val cod: String,
    val message: Int,
    val cnt: Int,
    @field:TypeConverters(WeatherConverter::class)
    val list: List<ForecastWeather>,
    @field:TypeConverters(WeatherConverter::class)
    val city: City,
    @PrimaryKey(autoGenerate = true)
    val room_id: Long = 0,
)

data class ForecastWeather(
    val dt: Long,
    @field:TypeConverters(WeatherConverter::class)
    val main: ForecastWeatherMain,
    @field:TypeConverters(WeatherConverter::class)
    val weather: List<Weather>,
    @field:TypeConverters(WeatherConverter::class)
    val clouds: Clouds,
    @field:TypeConverters(WeatherConverter::class)
    val wind: ForecastWeatherWind,
    val visibility: Int,
    val pop: Double,
    @field:TypeConverters(WeatherConverter::class)
    val sys: ForecastWeatherSys,
    val dt_txt: String,
    var favourite: Boolean,
    val list_id: Int = 0,
)

data class ForecastWeatherMain(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Int,
    val sea_level: Int,
    val grnd_level: Int,
    val humidity: Int,
    val tempKf: Double
)


data class ForecastWeatherWind(
    val speed: Double,
    val deg: Int,
    val gust: Double
)

data class ForecastWeatherSys(
    val pod: String
)

data class City(
    val id: Int,
    val name: String,
    @field:TypeConverters(WeatherConverter::class)
    val coord: Coord,
    val country: String,
    val population: Int,
    val timezone: Int,
    val sunrise: Long,
    val sunset: Long
)

