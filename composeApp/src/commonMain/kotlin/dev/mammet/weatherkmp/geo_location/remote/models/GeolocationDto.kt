package dev.mammet.weatherkmp.geo_location.remote.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GeolocationDto(
    @SerialName("generationtime_ms")
    val generationtimeMs: Double? = 0.0,
    @SerialName("results")
    val results: List<Result?>? = listOf()
)