package com.itis.android2coursepart21

import android.app.Application
import com.itis.android2coursepart21.di.AppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
    }
}