package com.example.weatherwise.di

import WeatherRepositoryImpl
import android.app.Application
import androidx.room.Room
import com.example.weatherwise.common.Constants.BASE_URL
import com.example.weatherwise.data.local.dao.WeatherDao
import com.example.weatherwise.data.local.db.WeatherwiseDatabase
import com.example.weatherwise.data.remote.WeatherApi
import com.example.weatherwise.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWeatherApi(): WeatherApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherWiseDatabase(application: Application): WeatherwiseDatabase {
        return Room.databaseBuilder(
            application,
            WeatherwiseDatabase::class.java,
            "weatherwise_db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideCurrentWeatherRepository(api: WeatherApi, local: WeatherwiseDatabase): WeatherRepository {
        return WeatherRepositoryImpl(api,local.WeatherDao)
    }

    @Provides
    @Singleton
    fun provideWeatherDao(database: WeatherwiseDatabase): WeatherDao {
        return database.WeatherDao
    }

}