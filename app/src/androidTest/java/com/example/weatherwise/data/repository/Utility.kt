package com.example.weatherwise.data.repository

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
import java.util.Random

object Utility {


    fun getFakeCurrentWeather() : CurrentWeatherDataEntity {
        val mockCoord = Coord(lon = 12.34, lat = 56.78)
        val mockWeatherList = listOf(
            Weather(id = 801, main = "Clouds", description = "Few clouds", icon = "02d"),
            Weather(id = 500, main = "Rain", description = "Light rain", icon = "10d")
        )
        val mockMain = Main(
            temp = 23.5,
            feels_like = 24.0,
            temp_min = 22.0,
            temp_max = 25.5,
            pressure = 1015,
            humidity = 60
        )
        val mockWind = Wind(speed = 5.0, deg = 180)
        val mockRain = Rain(`1h` = 1.2)
        val mockClouds = Clouds(all = 20)
        val mockSys = Sys(
            type = 1,
            id = 123,
            country = "US",
            sunrise = 1643534400,
            sunset = 1643582400
        )
        val mockCurrentWeatherDataEntity = CurrentWeatherDataEntity(
            coord = mockCoord,
            weather = mockWeatherList,
            base = "stations",
            main = mockMain,
            visibility = 10000,
            wind = mockWind,
            rain = mockRain,
            clouds = mockClouds,
            dt = 1643569200, // Example timestamp, you can replace it with a valid one
            sys = mockSys,
            timezone = 7200,
            id = 2643743,
            name = "London",
            cod = 200,
            last_updated = 1643569200
        )
        return mockCurrentWeatherDataEntity
    }

    fun getFakeForecastWeather(): ForecastWeatherDataEntity{
        val mockCoord = Coord(lon = 12.34, lat = 56.78)
        val mockWeatherList = listOf(
            Weather(id = 801, main = "Clouds", description = "Few clouds", icon = "02d"),
            Weather(id = 500, main = "Rain", description = "Light rain", icon = "10d")
        )
        val mockMain = Main(
            temp = 23.5,
            feels_like = 24.0,
            temp_min = 22.0,
            temp_max = 25.5,
            pressure = 1015,
            humidity = 60
        )
        val mockWind = Wind(speed = 5.0, deg = 180)
        val mockRain = Rain(`1h` = 1.2)
        val mockClouds = Clouds(all = 20)
        val mockSys = Sys(
            type = 1,
            id = 123,
            country = "US",
            sunrise = 1643534400,
            sunset = 1643582400
        )
        val mockForecastWeatherDataEntity = ForecastWeatherDataEntity(
            cod = "200",
            message = 0,
            cnt = 1,
            list = listOf(generateFakeForecastItem()),
            city = generateFakeCity()
        )
        return  mockForecastWeatherDataEntity
    }

    private fun generateFakeForecastItem(): ForecastWeather {
        return ForecastWeather(
            dt = System.currentTimeMillis(),
            main = ForecastWeatherMain(
                temp = 25.0,
                feels_like = 26.0,
                temp_min = 24.0,
                temp_max = 26.5,
                pressure = 1015,
                sea_level = 1020,
                grnd_level = 1010,
                humidity = 65,
                tempKf = 1.0
            ),
            weather = listOf(
                Weather(id = 801, main = "Clouds", description = "Few clouds", icon = "02d")
            ),
            clouds = Clouds(all = 20),
            wind = ForecastWeatherWind(speed = 5.0, deg = 180, gust = 6.0),
            visibility = 10000,
            pop = 0.1,
            sys = ForecastWeatherSys(pod = "d"),
            dt_txt = "2024-01-28 12:00:00",
            favourite = false
        )
    }

    private fun generateFakeCity(): City {
        return City(
            id = 2643743,
            name = "London",
            coord = Coord(lon = 12.34, lat = 56.78),
            country = "GB",
            population = 1000000,
            timezone = 0,
            sunrise = 1643534400,
            sunset = 1643582400
        )
    }

    fun getFakeFavouriteWeather():List<FavouriteWeatherDetailsEntity> {
        return listOf(FavouriteWeatherDetailsEntity(
            timeStamp = "2024-01-28 21:00:00",
            lat = 3.0,
            lon = 37.4,
            temp =2567.4,
            weatherKind = "Rainy",
            fav_id = 0
        ))
    }
}
