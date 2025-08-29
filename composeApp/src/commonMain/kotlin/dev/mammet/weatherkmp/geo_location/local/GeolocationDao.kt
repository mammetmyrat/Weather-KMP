package dev.mammet.weatherkmp.geo_location.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import dev.mammet.weatherkmp.geo_location.local.models.GeolocationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GeolocationDao {

    @Upsert
    @Transaction
    suspend fun upsertGeolocation(geolocation: GeolocationEntity)

    @Query("SELECT * FROM geolocation_table LIMIT 1")
    fun getGeolocation(): Flow<GeolocationEntity?>

    @Query("DELETE FROM geolocation_table")
    @Transaction
    suspend fun clearGeolocation()
}