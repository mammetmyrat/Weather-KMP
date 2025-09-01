package dev.mammet.weatherkmp.geo_location.data.remote.models

import dev.mammet.weatherkmp.common.data.safeRequest
import dev.mammet.weatherkmp.geo_location.data.remote.GeolocationRemoteApiService
import dev.mammet.weatherkmp.utils.ApiErrorResponse
import dev.mammet.weatherkmp.utils.K
import dev.mammet.weatherkmp.utils.Response
import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import kotlinx.coroutines.flow.Flow

class GeolocationRemoteApiServiceImpl(
    private val httpClient: HttpClient
): GeolocationRemoteApiService {
    override fun searchLocation(query: String): Flow<Response<GeolocationDto, ApiErrorResponse>> {
        return httpClient.safeRequest<GeolocationDto, ApiErrorResponse> {
            url(urlString = K.GEO_CODING_BASE_URL + "/${K.GEO_CODING_END_POINT}")
            parameter("name", query)
        }
    }
}