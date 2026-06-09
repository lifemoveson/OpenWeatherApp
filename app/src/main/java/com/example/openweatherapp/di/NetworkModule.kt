package com.example.openweatherapp.di

import android.content.Context
import com.example.openweatherapp.data.remote.WeatherApi
import com.example.openweatherapp.network.connectivity.ConnectivityObserver
import com.example.openweatherapp.network.connectivity.ConnectivityObserverImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun retrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    fun api(retrofit: Retrofit): WeatherApi =
        retrofit.create(WeatherApi::class.java)

    @Provides
    @Singleton
    fun provideConnectivityObserver(
        @ApplicationContext context: Context
    ): ConnectivityObserver = ConnectivityObserverImpl(context)
}