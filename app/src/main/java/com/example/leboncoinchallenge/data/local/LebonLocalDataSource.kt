package com.example.leboncoinchallenge.data.local

import com.example.leboncoinchallenge.data.domain.model.Album
import com.example.leboncoinchallenge.data.domain.model.toDatabaseEntity
import com.example.leboncoinchallenge.data.local.album.AlbumDao
import com.example.leboncoinchallenge.data.local.album.toDomain
import javax.inject.Inject

class LebonLocalDataSource @Inject constructor(
    private val albumDao: AlbumDao
) {
    suspend fun getAlbums(): List<Album> {
        return albumDao.getAlbums().map { it.toDomain() }
    }

    suspend fun setAlbums(albums: List<Album>) {
        albums.map { albumDao.insertAlbums(it.toDatabaseEntity()) }
    }
}