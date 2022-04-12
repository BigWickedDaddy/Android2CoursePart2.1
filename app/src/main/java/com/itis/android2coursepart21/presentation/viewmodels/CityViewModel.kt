package com.itis.android2coursepart21.presentation.viewmodels

import androidx.lifecycle.*
import com.itis.android2coursepart21.domain.entity.Weather
import com.itis.android2coursepart21.domain.usecase.getWeatherIdUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import javax.inject.Inject

class CityViewModel @AssistedInject constructor(
    @Assisted private val id: Int,
    private val getWeatherIdUseCase: getWeatherIdUseCase
): ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(cityId: Int): CityViewModel
    }

    private var _weatherDetail: MutableLiveData<Result<Weather>> = MutableLiveData()
    val weatherDetail: LiveData<Result<Weather>> = _weatherDetail

    fun getWeatherById() {
        viewModelScope.launch {
            try {
                val weather = getWeatherIdUseCase(id)
                _weatherDetail.value = Result.success(weather)
            } catch (ex: Exception) {
                _weatherDetail.value = Result.failure(ex)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            cityId: Int
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                assistedFactory.create(cityId) as T
        }
    }
}
