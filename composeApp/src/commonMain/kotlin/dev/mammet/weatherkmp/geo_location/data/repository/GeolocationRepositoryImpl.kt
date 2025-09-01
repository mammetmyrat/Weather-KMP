package dev.mammet.weatherkmp.geo_location.data.repository

import dev.mammet.weatherkmp.common.data.Mapper
import dev.mammet.weatherkmp.geo_location.data.local.GeolocationDao
import dev.mammet.weatherkmp.geo_location.data.local.models.GeolocationEntity
import dev.mammet.weatherkmp.geo_location.data.remote.GeolocationRemoteApiService
import dev.mammet.weatherkmp.geo_location.data.remote.models.toDomain
import dev.mammet.weatherkmp.geo_location.domain.models.Geolocation
import dev.mammet.weatherkmp.geo_location.domain.repository.GeolocationRepository
import dev.mammet.weatherkmp.utils.ApiErrorResponse
import dev.mammet.weatherkmp.utils.Response
import dev.mammet.weatherkmp.utils.map
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn

class GeolocationRepositoryImpl(
    private val geolocationRemoteApiService: GeolocationRemoteApiService,
    private val geolocationDao: GeolocationDao,
    private val geolocationMapper: Mapper<Geolocation, GeolocationEntity>,
    private val externalScope: CoroutineScope
) : GeolocationRepository {
    override val geolocation: Flow<Geolocation?>
        get() {
            return geolocationDao.getGeolocation().map {
                geolocationMapper.mapToDomainOrNull(it)
            }.shareIn(scope = externalScope, started = SharingStarted.Lazily)
        }

    override suspend fun upsertLocation(geolocation: Geolocation) {
        geolocationDao.upsertGeolocation(geolocationMapper.mapFromDomain(geolocation))
    }

    override fun fetchGeoLocation(query: String): Flow<Response<List<Geolocation>, ApiErrorResponse>> {
        return geolocationRemoteApiService.searchLocation(query).map { response ->
            response.map { geolocationDto ->
                geolocationDto.toDomain()
            }
        }
    }

    override suspend fun clearGeolocation() {
        geolocationDao.clearGeolocation()
    }

    override suspend fun clear() {
        externalScope.cancel()
    }
}