package com.example.leboncoinchallenge.data.domain.model

import com.example.leboncoinchallenge.data.local.album.AlbumEntity

data class Album(
    val id: Long,
    val title: String,
    val imageUrl: String,
    val thumbnailUrl: String
)

fun Album.toDatabaseEntity(): AlbumEntity{
    return AlbumEntity(
        id,
        title,
        imageUrl,
        thumbnailUrl
    )
}