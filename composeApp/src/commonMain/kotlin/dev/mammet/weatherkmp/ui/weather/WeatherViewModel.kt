package dev.mammet.weatherkmp.ui.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.mammet.weatherkmp.geo_location.domain.models.Geolocation
import dev.mammet.weatherkmp.geo_location.domain.repository.GeolocationRepository
import dev.mammet.weatherkmp.utils.Response
import dev.mammet.weatherkmp.utils.Util
import dev.mammet.weatherkmp.weather.domain.models.Daily
import dev.mammet.weatherkmp.weather.domain.models.Weather
import dev.mammet.weatherkmp.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val repository: WeatherRepository,
    private val geolocationRepository: GeolocationRepository,
) : ViewModel() {
    private val _weather = MutableStateFlow(WeatherState())
    val weatherState = _weather.asStateFlow()

    init {
        getGeolocation()
        observerWeatherData()

    }

    private fun observerWeatherData() = viewModelScope.launch {
        repository.weatherData.collect { response ->
            when (response) {
                is Response.Loading -> {
                    _weather.update { state ->
                        state.copy(isLoading = true, error = null)
                    }
                }

                is Response.Success -> {
                    val todayDailyWeatherInfo =
                        response.data.daily.weatherInfo.find { weatherInfo ->
                            Util.isTodayDate(weatherInfo.time)
                        }
                    _weather.update { state ->
                        state.copy(
                            isLoading = false,
                            error = null,
                            weather = response.data,
                            dailyWeatherInfo = todayDailyWeatherInfo
                        )
                    }
                }

                is Response.Error.DefaultError -> {
                    _weather.update { state ->
                        state.copy(isLoading = false, error = "Error Occurred")
                    }
                }

                is Response.Error.NetworkError -> {
                    _weather.update { state ->
                        state.copy(isLoading = false, error = "No Network")
                    }
                }

                is Response.Error.SerializationError -> {
                    _weather.update { state ->
                        state.copy(isLoading = false, error = "Failed to Serialize Data")
                    }
                }

                is Response.Error.HttpError -> {
                    _weather.update { state ->
                        state.copy(isLoading = false, error = response.code.toString())
                    }
                }

                else -> {}
            }
        }
    }

    fun getGeolocation() = viewModelScope.launch {
        geolocationRepository.geolocation.collect { geolocation ->
            _weather.update { state ->
                state.copy(selectedLocation = geolocation)
            }
        }
    }

    fun fetchWeatherData() = viewModelScope.launch {
        weatherState.value.selectedLocation?.let { geolocation ->
            repository.fetchWeatherData(
                latitude = geolocation.latitude.toFloat(),
                longitude = geolocation.latitude.toFloat(),
                timeZone = geolocation.timeZone
            )
        }
    }

}

data class WeatherState(
    val weather: Weather? = null,
    val error: String? = null,
    val isLoading: Boolean = false,
    val dailyWeatherInfo: Daily.WeatherInfo? = null,
    val selectedLocation: Geolocation? = null,
)