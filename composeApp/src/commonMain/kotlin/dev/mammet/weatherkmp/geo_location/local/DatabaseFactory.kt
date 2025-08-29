package dev.mammet.weatherkmp.geo_location.local

import androidx.room.RoomDatabase

expect class DatabaseFactory {
    fun create(): RoomDatabase.Builder<GeolocationDatabase>
}