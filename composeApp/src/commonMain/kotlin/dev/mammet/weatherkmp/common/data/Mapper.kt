package dev.mammet.weatherkmp.common.data

interface Mapper<Domain, Model> {
    fun mapToDomainOrNull(model: Model?): Domain?
    fun mapFromDomain(domain: Domain): Model

}