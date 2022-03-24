package com.itis.android2coursepart21.domain.usecase

import com.itis.android2coursepart21.domain.entity.Weather
import com.itis.android2coursepart21.domain.repository.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class getWeatherCityUseCase(
    private val weatherRepository: String,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
){
    suspend operator fun invoke(city: String): Weather{
        return withContext(dispatcher) {
            weatherRepository.getWeatherCity(city)
        }
    }
}