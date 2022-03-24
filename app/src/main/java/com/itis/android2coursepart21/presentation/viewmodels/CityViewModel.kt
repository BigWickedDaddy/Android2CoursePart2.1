package com.itis.android2coursepart21.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itis.android2coursepart21.domain.entity.Weather
import com.itis.android2coursepart21.domain.usecase.getWeatherIdUseCase
import kotlinx.coroutines.launch

class CityViewModel(
    private val getWeatherIdUseCase: getWeatherIdUseCase
): ViewModel() {

    private var _weatherDetail: MutableLiveData<Result<Weather>> = MutableLiveData()
    val weatherDetail: LiveData<Result<Weather>> = _weatherDetail

    fun getWeatherById(id: Int) {
        viewModelScope.launch {
            try {
                val weather = getWeatherIdUseCase(id)
                _weatherDetail.value = Result.success(weather)
            } catch (ex: Exception) {
                _weatherDetail.value = Result.failure(ex)
            }
        }
    }
}