package com.example.leboncoinchallenge.data.remote.album.model

import com.example.leboncoinchallenge.data.domain.model.Album
import com.google.gson.annotations.SerializedName

data class AlbumAPI(
    val id: Long,
    val albumId: Long,
    val title: String,
    @SerializedName("url")
    val imageUrl: String,
    @SerializedName("thumbnailUrl")
    val thumbnailUrl: String
)

fun AlbumAPI.toDomain(): Album {
    return Album(
        id = id,
        title = title,
        imageUrl = imageUrl,
        thumbnailUrl = thumbnailUrl
    )
}