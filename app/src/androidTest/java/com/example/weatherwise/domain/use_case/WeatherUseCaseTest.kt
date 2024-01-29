package com.example.weatherwise.domain.use_case

import android.accounts.NetworkErrorException
import com.example.weatherwise.common.Resource
import com.example.weatherwise.data.model.CurrentWeatherDataEntity
import com.example.weatherwise.data.repository.FakeWeatherRepositoryImpl
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertThrows
import org.junit.Before

import org.junit.Test
import retrofit2.HttpException
import java.lang.Exception

class WeatherUseCaseTest {

    private lateinit var weatherUseCase: WeatherUseCase
    private lateinit var fakeRepository: FakeWeatherRepositoryImpl
    private val fakeLat = 0.4
    private val fakeLon = 25.5

    @Before
    fun setup() {
        fakeRepository = FakeWeatherRepositoryImpl()
        weatherUseCase = WeatherUseCase(fakeRepository)
    }

    //CURRENT WEATHER

    @Test
    fun gettingCurrentWeatherWithNoInternetConnectionReturnsError() = runTest{
        fakeRepository.simulateNetworkError(true)
        try {
            val results = fakeRepository.getCurrentWeather(fakeLat,fakeLon)
        }catch(e:NetworkErrorException) {
            assertThat(e.message === "Simulated Network Error").isTrue()
        }
    }

    @Test
    fun getting_CurrentWeatherEncounters_HTTP_Error() = runTest{
        fakeRepository.simulateHTTPError(true)
        try {
            val results = fakeRepository.getCurrentWeather(fakeLat,fakeLon)
        }catch(e:FakeWeatherRepositoryImpl.HttpErrorException) {
            assertThat(e.message === "Simulated Resource Not Found").isTrue()
        }
    }

    @Test
    fun gettingCurrentWeatherInitiatesLoading() = runTest {
        fakeRepository.setFakeCurrentWeather()
        val results = fakeRepository.getCurrentWeather(fakeLat,fakeLon)
        assertThat(results.firstOrNull()).isNotNull()
    }

    @Test
    fun gettingCurrentWeatherReturnsCurrentWeatherData() = runTest {
        fakeRepository.setFakeCurrentWeather()
        val results = fakeRepository.getCurrentWeather(fakeLat,fakeLon)
        assertThat(results.firstOrNull()).isNotNull()
    }

    //FORECAST WEATHER

    @Test
    fun gettingForecastWeatherWithNoInternetConnectionReturnsError() = runTest{
        fakeRepository.simulateNetworkError(true)
        try {
            val results = fakeRepository.getForecastWeather(fakeLat,fakeLon)
        }catch(e:NetworkErrorException) {
            assertThat(e.message === "Simulated Network Error").isTrue()
        }
    }

    @Test
    fun gettingForecastWeatherEncountersHTTPError() = runTest{
        fakeRepository.simulateHTTPError(true)
        try {
            val results = fakeRepository.getForecastWeather(fakeLat,fakeLon)
        }catch(e:FakeWeatherRepositoryImpl.HttpErrorException) {
            assertThat(e.message === "Simulated Resource Not Found").isTrue()
        }
    }

    @Test
    fun gettingForecastWeatherInitiatesLoading() = runTest {
        fakeRepository.setFakeForecastWeather()
        val results = fakeRepository.getForecastWeather(fakeLat,fakeLon)
        assertThat(results.firstOrNull()).isNotNull()
    }

    @Test
    fun gettingForecastWeatherReturnsCurrentWeatherData() = runTest {
        fakeRepository.setFakeForecastWeather()
        val results = fakeRepository.getForecastWeather(fakeLat,fakeLon)
        assertThat(results.firstOrNull()).isNotNull()
    }

    //    FAVOURITE
    @Test
    fun gettingFavouriteWeatherDetailsReturnsData() = runTest {
        fakeRepository.setFakeFavouriteWeather()
        val results = fakeRepository.getFavouriteWeatherDetails()
        assertThat(results.firstOrNull()).isNotNull()
    }



}