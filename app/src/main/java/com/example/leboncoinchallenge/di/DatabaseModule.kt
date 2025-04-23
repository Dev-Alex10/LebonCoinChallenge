package com.example.leboncoinchallenge.di

import android.content.Context
import androidx.room.Room
import com.example.leboncoinchallenge.data.local.LebonCoinDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    fun provideAlbumDao(database: LebonCoinDatabase) = database.albumDao()

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): LebonCoinDatabase {
        return Room.databaseBuilder(
            appContext,
            LebonCoinDatabase::class.java,
            "lebon_database"
        ).build()
    }
}