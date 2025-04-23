package com.example.leboncoinchallenge.di

import com.example.leboncoinchallenge.data.LebonCoinRepositoryImpl
import com.example.leboncoinchallenge.data.local.LebonLocalDataSource
import com.example.leboncoinchallenge.data.remote.LebonRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideAlbumRepository(
        remoteDataSource: LebonRemoteDataSource,
        localDataSource: LebonLocalDataSource,
    ): LebonCoinRepositoryImpl {
        return LebonCoinRepositoryImpl(remoteDataSource, localDataSource)
    }
}