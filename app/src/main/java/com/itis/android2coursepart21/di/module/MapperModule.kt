package com.itis.android2coursepart21.di.module

import com.itis.android2coursepart21.data.api.mapper.WeatherMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class MapperModule {
    @Provides
    fun provideWeatherMapper(): WeatherMapper = WeatherMapper()
}