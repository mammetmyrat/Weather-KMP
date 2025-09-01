package dev.mammet.weatherkmp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.mammet.weatherkmp.geo_location.domain.models.Geolocation
import dev.mammet.weatherkmp.geo_location.domain.repository.GeolocationRepository
import dev.mammet.weatherkmp.utils.Response
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val geolocationRepository: GeolocationRepository
) : ViewModel() {
    private val _homeState = MutableStateFlow(HomeState())
    val homeState = _homeState.asStateFlow()

    init {
        getGeolocation()
    }

    fun getGeolocation() = viewModelScope.launch {
        geolocationRepository.geolocation.collect {
            _homeState.update { state ->
                state.copy(
                    selectedLocation = it,
                    isLocationSelected = it != null
                )
            }
        }
    }

    fun saveFavoriteLocation() = viewModelScope.launch {
        homeState.value.selectedLocation?.let {
            geolocationRepository.upsertLocation(it)
        }
    }

    fun setSelectedLocation(geolocation: Geolocation) {
        _homeState.update { state ->
            state.copy(
                selectedLocation = geolocation.copy(id = 1),
                isLocationSelected = true
            )

        }
    }

    fun fetchGeolocation(query: String) {
        viewModelScope.launch {
            geolocationRepository.fetchGeoLocation(query).collect { result ->
                when (result) {
                    is Response.Success -> {
                        _homeState.update { state ->
                            state.copy(
                                isLoading = false,
                                error = null,
                                geolocations = result.data,
                            )
                        }
                    }

                    Response.Error.DefaultError -> {
                        _homeState.update {state ->
                            state.copy(
                                isLoading = false,
                                error = "Error Occurred"
                            )
                        }
                    }
                    Response.Error.NetworkError -> {
                        _homeState.update { state ->
                            state.copy(
                                isLoading = false,
                                error = "Network Error"
                            )
                        }
                    }
                    Response.Error.SerializationError -> {
                        _homeState.update { state ->
                            state.copy(
                                isLoading = false,
                                error = "Failed to Serialize Data"
                            )
                        }
                    }

                    is Response.Error.HttpError -> {
                        _homeState.update { state ->
                            state.copy(
                                isLoading = false,
                                error = result.code.toString()
                            )
                        }
                    }
                    Response.Loading -> {
                        _homeState.update { state ->
                            state.copy(
                                isLoading = true, error = null
                            )
                        }
                    }
                }
            }
        }
    }

}

data class HomeState(
    val isLocationSelected: Boolean = false,
    val selectedLocation: Geolocation? = null,
    val query: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val geolocations: List<Geolocation> = emptyList()
)