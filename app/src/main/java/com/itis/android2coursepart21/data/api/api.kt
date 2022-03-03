package com.itis.android2coursepart21.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface api {
    @GET("find")
    suspend fun getNearCity(@Query("lon") longitude: Double,
                            @Query("lat") latitude: Double,
                            @Query("cnt") count: Int): NearCity

    @GET("weather")
    suspend fun getWeatherCity(@Query("q") city: String): WeatherResponse

    @GET("weather")
    suspend fun getWeatherByCoordinates(@Query("lat") latitude: Double, @Query("lon") longitude:Double
    ): WeatherResponse

    @GET("weather")
    suspend fun getWeatherId(@Query("id") id: Int): WeatherResponse
}