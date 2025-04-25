package com.example.leboncoinchallenge.di

import com.example.leboncoinchallenge.data.LebonCoinRepository
import com.example.leboncoinchallenge.data.LebonCoinRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Singleton
    @Binds
    fun provideAlbumRepository(
        impl: LebonCoinRepositoryImpl
    ): LebonCoinRepository
}