package dev.mammet.weatherkmp.geo_location.local

import androidx.room.RoomDatabaseConstructor

expect object GeolocationConstructor: RoomDatabaseConstructor<GeolocationDatabase> {
    override fun initialize(): GeolocationDatabase
}