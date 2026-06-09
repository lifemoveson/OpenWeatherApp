package com.example.openweatherapp.util

import com.example.openweatherapp.data.local.WeatherEntity
import com.example.openweatherapp.domain.model.Weather

fun WeatherDto.toDomain(city: String): Weather {
    val iconCode = weather.firstOrNull()?.icon ?: ""
    return Weather(
        city = city,
        temperature = main.temp,
        description = weather.firstOrNull()?.description ?: "",
        icon = iconCode,
        iconUrl = "https://openweathermap.org/img/wn/${iconCode}@2x.png",
        lastUpdated = System.currentTimeMillis()
    )
}

fun WeatherDto.toEntity(city: String): WeatherEntity {
    val iconCode = weather.firstOrNull()?.icon ?: ""
    return WeatherEntity(
        city = city,
        temperature = main.temp,
        description = weather.firstOrNull()?.description ?: "",
        icon = iconCode,
        iconUrl = "https://openweathermap.org/img/wn/${iconCode}@2x.png",
        timestamp = System.currentTimeMillis()
    )
}

fun WeatherEntity.toDomain(): Weather {
    return Weather(
        city = city,
        temperature = temperature,
        description = description,
        icon = icon,
        iconUrl = iconUrl,
        lastUpdated = timestamp
    )
}

data class GeoDto(
    val name: String,
    val lat: Double,
    val lon: Double,
    val country: String,
    val state: String? = null
)

data class WeatherDto(
    val weather: List<WeatherConditionDto>,
    val main: MainDto,
    val wind: WindDto,
    val name: String
)

data class WeatherConditionDto(
    val main: String,
    val description: String,
    val icon: String
)

data class MainDto(
    val temp: Double,
    val feelsLike: Double,
    val minTemp: Double,
    val maxTemp: Double,
    val humidity: Int
)

data class WindDto(
    val speed: Double,
    val deg: Int? = null
)