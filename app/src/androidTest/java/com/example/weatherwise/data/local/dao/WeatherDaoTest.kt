package com.example.weatherwise.data.local.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.weatherwise.data.local.db.WeatherwiseDatabase
import com.example.weatherwise.data.repository.Utility.getFakeCurrentWeather
import com.example.weatherwise.data.repository.Utility.getFakeForecastWeather
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class WeatherDaoTest {
    private lateinit var database: WeatherwiseDatabase
    private lateinit var dao: WeatherDao

    @Before
    fun setup(){
        database = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),
        WeatherwiseDatabase::class.java).allowMainThreadQueries().build()
        dao = database.WeatherDao
    }

    @After
    fun teardown(){
        database.close()
    }

    //    CURRENT WEATHER

    @Test
    fun insertCurrentWeatherInsertsAndGetCurrentWeatherFetchesData() = runTest {
        val currentWeather = getFakeCurrentWeather()
        dao.insertCurrentWeather(currentWeather)
        val fetchedCurrentWeather = dao.getCurrentWeather()
        assertThat(fetchedCurrentWeather.first()).isNotNull()
    }

   @Test
   fun deleteCurrentWeatherDeletes() = runTest {
       val currentWeather = getFakeCurrentWeather()
       dao.insertCurrentWeather(currentWeather)
       dao.deleteCurrentWeather()
       val fetchedCurrentWeather = dao.getCurrentWeather()
       assertThat(fetchedCurrentWeather.first()).isNull()

   }

    //    FORECAST WEATHER

    @Test
    fun insertForecastWeatherInsertsAndGetForecastWeatherFetchesData() = runTest {
        val forecastWeather = getFakeForecastWeather()
        dao.insertForecastWeather(forecastWeather)
        val fetchedForecastWeather = dao.getForecastWeather()
        assertThat(fetchedForecastWeather.first()).isNotNull()
    }

    @Test
    fun deleteForecastWeatherDeletes() = runTest {
        val forecastWeather = getFakeForecastWeather()
        dao.insertForecastWeather(forecastWeather)
        dao.deleteForecastWeather()
        val fetchedForecastWeather = dao.getForecastWeather()
        assertThat(fetchedForecastWeather.first()).isNull()
    }

    @Test
    fun getForecastWeatherByIdFetchesById(){
        val forecastWeather = getFakeForecastWeather()
        dao.insertForecastWeather(forecastWeather)
        val fetchedById = dao.getForecastWeatherById()
        assertThat(fetchedById).isNotNull()
    }

    @Test
    fun updateForecastWeatherUpdatesData(){
        val forecastWeather = getFakeForecastWeather()
        dao.insertForecastWeather(forecastWeather)
        val fetchedForecastWeather = dao.getForecastWeatherById()
        val initialCityName = fetchedForecastWeather?.city?.name
        val updatedForecastWeather = fetchedForecastWeather!!.copy(city = fetchedForecastWeather.city.copy(name = "New City Name"))
        dao.updateForecastWeather(updatedForecastWeather)
        val updatedCityName = dao.getForecastWeatherById()?.city?.name

        assertThat(initialCityName).isNotEqualTo(updatedCityName)
    }



}