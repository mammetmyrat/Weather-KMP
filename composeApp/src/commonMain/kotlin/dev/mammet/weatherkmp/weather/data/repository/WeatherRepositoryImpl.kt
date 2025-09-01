package dev.mammet.weatherkmp.weather.data.repository

import dev.mammet.weatherkmp.utils.ApiErrorResponse
import dev.mammet.weatherkmp.utils.Response
import dev.mammet.weatherkmp.utils.map
import dev.mammet.weatherkmp.weather.data.mapper.ApiWeatherMapper
import dev.mammet.weatherkmp.weather.data.remote.WeatherRemoteApiService
import dev.mammet.weatherkmp.weather.domain.models.Weather
import dev.mammet.weatherkmp.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class WeatherRepositoryImpl(
    private val weatherRemoteApiService: WeatherRemoteApiService,
    private val mapper: ApiWeatherMapper,
    private val external: CoroutineScope,
) : WeatherRepository {
    private val _weatherData = MutableStateFlow<Response<Weather, ApiErrorResponse>?>(null)
    override val weatherData: StateFlow<Response<Weather, ApiErrorResponse>?>
        get() = _weatherData.asStateFlow()

    override fun fetchWeatherData(
        latitude: Float,
        longitude: Float,
        timeZone: String
    ) {
        weatherRemoteApiService.fetchWeather(
            latitude = latitude, longitude = longitude, timeZone = timeZone
        ).map { response -> response.map { mapper.mapToDomain(it) } }
            .onEach { result ->
                _weatherData.update { result }
            }.launchIn(external)
    }
}