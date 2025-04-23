package com.example.leboncoinchallenge.data.local.album

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.leboncoinchallenge.data.domain.model.Album

@Entity(tableName = "Album")
data class AlbumEntity(
    @PrimaryKey
    val id: Long,
    val title: String,
    val imageUrl: String,
    val thumbnailUrl: String
)

fun AlbumEntity.toDomain(): Album {
    return Album(
        id = id,
        title = title,
        imageUrl = imageUrl,
        thumbnailUrl = thumbnailUrl
    )
}
