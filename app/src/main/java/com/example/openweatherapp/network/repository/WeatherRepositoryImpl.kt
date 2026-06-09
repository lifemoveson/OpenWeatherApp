package com.example.openweatherapp.network.repository

import com.example.openweatherapp.data.local.WeatherDao
import com.example.openweatherapp.data.remote.WeatherApi
import com.example.openweatherapp.domain.model.Weather
import com.example.openweatherapp.util.toDomain
import com.example.openweatherapp.util.toEntity
import kotlin.collections.first

class WeatherRepositoryImpl(
    private val api: WeatherApi,
    private val dao: WeatherDao
) : WeatherRepository {

    private val API_KEY = "916e5dfcc668807ea01a90a1d9e712bd"
    override suspend fun getWeather(city: String): Weather {

        return try {

            val geo = api.getCoordinates(
                city,
                limit = 25,
                apiKey = API_KEY
            ).first()

            val dto = api.getWeather(
                lat = geo.lat,
                lon = geo.lon,
                apiKey = API_KEY
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