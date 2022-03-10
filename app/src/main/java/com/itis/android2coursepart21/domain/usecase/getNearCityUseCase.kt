package com.itis.android2coursepart21.domain.usecase

import com.itis.android2coursepart21.domain.entity.NearWeather
import com.itis.android2coursepart21.domain.repository.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class getNearCityUseCase(
    private val weatherRepository: WeatherRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

){
    suspend operator fun invoke(lat: Double,
                                lon: Double,
                                count: Int): NearWeather{
        return withContext(dispatcher) {
            weatherRepository.getNearCity(lat, lon, count)
        }
    }
}