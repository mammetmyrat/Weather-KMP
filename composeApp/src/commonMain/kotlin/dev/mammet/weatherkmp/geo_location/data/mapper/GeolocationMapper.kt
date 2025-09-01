package dev.mammet.weatherkmp.geo_location.data.mapper

import dev.mammet.weatherkmp.common.data.Mapper
import dev.mammet.weatherkmp.geo_location.data.local.models.GeolocationEntity
import dev.mammet.weatherkmp.geo_location.domain.models.Geolocation
import dev.mammet.weatherkmp.utils.K

class GeolocationMapper: Mapper<Geolocation, GeolocationEntity> {
    override fun mapToDomainOrNull(model: GeolocationEntity?): Geolocation? {
        return model?.run {
            Geolocation(
                id = id,
                name = name,
                countryName = countryName,
                countryCode = countryCode,
                flagUrl = K.flagUrl(countryCode),
                countryId = countryId,
                latitude = latitude,
                longitude = longitude,
                timeZone = timeZone,
                elevation = elevation
            )
        }
    }

    override fun mapFromDomain(domain: Geolocation): GeolocationEntity {
       return domain.run {
            GeolocationEntity(
                id = id,
                name = name,
                countryName = countryName,
                countryCode = countryCode,
                countryId = countryId,
                latitude = latitude,
                longitude = longitude,
                timeZone = timeZone,
                elevation = elevation,
            )
        }
    }

}