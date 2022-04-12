package com.itis.android2coursepart21.di.module

import com.google.android.gms.common.api.Api
import com.itis.android2coursepart21.BuildConfig

import com.itis.android2coursepart21.di.qualifier.ApiKeyInterceptor
import com.itis.android2coursepart21.di.qualifier.ApiUnitsInterceptor
import com.itis.android2coursepart21.di.qualifier.LoggingInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val DEGREES = 360
private const val BASE_IMAGE_URL = "http://openweathermap.org/img/wn/"
private const val IMAGE_URL_SUFFIX = "@2x.png"
private const val PRESSURE_UNITS = "mmHg"
private const val WIND_SPEED_UNITS = "m/s"
private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
private const val API_KEY = "56fc6c6cb76c0864b4cd055080568268"
private const val QUERY_API_KEY = "appid"
private const val API_UNITS = "metric"
private const val QUERY_UNITS_VALUE = "units"
private const val API_LOCALE = "ru"
private const val QUERY_LOCALE_VALUE = "lang"
private const val DEGREE_UNIT_UNICODE = "\u2103"

@Module
@InstallIn(SingletonComponent::class)
class NetModule {

    @Provides
    @ApiKeyInterceptor
    fun providesApiKeyInterceptor() = Interceptor { chain ->
        val original = chain.request()
        val newURL = original.url.newBuilder()
            .addQueryParameter(QUERY_API_KEY, API_KEY)
            .build()

        chain.proceed(
            original.newBuilder()
                .url(newURL)
                .build()
        )
    }

    @Provides
    @ApiUnitsInterceptor
    fun providesApiUnitsInterceptor() = Interceptor { chain ->
        val original = chain.request()
        val newURL = original.url.newBuilder()
            .addQueryParameter(QUERY_UNITS_VALUE, API_UNITS)
            .addQueryParameter(QUERY_LOCALE_VALUE, API_LOCALE)
            .build()

        chain.proceed(
            original.newBuilder()
                .url(newURL)
                .build()
        )
    }


    @Provides
    @LoggingInterceptor
    fun provideLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor()
            .setLevel(
                HttpLoggingInterceptor.Level.BODY
            )
    }

    @Provides
    fun providesOkhttp(
        @ApiKeyInterceptor apiKeyInterceptor: Interceptor,
        @ApiUnitsInterceptor apiUnitsInterceptor: Interceptor,
        @LoggingInterceptor loggingInterceptor: Interceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(apiUnitsInterceptor)
            .also {
                if (BuildConfig.DEBUG) {
                    it.addInterceptor(loggingInterceptor)
                }
            }
            .build()

    @Provides
    fun provideGsonConverter(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    fun providesApi(
        gsonConverter: GsonConverterFactory,
        okHttpClient: OkHttpClient,
    ): Api =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverter)
            .build()
            .create(Api::class.java)
}


