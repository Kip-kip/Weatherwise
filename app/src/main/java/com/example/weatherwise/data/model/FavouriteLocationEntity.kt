package com.example.weatherwise.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavouriteWeatherDetailsEntity(
    val timeStamp:String,
    val lat:Double,
    val lon:Double,
    val temp:Double,
    val weatherKind:String,
    @PrimaryKey(autoGenerate = true)
    val fav_id: Long = 0,
    )
