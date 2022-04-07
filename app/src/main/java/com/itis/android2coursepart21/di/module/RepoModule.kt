package com.itis.android2coursepart21.di.module

import com.itis.android2coursepart21.data.WeatherRepositoryImpl
import com.itis.android2coursepart21.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module

@Module
interface RepoModule {

    @Binds
    fun weatherRepository(
        impl: WeatherRepositoryImpl
    ): WeatherRepository

}
