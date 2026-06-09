package com.example.openweatherapp.domain.model

data class Weather(
    val city: String,
    val temperature: Double,
    val description: String,
    val icon: String,
    val iconUrl: String,
    val lastUpdated: Long
)