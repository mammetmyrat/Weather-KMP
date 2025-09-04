package dev.mammet.weatherkmp.weather.data.mapper

import dev.mammet.weatherkmp.weather.data.remote.models.DailyDto
import dev.mammet.weatherkmp.weather.domain.models.Daily
import dev.mammet.weatherkmp.utils.Util
import dev.mammet.weatherkmp.utils.WeatherInfoItem

class ApiDailyWeatherMapper : ApiMapper<Daily, DailyDto> {
    override fun mapToDomain(
        model: DailyDto,
        timeZone: String
    ): Daily {
        return Daily(
            temperaturaMax = model.temperature2mMax,
            temperaturaMin = model.temperature2mMin,
            time = parseTime(model.time, timeZone),
            weatherStatus = parseWeatherStatus(model.weatherCode),
            windDirection = parseWeatherDirection(model.windDirection10mDominant),
            windSpeed = model.windSpeed10mMax,
            sunrise = model.sunrise.map { Util.formatUnixToHour(it, timeZone) },
            sunset = model.sunset.map { Util.formatUnixToHour(it, timeZone) },
            uvIndex = model.uvIndexMax,
        )
    }

    private fun parseTime(time: List<Long>, timeZone: String): List<String> {
        return time.map { Util.formatUnixToDay(it, timeZone) }
    }

    private fun parseWeatherStatus(code: List<Int>): List<WeatherInfoItem> {
        return code.map { Util.getWeatherInfo(it) }
    }

    private fun parseWeatherDirection(windDirections: List<Double>): List<String> {
        return windDirections.map { Util.getWindDirection(it) }
    }
}