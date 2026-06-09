package com.example.openweatherapp

import androidx.lifecycle.SavedStateHandle
import com.example.openweatherapp.domain.model.Weather
import com.example.openweatherapp.domain.usecase.GetWeatherUseCase
import com.example.openweatherapp.network.connectivity.ConnectivityObserver
import com.example.openweatherapp.viewmodel.WeatherViewModel
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertNull
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.net.SocketTimeoutException
import java.net.UnknownHostException

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var useCase: GetWeatherUseCase
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var connectivityObserver: ConnectivityObserver

    private lateinit var viewModel: WeatherViewModel

    @Before
    fun setup() {

        useCase = mock()
        connectivityObserver = mock()
        savedStateHandle = SavedStateHandle()

        whenever(connectivityObserver.observe())
            .thenReturn(flowOf(ConnectivityObserver.Status.Available))

        viewModel = WeatherViewModel(
            useCase,
            savedStateHandle,
            connectivityObserver
        )
    }

    @Test
    fun search_success_updates_state_with_weather(): Unit = runTest {

        val weather = Weather(
            city = "Dallas",
            temperature = 30.0,
            description = "Sunny",
            icon = "01d",
            iconUrl = "https://www.example.com",
            lastUpdated = 123L
        )

        whenever(useCase("Dallas"))
            .thenReturn(weather)

        viewModel.search("Dallas")

        advanceUntilIdle()

        val state = viewModel.state.value

        assertEquals(weather, state.weather)
        assertFalse(state.isLoading)
        assertNull(state.error)
    }

    @Test
    fun search_unknownHost_sets_network_error() = runTest {

        whenever(useCase("Dallas"))
            .thenAnswer {
                throw UnknownHostException()
            }

        viewModel.search("Dallas")

        advanceUntilIdle()

        val state = viewModel.state.value

        assertEquals(
            "No internet connection. Please check your network and try again.",
            state.error
        )
    }

    @Test
    fun search_timeout_sets_timeout_error() = runTest {

        whenever(useCase("Dallas"))
            .thenAnswer {
                throw SocketTimeoutException()
            }

        viewModel.search("Dallas")

        advanceUntilIdle()

        val state = viewModel.state.value

        assertEquals(
            "Request timed out. Please try again.",
            state.error
        )
    }

    @Test
    fun connectivity_unavailable_sets_offline_true() = runTest {

        whenever(connectivityObserver.observe())
            .thenReturn(
                flowOf(
                    ConnectivityObserver.Status.Unavailable
                )
            )

        viewModel = WeatherViewModel(
            useCase,
            savedStateHandle,
            connectivityObserver
        )

        advanceUntilIdle()

        assertTrue(viewModel.state.value.isOffline)
    }
}