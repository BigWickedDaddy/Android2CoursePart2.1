package com.itis.android2coursepart21.di

import com.itis.android2coursepart21.App
import com.itis.android2coursepart21.di.module.*
import com.itis.android2coursepart21.presentation.MainActivity
import com.itis.android2coursepart21.presentation.fragments.CityFragment
import com.itis.android2coursepart21.presentation.fragments.MainFragment
import dagger.BindsInstance
import dagger.Component

class AppComponent {
    @Component(
        modules = [
            AppModule::class,
            NetModule::class,
            RepoModule::class,
            ViewModelModule::class
        ]
    )
    interface AppComponent {

        fun inject(mainActivity: MainActivity)
        fun inject(mainFragment: MainFragment)
        fun inject(cityFragment: CityFragment)

        @Component.Builder
        interface Builder {

            @BindsInstance
            fun application(application: App): Builder

            fun build(): AppComponent
        }
    }
}