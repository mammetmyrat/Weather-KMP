package dev.mammet.weatherkmp.geo_location.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.mammet.weatherkmp.utils.FlagUrl

data class Geolocation(
    val id: Int = 0,
    val name: String,
    val countryName: String,
    val countryCode: String,
    val flagUrl: FlagUrl,
    val countryId: Int,
    val latitude: Double,
    val longitude: Double,
    val timeZone: String,
    val elevation: Double,
)
