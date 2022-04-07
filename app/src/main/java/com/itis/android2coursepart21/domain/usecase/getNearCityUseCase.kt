package com.itis.android2coursepart21.domain.usecase

import com.itis.android2coursepart21.domain.entity.NearWeather
import com.itis.android2coursepart21.domain.repository.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class getNearCityUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val dispatcher: CoroutineDispatcher

){
    suspend operator fun invoke(lat: Double,
                                lon: Double,
                                count: Int): NearWeather{
        return withContext(dispatcher) {
            weatherRepository.getNearCity(lat, lon, count)
        }
    }
}