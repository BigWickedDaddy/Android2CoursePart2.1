package com.itis.android2coursepart21.presentation.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.android.ads.mediationtestsuite.viewmodels.ViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.itis.android2coursepart21.App
import com.itis.android2coursepart21.presentation.MainActivity
import com.itis.android2coursepart21.R
import com.itis.android2coursepart21.data.WeatherRepositoryImpl
import com.itis.android2coursepart21.data.api.mapper.WeatherMapper
import com.itis.android2coursepart21.data.api.response.Coord
import com.itis.android2coursepart21.presentation.WeatherAdapter
import com.itis.android2coursepart21.databinding.FragmentMainBinding
import com.itis.android2coursepart21.domain.usecase.getNearCityUseCase
import com.itis.android2coursepart21.domain.usecase.getWeatherCityUseCase
import com.itis.android2coursepart21.domain.usecase.getWeatherIdUseCase
import com.itis.android2coursepart21.presentation.viewmodels.CityViewModel
import com.itis.android2coursepart21.presentation.viewmodels.MainViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


class MainFragment : Fragment(R.layout.fragment_main) {

    private var binding: FragmentMainBinding? = null
    private var latitude: Double? = null
    private var longitude: Double? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var coordinates: Coord

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private val viewModel: MainViewModel by viewModels {
        factory
    }

    @SuppressLint("MissingPermission")
    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            if (it[Manifest.permission.ACCESS_FINE_LOCATION] == true || it[Manifest.permission.ACCESS_COARSE_LOCATION] == true) {
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    latitude = location.latitude
                    longitude = location.longitude
                }
            } else{
                latitude = DEFAULT_LATITUDE
                longitude = DEFAULT_LONGITUDE
            }
            latitude?.let { it1 ->
                longitude?.let { it2 ->
                    initWeatherAdapter(it1, it2, CITY_LIST_SIZE)
                }
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as App).appComponent.inject(this)

        setHasOptionsMenu(false)

        context?.let {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(it)
        }
        lastLocation()
        }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)

        initObservers()

        (activity as MainActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(false)
            title = getString(R.string.app_name)
        }

        binding?.apply {
            rvCities.run {
            }
            svCity.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String): Boolean {
                    return true
                }
                override fun onQueryTextSubmit(query: String?): Boolean {
                    checkData(query)
                    return false
                }
            })
        }
        lastLocation()
    }


    private fun initObservers() {
        viewModel.weatherDetail.observe(viewLifecycleOwner) {
            it.fold(onSuccess = { weatherData ->
                val bundle = Bundle().apply {
                    putInt("ARG_CITY_ID", id)
                }
                val options = NavOptions.Builder()
                    .build()

                findNavController().navigate(
                    R.id.action_mainFragment_to_cityFragment,
                    bundle,
                    options
                )
            }, onFailure = {
                Snackbar.make(
                    requireActivity().findViewById(R.id.container),
                    "No data",
                    Snackbar.LENGTH_LONG
                ).show()
            })
        }
        viewModel.weatherList.observe(viewLifecycleOwner) {
            it.fold(onSuccess = { result ->
                WeatherAdapter.submitList(result)
            }, onFailure = {
                Snackbar.make(
                    requireActivity().findViewById(R.id.container),
                    "No data",
                    Snackbar.LENGTH_LONG
                ).show()
            })
        }
        viewModel.weathernearcity.observe(viewLifecycleOwner) {
            it.fold(onSuccess = { result ->
                coordinates = result
            }, onFailure = {
                Log.e("Location", "No data(location)")
            })
        }
    }

    private fun checkData(city: String?) {
        city?.let {
            lifecycleScope.launch {
                val weatherResponse =getWeatherCityUseCase(city)
                if (weatherResponse.cod == HTTP_STATUS_NOT_FOUND){
                    Snackbar.make(
                        requireActivity().findViewById(R.id.container),
                        "Такого города не существует. ",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
                else cityFrag(weatherResponse.id)
            }
        }
    }




    private fun lastLocation() {
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }


    private fun initWeatherAdapter(lat: Double, lon: Double, count: Int) {
        lifecycleScope.launch {
            binding?.rvCities?.apply {
                adapter = WeatherAdapter(getNearCityUseCase(lat, lon, count).list) {
                    cityFrag(it)
                }
            }
        }
    }


    private fun cityFrag(id: Int) {
        val bundle = Bundle().apply {
            putInt("ARG_CITY_ID", id)
        }
        val options = NavOptions.Builder()
            .setLaunchSingleTop(true)
            .build()

        findNavController().navigate(
            R.id.action_mainFragment_to_cityFragment,
            bundle,
            options
        )
    }

    override fun onResume() {
        super.onResume()
        lastLocation()
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    companion object {
        private const val DEFAULT_LATITUDE = 55.788
        private const val DEFAULT_LONGITUDE = 49.122
        private const val CITY_LIST_SIZE = 15
        private const val HTTP_STATUS_NOT_FOUND = 404

    }


}