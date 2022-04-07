package com.itis.android2coursepart21.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itis.android2coursepart21.data.api.response.Coord
import com.itis.android2coursepart21.domain.entity.NearWeather
import com.itis.android2coursepart21.domain.entity.Weather
import com.itis.android2coursepart21.domain.usecase.getNearCityUseCase
import com.itis.android2coursepart21.domain.usecase.getWeatherCityUseCase
import com.itis.android2coursepart21.domain.usecase.getWeatherIdUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject


class MainViewModel @Inject constructor(
    private var getNearCityUseCase: getNearCityUseCase,
    private var getWeatherCityUseCase: getWeatherCityUseCase,
    private var getWeatherIdUseCase: getWeatherIdUseCase
) : ViewModel() {

    private var _weather: MutableLiveData<Result<Weather>> = MutableLiveData()
    val weatherDetail: LiveData<Result<Weather>> = _weather

    private var _weathernearcity: MutableLiveData<Result<Coord>> = MutableLiveData()
    val weathernearcity: LiveData<Result<Coord>> = _weathernearcity

    private var _nearweather: MutableLiveData<Result<MutableList<NearWeather>>> = MutableLiveData()
    val weatherList: LiveData<Result<MutableList<NearWeather>>> = _nearweather


    fun getNearCity(lat: Double,
                    lon: Double,
                    count: Int) {
        viewModelScope.launch {
            try {
                val weatherList = getNearCityUseCase(lat, lon , count)
                _nearweather.value = Result.success(weatherList)
            } catch (ex: Exception) {
                _nearweather.value = Result.failure(ex)
            }
        }
    }

    fun getWeatherCity(city: String) {
        viewModelScope.launch {
            try {
                val weatherTest =
                    getWeatherCityUseCase(city)
                _weather.value = Result.success(weatherTest)
            } catch (ex: Exception) {
                _weather.value = Result.failure(ex)
            }
        }
    }

    fun getWeatherId(id: Int) {
        viewModelScope.launch {
            try {
                val id = getWeatherIdUseCase(id)
                _weathernearcity.value = Result.success(id)
            } catch (ex: Exception) {
                _weathernearcity.value = Result.failure(ex)
            }
        }
    }
}