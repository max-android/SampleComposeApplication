package com.sample.ru.di

import android.content.Context
import com.google.gson.GsonBuilder
import com.sample.ru.BuildConfig
import com.sample.ru.data.network.interceptor.InternetInterceptor
import com.sample.ru.data.service.InternetConnectionService
import com.sample.ru.util.CONNECT_TIMEOUT
import com.sample.ru.util.READ_TIMEOUT
import com.sample.ru.util.WRITE_TIMEOUT
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideInternetConnectionService(
        @ApplicationContext appContext: Context
    ): InternetConnectionService = InternetConnectionService(appContext)

    @Provides
    @Singleton
    fun provideInternetInterceptor(
        netService: InternetConnectionService
    ): InternetInterceptor = InternetInterceptor(netService)

    @Provides
    @Singleton
    fun provideOkHttpClient(
        netInterceptor: InternetInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .apply {
                addInterceptor(netInterceptor)
                addInterceptor(HttpLoggingInterceptor().also {
                    it.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                    else HttpLoggingInterceptor.Level.NONE
                })
                connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
                writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
            }.build()

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create(GsonBuilder().create())

}