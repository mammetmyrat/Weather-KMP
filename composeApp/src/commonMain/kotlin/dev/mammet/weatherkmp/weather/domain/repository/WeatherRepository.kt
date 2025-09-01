package dev.mammet.weatherkmp.weather.domain.repository

import dev.mammet.weatherkmp.utils.ApiErrorResponse
import dev.mammet.weatherkmp.utils.Response
import dev.mammet.weatherkmp.weather.domain.models.Weather
import kotlinx.coroutines.flow.StateFlow

interface WeatherRepository {
    val weatherData: StateFlow<Response<Weather, ApiErrorResponse>?>

    fun fetchWeatherData(
        latitude: Float,
        longitude: Float,
        timeZone: String
    )
}