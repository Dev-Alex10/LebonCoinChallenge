package com.example.leboncoinchallenge.data

import com.example.leboncoinchallenge.data.domain.model.Album
import kotlinx.coroutines.flow.Flow

interface LebonCoinRepository {
    suspend fun getAlbums(): Result<Flow<List<Album>>>
}