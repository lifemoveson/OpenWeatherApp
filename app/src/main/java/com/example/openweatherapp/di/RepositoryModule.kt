package com.example.openweatherapp.di

import com.example.openweatherapp.data.local.WeatherDao
import com.example.openweatherapp.data.remote.WeatherApi
import com.example.openweatherapp.network.repository.WeatherRepository
import com.example.openweatherapp.network.repository.WeatherRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun repo(
        api: WeatherApi,
        dao: WeatherDao
    ): WeatherRepository =
        WeatherRepositoryImpl(api, dao)
}