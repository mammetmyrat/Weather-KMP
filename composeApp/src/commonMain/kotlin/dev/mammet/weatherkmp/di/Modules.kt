package dev.mammet.weatherkmp.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import dev.mammet.weatherkmp.common.data.HttpClientFactory
import dev.mammet.weatherkmp.common.data.Mapper
import dev.mammet.weatherkmp.geo_location.data.local.DatabaseFactory
import dev.mammet.weatherkmp.geo_location.data.local.GeolocationDatabase
import dev.mammet.weatherkmp.geo_location.data.local.models.GeolocationEntity
import dev.mammet.weatherkmp.geo_location.data.mapper.GeolocationMapper
import dev.mammet.weatherkmp.geo_location.data.remote.GeolocationRemoteApiService
import dev.mammet.weatherkmp.geo_location.data.remote.models.GeolocationRemoteApiServiceImpl
import dev.mammet.weatherkmp.geo_location.data.repository.GeolocationRepositoryImpl
import dev.mammet.weatherkmp.geo_location.domain.models.Geolocation
import dev.mammet.weatherkmp.geo_location.domain.repository.GeolocationRepository
import dev.mammet.weatherkmp.ui.home.HomeViewModel
import dev.mammet.weatherkmp.utils.provideExternalCoroutineScope
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    single {
        get<DatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }
    single { get<GeolocationDatabase>().geolocationDao() }
    single { HttpClientFactory.create(get()) }
    single { provideExternalCoroutineScope() }.bind()
    singleOf(::GeolocationRemoteApiServiceImpl).bind<GeolocationRemoteApiService>()
    singleOf(::GeolocationRepositoryImpl).bind<GeolocationRepository>()
    singleOf(::GeolocationMapper).bind<Mapper<Geolocation, GeolocationEntity>>()

    viewModelOf(::HomeViewModel)
}