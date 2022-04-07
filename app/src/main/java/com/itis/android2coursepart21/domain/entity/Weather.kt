package com.itis.android2coursepart21.domain.entity


data class Weather(
    val lat: Double,
    val lon: Double,
    val id: Int,
    val name: String,
    val wind: Any,
    val main: Any,
    val weather: Any,

    val description:Any,
    val deg:Int,
    val speed:Double,
    val pressure:Int,
    val temp:Double,
    val feelsLike:Double,
    val icon:String,

    val cod: Any,

) {

}
