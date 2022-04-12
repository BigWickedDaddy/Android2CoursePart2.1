package com.itis.android2coursepart21.data

import com.google.android.gms.location.FusedLocationProviderClient
import com.itis.android2coursepart21.BuildConfig
import com.itis.android2coursepart21.data.api.api
import com.itis.android2coursepart21.domain.entity.Weather
import com.itis.android2coursepart21.domain.repository.WeatherRepository
import com.itis.android2coursepart21.data.api.mapper.WeatherMapper
import com.itis.android2coursepart21.domain.entity.NearWeather
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import kotlin.math.roundToInt

class WeatherRepositoryImpl @Inject constructor(
    private val api: Api,
    private val WeatherMapper: WeatherMapper
    private val fusedLocationProviderClient: FusedLocationProviderClient
): WeatherRepository {


    override suspend fun getWeatherCity(city: String): Weather {
        return WeatherMapper.map(api.getWeatherCity(city))
    }

    override suspend fun getWeatherId(id: Int): Weather {
        return WeatherMapper.map(api.getWeatherId(id))
    }

    override suspend fun getNearCity(
        lat: Double,
        lon: Double,
        count: Int): NearWeather {
        return WeatherMapper.mapNear(api.getNearCity(lat, lon, count))
    }


    object WeatherDataHandler {
        fun setImageIconUrl(iconCode: String?): String = BASE_IMAGE_URL + iconCode + IMAGE_URL_SUFFIX

        fun convertTemperature(temperature: Double?): String = temperature.toString() + DEGREE_UNIT_UNICODE

        fun convertPressure(pressure: Int?): String = pressure.toString() + PRESSURE_UNITS

        fun convertWindSpeed(speed: Double?): String = speed.toString() + WIND_SPEED_UNITS

        fun convertWindDegree(degree: Int, directions: List<String>): String {
            val degrees = (degree * directions.size / DEGREES).toDouble().roundToInt()
            return directions[degrees % directions.size]
        }
    }

    companion object {
        private const val DEGREES = 360
        private const val BASE_IMAGE_URL = "http://openweathermap.org/img/wn/"
        private const val IMAGE_URL_SUFFIX = "@2x.png"
        private const val PRESSURE_UNITS = "mmHg"
        private const val WIND_SPEED_UNITS = "m/s"
        private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        private const val API_KEY = "56fc6c6cb76c0864b4cd055080568268"
        private const val QUERY_API_KEY = "appid"
        private const val API_UNITS = "metric"
        private const val QUERY_UNITS_VALUE = "units"
        private const val API_LOCALE = "ru"
        private const val QUERY_LOCALE_VALUE = "lang"
        private const val DEGREE_UNIT_UNICODE = "\u2103"
    }
}