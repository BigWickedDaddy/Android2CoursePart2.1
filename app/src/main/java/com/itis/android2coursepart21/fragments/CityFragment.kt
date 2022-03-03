package com.itis.android2coursepart21.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import com.itis.android2coursepart21.MainActivity
import com.itis.android2coursepart21.R
import com.itis.android2coursepart21.data.WeatherRepository
import com.itis.android2coursepart21.data.api.WeatherResponse
import com.itis.android2coursepart21.databinding.FragmentCityBinding
import kotlinx.coroutines.launch

class CityFragment : Fragment(R.layout.fragment_city) {

    private var binding: FragmentCityBinding? = null
    private var windDirections: List<String>? = null
    private var id: Int? = null

    private val repository by lazy {
        WeatherRepository()
    }

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
        windDirections = listOf(
            getString(R.string.wind_n),
            getString(R.string.wind_ne),
            getString(R.string.wind_e),
            getString(R.string.wind_se),
            getString(R.string.wind_s),
            getString(R.string.wind_sw),
            getString(R.string.wind_w),
            getString(R.string.wind_nw)
        )
    }

    private fun initAttrs(weatherResponse: WeatherResponse){
        binding?.apply {
            ivWeatherIcon.load(
                WeatherRepository.WeatherDataHandler.setImageIconUrl(weatherResponse.weather[0].icon)
            )
            tvState.text = weatherResponse.weather[0].description
            tvFeelsLikeState.text = WeatherRepository.WeatherDataHandler.convertTemperature(weatherResponse.main.feelsLike)
            tvCityTemperature.text = WeatherRepository.WeatherDataHandler.convertTemperature(weatherResponse.main.temp)
            tvPressureMmhg.text = WeatherRepository.WeatherDataHandler.convertPressure(weatherResponse.main.pressure)
            tvWindSpeedMs.text = WeatherRepository.WeatherDataHandler.convertWindSpeed(weatherResponse.wind.speed)
            tvWindDirectionCompass.text = windDirections?.let {
                WeatherRepository.WeatherDataHandler.convertWindDegree(
                    weatherResponse.wind.deg,
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
            initAttrs(repository.getWeatherId(id))
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

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}