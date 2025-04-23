package com.example.leboncoinchallenge.data.remote

import com.example.leboncoinchallenge.data.remote.album.model.AlbumAPI
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface LebonCoinAPI {

    @GET("/")
    suspend fun getAlbums(): Flow<List<AlbumAPI>>
}