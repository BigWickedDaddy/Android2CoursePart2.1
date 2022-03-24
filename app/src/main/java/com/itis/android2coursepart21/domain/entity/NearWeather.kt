package com.itis.android2coursepart21.domain.entity

import com.itis.android2coursepart21.data.api.response.NearWeather

data class NearWeather(
    val lat: Double,
    val lon: Double,
    val cnt: Int,
    val list: List<NearWeather>

//    val list: List<NearWeather>,
) {
}
