package com.example.openweatherapp.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.util.copy
import com.example.openweatherapp.domain.model.Weather
import com.example.openweatherapp.domain.usecase.GetWeatherUseCase
import com.example.openweatherapp.network.connectivity.ConnectivityObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val connectivityObserver: ConnectivityObserver
) : ViewModel() {

    private val _state = MutableStateFlow(WeatherUiState())
    val state = _state.asStateFlow()

    init {

        savedStateHandle
            .get<String>("city")
            ?.let { search(it) }

        observeConnectivity()
    }

    fun search(city: String) {

        savedStateHandle["city"] = city

        viewModelScope.launch {

            _state.value = WeatherUiState(isLoading = true)

            try {
                val weather = getWeatherUseCase(city)

                _state.value = WeatherUiState(
                    isLoading = false,
                    weather = weather,
                    error = null
                )

            } catch (e: UnknownHostException) {

                _state.update {
                    it.copy(
                        isLoading = false,
                        error = "No internet connection. Please check your network and try again."
                    )
                }

            } catch (e: SocketTimeoutException) {

                _state.update {
                    it.copy(
                        isLoading = false,
                        error = "Request timed out. Please try again."
                    )
                }

            } catch (e: Exception) {

                _state.update {
                    it.copy(
                        isLoading = false,
                        error = "Unable to load weather information."
                    )
                }
            }
        }
    }

    private fun observeConnectivity() {

        connectivityObserver.observe()
            .onEach { status ->

                _state.update {
                    it.copy(
                        isOffline = status == ConnectivityObserver.Status.Unavailable
                    )
                }
            }
            .launchIn(viewModelScope)
    }

}

data class WeatherUiState(
    val isLoading: Boolean = false,
    val weather: Weather? = null,
    val error: String? = null,
    val isOffline: Boolean = false
)