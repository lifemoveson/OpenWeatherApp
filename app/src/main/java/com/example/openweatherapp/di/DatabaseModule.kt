package com.example.openweatherapp.di

import android.content.Context
import androidx.room.Room
import com.example.openweatherapp.data.local.WeatherDao
import com.example.openweatherapp.data.local.WeatherDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun db(@ApplicationContext context: Context): WeatherDatabase =
        Room.databaseBuilder(
            context,
            WeatherDatabase::class.java,
            "weather.db"
        ).fallbackToDestructiveMigration().build()


    @Provides
    fun provideWeatherDao(
        db: WeatherDatabase
    ): WeatherDao {
        return db.weatherDao()
    }
}