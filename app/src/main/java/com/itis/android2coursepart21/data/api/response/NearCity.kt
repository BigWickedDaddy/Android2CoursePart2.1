package com.itis.android2coursepart21.data.api.response

import com.itis.android2coursepart21.data.api.response.NearWeather

data class NearCity(
    val cod: String,
    val count: Int,
    val list: List<NearWeather>,
    val message: String
)