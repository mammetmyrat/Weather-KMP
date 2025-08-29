package dev.mammet.weatherkmp.geo_location.data.local

import androidx.room.RoomDatabaseConstructor

expect object GeolocationConstructor: RoomDatabaseConstructor<GeolocationDatabase> {
    override fun initialize(): GeolocationDatabase
}