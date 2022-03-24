package com.itis.android2coursepart21.domain.entity

//import com.itis.android2coursepart21.data.api.response.NearWeather

data class WeatherNearCity(
    val cod: String,
    val count: Int,
//    val list: List<NearWeather>,
    val message: String
    )