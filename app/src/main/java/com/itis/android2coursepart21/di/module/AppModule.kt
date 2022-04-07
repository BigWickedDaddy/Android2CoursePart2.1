package com.itis.android2coursepart21.di.module

import android.content.Context
import com.itis.android2coursepart21.App
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers


@Module
class AppModule {

    @Provides
    fun provideContext(app: App): Context = app.applicationContext

    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}