package com.example.openweatherapp.domain.usecase

import com.example.openweatherapp.domain.model.Weather
import com.example.openweatherapp.network.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val repo: WeatherRepository
) {
    suspend operator fun invoke(city: String): Weather {
        return repo.getWeather(city)
    }
}