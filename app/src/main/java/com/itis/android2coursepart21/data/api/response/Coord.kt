package com.itis.android2coursepart21.data.api.response


import com.google.gson.annotations.SerializedName

data class Coord(
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lon")
    val lon: Double,

    val count: Int,
    val list: List<NearWeather>,
)