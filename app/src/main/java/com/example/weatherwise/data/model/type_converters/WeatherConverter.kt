package com.example.weatherwise.data.model.type_converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.weatherwise.data.model.City
import com.example.weatherwise.data.model.Clouds
import com.example.weatherwise.data.model.Coord
import com.example.weatherwise.data.model.ForecastWeather
import com.example.weatherwise.data.model.ForecastWeatherMain
import com.example.weatherwise.data.model.ForecastWeatherSys
import com.example.weatherwise.data.model.ForecastWeatherWind
import com.example.weatherwise.data.model.Main
import com.example.weatherwise.data.model.Rain
import com.example.weatherwise.data.model.Sys
import com.example.weatherwise.data.model.Weather
import com.example.weatherwise.data.model.Wind

class WeatherConverter {
    @TypeConverter
    fun fromCoord(coord: Coord?): String {
        return Gson().toJson(coord)
    }

    @TypeConverter
    fun toCoord(coordString: String?): Coord? {
        return Gson().fromJson(coordString, Coord::class.java)
    }

    @TypeConverter
    fun fromWeatherList(weatherList: List<Weather>?): String {
        return Gson().toJson(weatherList)
    }

    @TypeConverter
    fun toWeatherList(weatherListString: String?): List<Weather>? {
        val type = object : TypeToken<List<Weather>>() {}.type
        return Gson().fromJson(weatherListString, type)
    }


    @TypeConverter
    fun fromMain(main: Main?): String {
        return Gson().toJson(main)
    }

    @TypeConverter
    fun toMain(mainString: String?): Main? {
        return Gson().fromJson(mainString, Main::class.java)
    }

    @TypeConverter
    fun fromWind(wind: Wind?): String {
        return Gson().toJson(wind)
    }

    @TypeConverter
    fun toWind(windString: String?): Wind? {
        return Gson().fromJson(windString, Wind::class.java)
    }

    @TypeConverter
    fun fromRain(rain: Rain?): String {
        return Gson().toJson(rain)
    }

    @TypeConverter
    fun toRain(rainString: String?): Rain? {
        return Gson().fromJson(rainString, Rain::class.java)
    }

    @TypeConverter
    fun fromClouds(clouds: Clouds?): String {
        return Gson().toJson(clouds)
    }

    @TypeConverter
    fun toClouds(cloudsString: String?): Clouds? {
        return Gson().fromJson(cloudsString, Clouds::class.java)
    }

    @TypeConverter
    fun fromSys(sys: Sys?): String {
        return Gson().toJson(sys)
    }

    @TypeConverter
    fun toSys(sysString: String?): Sys? {
        return Gson().fromJson(sysString, Sys::class.java)
    }

    //Forecast weather
    @TypeConverter
    fun fromForecastWeatherList(forecastWeatherList: List<ForecastWeather>?): String {
        return Gson().toJson(forecastWeatherList)
    }

    @TypeConverter
    fun toForecastWeatherList(forecastWeatherListString: String?): List<ForecastWeather>? {
        val type = object : TypeToken<List<ForecastWeather>>() {}.type
        return Gson().fromJson(forecastWeatherListString, type)
    }


    @TypeConverter
    fun fromCity(city: City?): String {
        return Gson().toJson(city)
    }

    @TypeConverter
    fun toCity(cityString: String?): City? {
        return Gson().fromJson(cityString, City::class.java)
    }

    @TypeConverter
    fun fromForecastWeatherMain(forecastWeatherMain: ForecastWeatherMain?): String {
        return Gson().toJson(forecastWeatherMain)
    }

    @TypeConverter
    fun toForecastWeatherMain(forecastWeatherMainString: String?): ForecastWeatherMain? {
        return Gson().fromJson(forecastWeatherMainString, ForecastWeatherMain::class.java)
    }

    @TypeConverter
    fun fromForecastWeatherWind(forecastWeatherWind: ForecastWeatherWind?): String {
        return Gson().toJson(forecastWeatherWind)
    }

    @TypeConverter
    fun toForecastWeatherWind(forecastWeatherWindString: String?): ForecastWeatherWind? {
        return Gson().fromJson(forecastWeatherWindString, ForecastWeatherWind::class.java)
    }

    @TypeConverter
    fun fromForecastWeatherSys(forecastWeatherSys: ForecastWeatherSys?): String {
        return Gson().toJson(forecastWeatherSys)
    }

    @TypeConverter
    fun toForecastWeatherSys(forecastWeatherSysString: String?): ForecastWeatherSys? {
        return Gson().fromJson(forecastWeatherSysString, ForecastWeatherSys::class.java)
    }

}