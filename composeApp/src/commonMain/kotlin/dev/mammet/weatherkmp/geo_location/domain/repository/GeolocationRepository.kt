package dev.mammet.weatherkmp.geo_location.domain.repository

import dev.mammet.weatherkmp.geo_location.domain.models.Geolocation
import dev.mammet.weatherkmp.utils.ApiErrorResponse
import dev.mammet.weatherkmp.utils.Response
import kotlinx.coroutines.flow.Flow

interface GeolocationRepository {
    val geolocation: Flow<Geolocation?>
    suspend fun upsertLocation(geolocation: Geolocation)
    fun fetchGeoLocation(query: String): Flow<Response<List<Geolocation>, ApiErrorResponse>>
    suspend fun clearGeolocation()
    suspend fun clear()
}