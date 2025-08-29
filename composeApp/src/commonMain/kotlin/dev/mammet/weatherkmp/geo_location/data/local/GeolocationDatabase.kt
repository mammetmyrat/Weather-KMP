package dev.mammet.weatherkmp.geo_location.data.local

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import dev.mammet.weatherkmp.geo_location.data.local.models.GeolocationEntity

@Database(entities = [GeolocationEntity::class], version = 1)
@ConstructedBy(GeolocationConstructor::class)
abstract class GeolocationDatabase: RoomDatabase() {

    companion object{
        const val DB_NAME = "geolocation_db"
    }

    abstract fun geolocationDao(): GeolocationDao
}