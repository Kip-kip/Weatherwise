package com.example.weatherwise.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavouriteLocationEntity(
    val lat:Double,
    val lon:Double,
    @PrimaryKey(autoGenerate = true)
    val loc_id: Long = 0,
    )
