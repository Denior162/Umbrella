package com.denior.parasol.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cities")
data class CityEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val cityName: String,
    val countryName: String,
    val latitude: Double,
    val longitude: Double
)