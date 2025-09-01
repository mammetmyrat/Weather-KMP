package dev.mammet.weatherkmp.geo_location.data.remote

import androidx.room.Query
import dev.mammet.weatherkmp.geo_location.data.remote.models.GeolocationDto
import dev.mammet.weatherkmp.utils.ApiErrorResponse
import dev.mammet.weatherkmp.utils.Response
import kotlinx.coroutines.flow.Flow

interface GeolocationRemoteApiService {
    fun searchLocation(query: String): Flow<Response<GeolocationDto, ApiErrorResponse>>
}