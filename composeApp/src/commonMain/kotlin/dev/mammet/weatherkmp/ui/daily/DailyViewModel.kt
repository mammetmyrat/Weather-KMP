package dev.mammet.weatherkmp.ui.daily

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.mammet.weatherkmp.geo_location.domain.models.Geolocation
import dev.mammet.weatherkmp.geo_location.domain.repository.GeolocationRepository
import dev.mammet.weatherkmp.utils.Response
import dev.mammet.weatherkmp.weather.domain.models.Daily
import dev.mammet.weatherkmp.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DailyViewModel(
    private val repository: WeatherRepository,
    private val geolocationRepository: GeolocationRepository
): ViewModel() {
    private var _dailyState = MutableStateFlow(DailyState())
    val dailyState = _dailyState.asStateFlow()

    init {
        observeWeatherData()
    }

    private fun observeWeatherData() = viewModelScope.launch {
        repository.weatherData.collect{response ->
            when(response){
                is Response.Loading -> {
                    _dailyState.update {state ->
                        state.copy(
                            isLoading = true,
                            error = null,
                        )
                    }
                }

                is Response.Success ->{
                    _dailyState.update {state ->
                        state.copy(
                            isLoading = false,
                            daily = response.data.daily,
                            error = null,
                        )
                    }
                }

                is Response.Error.DefaultError -> {
                    _dailyState.update {state ->
                        state.copy(
                            isLoading = false,
                            error = "Error Occurred"
                        )
                    }
                }

                is Response.Error.NetworkError -> {
                    _dailyState.update {state ->
                        state.copy(
                            isLoading = false,
                            error = "No Network"
                        )
                    }
                }

                is Response.Error.SerializationError -> {
                    _dailyState.update {state ->
                        state.copy(
                            isLoading = false,
                            error = "Failed to serialize data"
                        )
                    }
                }

                is Response.Error.HttpError -> {
                    _dailyState.update {state ->
                        state.copy(
                            isLoading = false,
                            error = response.code.toString()
                        )
                    }
                }

                else -> {}

            }

        }
    }
}

data class DailyState(
    val daily: Daily? = null,
    val error: String? = null,
    val isLoading: Boolean = false,
    val geolocation: Geolocation? = null,
)