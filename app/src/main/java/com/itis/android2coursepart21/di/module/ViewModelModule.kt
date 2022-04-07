package com.itis.android2coursepart21.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.itis.android2coursepart21.di.ViewModelKey
import com.itis.android2coursepart21.presentation.viewmodels.CityViewModel
import com.itis.android2coursepart21.presentation.viewmodels.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(
        viewModel: MainViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CityViewModel::class)
    fun bindCityViewModel(
        viewModel: CityViewModel
    ): ViewModel
}