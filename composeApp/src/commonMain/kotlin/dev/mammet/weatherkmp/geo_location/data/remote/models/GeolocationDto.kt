package dev.mammet.weatherkmp.geo_location.data.remote.models


import dev.mammet.weatherkmp.geo_location.domain.models.Geolocation
import dev.mammet.weatherkmp.utils.K
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GeolocationDto(
    @SerialName("generationtime_ms")
    val generationtimeMs: Double = 0.0,
    @SerialName("results")
    val results: List<Result> = listOf()
)

fun GeolocationDto.toDomain(): List<Geolocation>{
    return results.map {
        Geolocation(
            id = it.id,
            name = it.name,
            countryName = it.country,
            countryCode = it.countryCode,
            flagUrl = K.flagUrl(it.countryCode),
            countryId = it.countryId,
            latitude = it.latitude,
            longitude = it.longitude,
            timeZone = it.timezone,
            elevation = it.elevation,
        )
    }
}