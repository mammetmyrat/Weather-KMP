package dev.mammet.weatherkmp

import android.app.Application
import dev.mammet.weatherkmp.di.initKoin
import org.koin.android.ext.koin.androidContext

class WeatherApp: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin{
            androidContext(this@WeatherApp)
        }
    }
}