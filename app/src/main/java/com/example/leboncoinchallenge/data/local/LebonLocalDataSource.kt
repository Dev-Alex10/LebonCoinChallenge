package com.example.leboncoinchallenge.data.local

import com.example.leboncoinchallenge.data.domain.model.Album
import com.example.leboncoinchallenge.data.domain.model.toDatabaseEntity
import com.example.leboncoinchallenge.data.local.album.AlbumDao
import com.example.leboncoinchallenge.data.local.album.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LebonLocalDataSource @Inject constructor(
    private val albumDao: AlbumDao
) {
    fun getAlbums(): Flow<List<Album>> {
        return albumDao.getAlbums().map { list -> list.map { it.toDomain() } }
    }

    suspend fun setAlbums(albums: List<Album>) {
        albumDao.insertAlbums(albums.map { it.toDatabaseEntity() })
    }
}