package com.example.leboncoinchallenge.data.remote

import com.example.leboncoinchallenge.data.remote.album.model.AlbumAPI
import retrofit2.http.GET

interface LebonCoinAPI {

    @GET("technical-test.json")
    suspend fun getAlbums(): List<AlbumAPI>
}