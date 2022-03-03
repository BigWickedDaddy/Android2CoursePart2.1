package com.itis.android2coursepart21.data.api

import com.itis.android2coursepart21.data.api.response.NearWeather
import com.google.gson.annotations.SerializedName


data class NearCity(
    val cod: String,
    val count: Int,
    val list: List<NearWeather>,
    val message: String
)