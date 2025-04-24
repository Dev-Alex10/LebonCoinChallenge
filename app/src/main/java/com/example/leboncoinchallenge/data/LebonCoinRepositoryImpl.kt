package com.example.leboncoinchallenge.data

import com.example.leboncoinchallenge.data.domain.model.Album
import com.example.leboncoinchallenge.data.local.LebonLocalDataSource
import com.example.leboncoinchallenge.data.remote.LebonRemoteDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OptIn(ExperimentalCoroutinesApi::class)
class LebonCoinRepositoryImpl @Inject constructor(
    private val remoteDataSource: LebonRemoteDataSource,
    private val localDataSource: LebonLocalDataSource
) : LebonCoinRepository {
    private val mutex = Mutex()

    override fun getAlbums(): Flow<List<Album>> {
        return localDataSource.getAlbums().onStart {
            fetchAlbumsIfNeeded()
        }
    }

    private suspend fun fetchAlbumsIfNeeded() {
        return mutex.withLock {
            val localAlbums = localDataSource.getAlbums().first()
            if (localAlbums.isNotEmpty()) {
                return@withLock
            }
            val remoteAlbums = remoteDataSource.getAlbums().getOrThrow()
            localDataSource.setAlbums(remoteAlbums)
        }
    }
}