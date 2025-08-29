package dev.mammet.weatherkmp.geo_location.data.local

import androidx.room.Room
import androidx.room.RoomDatabase
import java.io.File

actual class DatabaseFactory {
    actual fun create(): RoomDatabase.Builder<GeolocationDatabase> {
        val os = System.getProperty("os.name").lowercase()
        val userHome = System.getProperty("user.home")
        val appDataDir = when {
            os.contains("win") -> File(System.getenv("APPDATA"), "weatherkmp")
            os.contains("mac") -> File(userHome, "Library/Application Support/weatherkmp")
            else -> File(userHome, ".local/share/weatherkmp")
        }
        if (!appDataDir.exists()){
            appDataDir.mkdirs()
        }

        val dbFile = File(appDataDir, GeolocationDatabase.DB_NAME)
        return Room.databaseBuilder(dbFile.absolutePath)
    }
}