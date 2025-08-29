package dev.mammet.weatherkmp.geo_location.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

actual class DatabaseFactory(
    private val context: Context,
) {
    actual fun create(): RoomDatabase.Builder<GeolocationDatabase> {
        val appContext = context.applicationContext
        val dbFile = appContext.getDatabasePath(GeolocationDatabase.DB_NAME)

        return Room.databaseBuilder(
            context = appContext,
            name = dbFile.absolutePath
        )
    }
}