package com.itis.android2coursepart21.domain.usecase

import com.itis.android2coursepart21.domain.entity.Weather
import com.itis.android2coursepart21.domain.repository.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class getWeatherIdUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val dispatcher: CoroutineDispatcher

) {
    suspend operator fun invoke(id: Int): Weather{
        return withContext(dispatcher) {
            weatherRepository.getWeatherId(id)
        }
    }
}