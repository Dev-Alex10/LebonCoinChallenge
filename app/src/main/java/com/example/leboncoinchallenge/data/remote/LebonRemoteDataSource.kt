package com.example.leboncoinchallenge.data.remote

import com.example.leboncoinchallenge.data.remote.album.model.toDomain
import javax.inject.Inject

class LebonRemoteDataSource @Inject constructor(
    private val lebonCoinAPI: LebonCoinAPI
) {

    suspend fun getAlbums() = runCatching {
        lebonCoinAPI.getAlbums().map { it.toDomain() }
    }
}