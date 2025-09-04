package dev.mammet.weatherkmp.weather.data.mapper

import dev.mammet.weatherkmp.weather.data.remote.models.CurrentDto
import dev.mammet.weatherkmp.weather.domain.models.CurrentWeather
import dev.mammet.weatherkmp.utils.Util
import dev.mammet.weatherkmp.utils.WeatherInfoItem

class CurrentWeatherMapper: ApiMapper<CurrentWeather, CurrentDto> {
    override fun mapToDomain(
        model: CurrentDto,
        timeZone: String
    ): CurrentWeather {
        return CurrentWeather(
            temperature = model.temperature2m,
            time = parseTime(model.time.toString(), timeZone),
            weatherStatus = parseWeatherStatus(model.weatherCode),
            windDirection = parseWeatherDirection(model.windDirection10m),
            windSpeed = model.windSpeed10m,
            isDay = model.isDay == 1
        )
    }

    private fun parseTime(time: String, timeZone: String): String {
        return Util.formatUnixToDay(time.toLong(), timeZone)
    }

    private fun parseWeatherStatus(code: Int): WeatherInfoItem {
        return Util.getWeatherInfo(code)
    }

    private fun parseWeatherDirection(windDirection: Double): String {
        return  Util.getWindDirection(windDirection)
    }
}