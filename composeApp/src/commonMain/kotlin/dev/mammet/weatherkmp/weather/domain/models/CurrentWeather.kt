package dev.mammet.weatherkmp.weather.domain.models

import dev.mammet.weatherkmp.utils.WeatherInfoItem

data class CurrentWeather(
    val temperature: Double,
    val time: String,
    val weatherStatus: WeatherInfoItem,
    val windDirection: String,
    val windSpeed: Double,
    val isDay: Boolean,
)
