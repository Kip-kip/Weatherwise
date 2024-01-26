//package com.example.weatherwise.domain.model
//
//data class ForecastWeatherData(
//    val cod: String,
//    val message: Int,
//    val cnt: Int,
//    val list: List<ForecastWeather>,
//    val city: City
//)
//
//data class ForecastWeather(
//    val dt: Long,
//    val main: ForecastWeatherMain,
//    val weather: List<Weather>,
//    val clouds: Clouds,
//    val wind: ForecastWeatherWind,
//    val visibility: Int,
//    val pop: Double,
//    val sys: ForecastWeatherSys,
//    val dt_txt: String
//)
//
//data class ForecastWeatherMain(
//    val temp: Double,
//    val feels_like: Double,
//    val temp_min: Double,
//    val temp_max: Double,
//    val pressure: Int,
//    val seaLevel: Int,
//    val grnd_level: Int,
//    val humidity: Int,
//    val tempKf: Double
//)
//
//
//data class ForecastWeatherWind(
//    val speed: Double,
//    val deg: Int,
//    val gust: Double
//)
//
//data class ForecastWeatherSys(
//    val pod: String
//)
//
//data class City(
//    val id: Int,
//    val name: String,
//    val coord: Coord,
//    val country: String,
//    val population: Int,
//    val timezone: Int,
//    val sunrise: Long,
//    val sunset: Long
//)
//
