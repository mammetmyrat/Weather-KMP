package dev.mammet.weatherkmp.weather.data.mapper

import dev.mammet.weatherkmp.weather.data.remote.models.HourlyDto
import dev.mammet.weatherkmp.weather.domain.models.Hourly
import dev.mammet.weatherkmp.utils.Util
import dev.mammet.weatherkmp.utils.WeatherInfoItem

class ApiHourlyMapper: ApiMapper<Hourly, HourlyDto> {
    override fun mapToDomain(
        model: HourlyDto,
        timeZone: String
    ): Hourly {
        return Hourly(
            temperature = model.temperature2m,
            time = parseTime(model.time,timeZone),
            weatherStatus = parseWeatherStatus(model.weatherCode)
        )
    }

    private fun parseTime(time: List<String>, timeZone: String): List<String> {
        return time.map { Util.formatUnixToDay(it.toLong(), timeZone) }
    }

    private fun parseWeatherStatus(code: List<Int>): List<WeatherInfoItem> {
        return code.map { Util.getWeatherInfo(it) }
    }
}