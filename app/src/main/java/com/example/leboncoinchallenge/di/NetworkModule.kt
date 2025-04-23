package com.example.leboncoinchallenge.di

import com.example.leboncoinchallenge.data.remote.LebonCoinAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "https://static.leboncoin.fr/img/shared/technical-test.json"

    @Provides
    fun provideLebonCoinService(): LebonCoinAPI {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()
            .create(LebonCoinAPI::class.java)
    }
}