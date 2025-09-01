package dev.mammet.weatherkmp.weather.data.remote

import dev.mammet.weatherkmp.utils.ApiErrorResponse
import dev.mammet.weatherkmp.utils.Response
import dev.mammet.weatherkmp.weather.data.remote.models.WeatherDto
import kotlinx.coroutines.flow.Flow

interface WeatherRemoteApiService {

    fun fetchWeather(
        latitude: Float = -6.8f,
        longitude: Float = 39.28f,
        daily: Array<String> = arrayOf(
            "weather_code",
            "temperature_2m_max",
            "temperature_2m_min",
            "wind_speed_10m_max",
            "wind_direction_10m_dominant",
            "sunrise",
            "sunset",
            "uv_index_max"
        ),
        currentWeather: Array<String> = arrayOf(
            "temperature_2m",
            "is_day",
            "weather_code",
            "wind_speed_10m",
            "wind_direction_10m",
        ),
        hourlyWeather: Array<String> = arrayOf(
            "weather_code",
            "temperature_2m",
        ),
        timeformat: String = "unixtime",
        timeZone: String = "Africe/Dar_es_Salaam"
    ): Flow<Response<WeatherDto, ApiErrorResponse>>
}