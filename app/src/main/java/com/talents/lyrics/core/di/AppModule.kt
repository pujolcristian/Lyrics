package com.talents.lyrics.core.di

import android.app.Application
import com.talents.lyrics.NetworkHelper
import com.talents.lyrics.feature_lyrics.data.remote.ApiServiceGenerator
import com.talents.lyrics.feature_lyrics.data.remote.ApiSongs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApiService(
        loggingInterceptor: HttpLoggingInterceptor
    ): ApiSongs {
        val interceptors: Array<Interceptor> = arrayOf(loggingInterceptor)
        return ApiServiceGenerator.generate(
            ApiSongs.BASE_URL,
            ApiSongs::class.java,
            interceptors
        )
    }

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    @Singleton
    fun providesNetworkHelper(
        application: Application
    ): NetworkHelper {
        return NetworkHelper(application)
    }

}