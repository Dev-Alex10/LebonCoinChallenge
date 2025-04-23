package com.example.leboncoinchallenge.data

import com.example.leboncoinchallenge.data.domain.model.Album
import com.example.leboncoinchallenge.data.local.LebonLocalDataSource
import com.example.leboncoinchallenge.data.remote.LebonRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LebonCoinRepositoryImpl @Inject constructor(
    private val remoteDataSource: LebonRemoteDataSource,
    private val localDataSource: LebonLocalDataSource
) : LebonCoinRepository {

    override suspend fun getAlbums(): Result<Flow<List<Album>>> {
        val albums = localDataSource.getAlbums()
        if (albums.isNotEmpty()) {
            return Result.success(flow {
                emit(albums)
            })
        }

        return remoteDataSource.getAlbums().onSuccess {
            localDataSource.setAlbums(albums)
        }
    }
}