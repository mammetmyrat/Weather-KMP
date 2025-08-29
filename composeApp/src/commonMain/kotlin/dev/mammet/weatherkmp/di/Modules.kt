package dev.mammet.weatherkmp.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import dev.mammet.weatherkmp.geo_location.data.local.DatabaseFactory
import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    single {
        get<DatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }
}