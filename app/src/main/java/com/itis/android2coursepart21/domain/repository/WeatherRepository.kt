package com.itis.android2coursepart21.domain.repository

import com.itis.android2coursepart21.domain.entity.NearWeather
import com.itis.android2coursepart21.domain.entity.Weather

interface WeatherRepository {
    suspend fun getNearCity(
        lat: Double,
        lon: Double,
        count: Int): NearWeather

    suspend fun getWeatherId(id: Int): Weather

    suspend fun getWeatherCity(city: String): Weather
}