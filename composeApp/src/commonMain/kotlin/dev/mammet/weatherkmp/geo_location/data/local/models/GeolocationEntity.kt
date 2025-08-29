package dev.mammet.weatherkmp.geo_location.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "geolocation_table")
data class GeolocationEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 1,
    val name: String,
    val countryName: String,
    val countryCode: String,
    val countryId: Int,
    val latitude: Double,
    val longitude: Double,
    val timeZone: String,
    val elevation: Double,
)
