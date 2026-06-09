package com.example.openweatherapp.network.repository

import com.example.openweatherapp.data.local.WeatherDao
import com.example.openweatherapp.data.remote.WeatherApi
import com.example.openweatherapp.domain.model.Weather
import com.example.openweatherapp.network.repository.WeatherRepositoryImpl.ApiKeys.OPEN_WEATHER_KEY
import com.example.openweatherapp.util.toDomain
import com.example.openweatherapp.util.toEntity
import kotlin.collections.first

class WeatherRepositoryImpl(
    private val api: WeatherApi,
    private val dao: WeatherDao
) : WeatherRepository {

    object ApiKeys {
        const val OPEN_WEATHER_KEY = "YOUR_API_KEY"
    }

    override suspend fun getWeather(city: String): Weather {

        return try {

            val geo = api.getCoordinates(
                city,
                limit = 25,
                apiKey = OPEN_WEATHER_KEY
            ).first()

            val dto = api.getWeather(
                lat = geo.lat,
                lon = geo.lon,
                apiKey = OPEN_WEATHER_KEY
            )

            val domain = dto.toDomain(city)

            dao.insert(dto.toEntity(city))

            domain

        } catch (e: Exception) {

            dao.getWeather(city)?.toDomain()
                ?: throw e
        }
    }
}