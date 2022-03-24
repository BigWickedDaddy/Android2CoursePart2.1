package com.itis.android2coursepart21.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import com.google.android.ads.mediationtestsuite.viewmodels.ViewModelFactory
import com.itis.android2coursepart21.presentation.MainActivity
import com.itis.android2coursepart21.R
import com.itis.android2coursepart21.data.WeatherRepositoryImpl
import com.itis.android2coursepart21.data.api.mapper.WeatherMapper
import com.itis.android2coursepart21.databinding.FragmentCityBinding
import com.itis.android2coursepart21.domain.entity.Weather
import com.itis.android2coursepart21.domain.usecase.getNearCityUseCase
import com.itis.android2coursepart21.domain.usecase.getWeatherCityUseCase
import com.itis.android2coursepart21.domain.usecase.getWeatherIdUseCase
import com.itis.android2coursepart21.presentation.viewmodels.CityViewModel
import kotlinx.coroutines.launch

class CityFragment : Fragment(R.layout.fragment_city) {

    private lateinit var getNearCityUseCase: getNearCityUseCase
    private lateinit var getWeatherCityUseCase: getWeatherCityUseCase
    private lateinit var getWeatherIdUseCase: getWeatherIdUseCase

    private lateinit var binding: FragmentCityBinding
    private lateinit var viewModel: CityViewModel
    private var windDirections: List<String>? = null
    //    private var binding: FragmentCityBinding? = null
//    private var windDirections: List<String>? = null
    private var id: Int? = null

//    private val repository by lazy {
//        WeatherRepositoryImpl()
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        (activity as MainActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }
        arguments?.getInt("ARG_CITY_ID").let {
            id = it
        }
        id?.let {
            setWeatherData(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCityBinding.bind(view)

        initObjects()
        initObservers()

        windDirections = listOf(
            getString(R.string.wind_n),
            getString(R.string.wind_ne),
            getString(R.string.wind_e),
            getString(R.string.wind_se),
            getString(R.string.wind_s),
            getString(R.string.wind_sw),
            getString(R.string.wind_w),
            getString(R.string.wind_nw),
        )
    }

    private fun initObjects() {
        val weatherRepository = WeatherRepositoryImpl(WeatherMapper())

        val factory = ViewModelFactory(
            getNearCityUseCase(WeatherRepositoryImpl(WeatherMapper())),
            getWeatherCityUseCase(WeatherRepositoryImpl(WeatherMapper())),
            getWeatherIdUseCase(WeatherRepositoryImpl(WeatherMapper())),
        )

        viewModel = ViewModelProvider(
            this,
            factory
        )[CityViewModel::class.java]
    }


    //private fun initAttrs(weatherResponse: WeatherResponse){
    private fun initAttrs(weatherResponse: Weather){
        binding?.apply {
            ivWeatherIcon.load(
                WeatherRepositoryImpl.WeatherDataHandler.setImageIconUrl(weatherResponse.icon)
            )
            tvState.text = weatherResponse.description.toString()
            tvFeelsLikeState.text = WeatherRepositoryImpl.WeatherDataHandler.convertTemperature(weatherResponse.feelsLike)
            tvCityTemperature.text = WeatherRepositoryImpl.WeatherDataHandler.convertTemperature(weatherResponse.temp)
            tvPressureMmhg.text = WeatherRepositoryImpl.WeatherDataHandler.convertPressure(weatherResponse.pressure)
            tvWindSpeedMs.text = WeatherRepositoryImpl.WeatherDataHandler.convertWindSpeed(weatherResponse.speed)
            tvWindDirectionCompass.text = windDirections?.let {
                WeatherRepositoryImpl.WeatherDataHandler.convertWindDegree(
                    weatherResponse.deg,
                    it
                )
            }
        }
        setName(weatherResponse.name)
    }

    private fun setName(name: String?) {
        (activity as MainActivity).supportActionBar?.apply { title = name }
    }

    private fun setWeatherData(id: Int) {
        lifecycleScope.launch {
            initAttrs(getWeatherIdUseCase(id))
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                returnToMain()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun returnToMain() {
        findNavController().navigateUp()
    }

//
//    private fun initObjects() {
//        getNearCityUseCase = getNearCityUseCase(
//            weatherRepository = WeatherRepositoryImpl(
//                api = DIContainer.api,
//                weatherMapper = WeatherMapper()
//            ),
//            dispatcher = Dispatchers.Default
//        )
//    }
}