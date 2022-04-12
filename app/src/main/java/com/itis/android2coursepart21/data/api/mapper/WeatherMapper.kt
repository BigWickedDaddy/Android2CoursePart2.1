package com.itis.android2coursepart21.data.api.mapper

import com.itis.android2coursepart21.data.api.response.NearCity
import com.itis.android2coursepart21.data.api.response.WeatherResponse
import com.itis.android2coursepart21.data.api.response.Coord
import com.itis.android2coursepart21.domain.entity.NearWeather
import com.itis.android2coursepart21.domain.entity.Weather
import com.itis.android2coursepart21.domain.entity.WeatherNearCity

class WeatherMapper {

     fun map(response: WeatherResponse):Weather= Weather(
         lat = response.coord.lat,
         lon = response.coord.lon,
         id = response.id,
         name = response.name,

         wind = response.wind.speed,
         main = response.main,
         weather = response.weather,
         description = response.weather[0].description,
         deg = response.wind.deg,
         speed = response.wind.speed,
         pressure = response.main.pressure,
         temp = response.main.temp,
         feelsLike = response.main.feelsLike,
         icon = response.weather[0].icon,
         cod = response.cod,
    )
    fun mapNear(response: Coord):NearWeather = NearWeather(
        lat = response.lat,
        lon = response.lon,
        cnt = response.count,
        list = response.list,
    )

}