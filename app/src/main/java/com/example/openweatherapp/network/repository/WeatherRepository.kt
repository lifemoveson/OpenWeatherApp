package com.example.openweatherapp.network.repository

import com.example.openweatherapp.domain.model.Weather

interface WeatherRepository {
    suspend fun getWeather(city: String): Weather
}