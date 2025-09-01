package dev.mammet.weatherkmp.weather.data.remote

import dev.mammet.weatherkmp.common.data.safeRequest
import dev.mammet.weatherkmp.utils.ApiErrorResponse
import dev.mammet.weatherkmp.utils.ApiParameters
import dev.mammet.weatherkmp.utils.K
import dev.mammet.weatherkmp.utils.Response
import dev.mammet.weatherkmp.weather.data.remote.models.WeatherDto
import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import kotlinx.coroutines.flow.Flow

class WeatherRemoteApiServiceImpl(
    private val httpClient: HttpClient,
) : WeatherRemoteApiService {
    override fun fetchWeather(
        latitude: Float,
        longitude: Float,
        daily: Array<String>,
        currentWeather: Array<String>,
        hourlyWeather: Array<String>,
        timeformat: String,
        timeZone: String
    ): Flow<Response<WeatherDto, ApiErrorResponse>> {
        return httpClient.safeRequest {
            url("${K.WEATHER_BASE_URL}/${K.WEATHER_END_POINT}")
            parameter(ApiParameters.LATITUDE, latitude.toString())
            parameter(ApiParameters.LONGITUDE, longitude.toString())
            parameter(ApiParameters.DAILY, daily.joinToString(","))
            parameter(ApiParameters.CURRENT_WEATHER, currentWeather.joinToString(","))
            parameter(ApiParameters.HOURLY, hourlyWeather.joinToString(","))
            parameter(ApiParameters.TIME_FORMAT, timeformat)
            parameter(ApiParameters.TIMEZONE, timeZone)
        }
    }


}