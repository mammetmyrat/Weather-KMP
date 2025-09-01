package dev.mammet.weatherkmp.weather.data.mapper

interface ApiMapper<Domain, Model> {
    fun mapToDomain(model: Model, timeZone: String = ""): Domain
}