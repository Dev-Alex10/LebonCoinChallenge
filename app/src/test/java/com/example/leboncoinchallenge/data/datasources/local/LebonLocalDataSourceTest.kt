package com.example.leboncoinchallenge.data.datasources.local

import com.example.leboncoinchallenge.data.domain.model.Album
import com.example.leboncoinchallenge.data.domain.model.toDatabaseEntity
import com.example.leboncoinchallenge.data.local.LebonLocalDataSource
import com.example.leboncoinchallenge.data.local.album.AlbumDao
import com.example.leboncoinchallenge.data.local.album.AlbumEntity
import com.example.leboncoinchallenge.data.local.album.toDomain
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class LebonLocalDataSourceTest {

    private val albumDao: AlbumDao = mockk(relaxed = true)
    private lateinit var localDataSource: LebonLocalDataSource

    @Before
    fun setUp() {
        localDataSource = LebonLocalDataSource(albumDao)
    }

    @Test
    fun `getAlbums should return mapped domain models`() = runTest {
        val albumEntities = listOf(
            AlbumEntity(
                id = 1,
                title = "Test",
                imageUrl = "url",
                thumbnailUrl = "thumb"
            )
        )
        every { albumDao.getAlbums() } returns flowOf(albumEntities)

        val result = localDataSource.getAlbums().first()

        assertEquals(1, result.size)
        assertEquals(albumEntities[0].toDomain(), result[0])
    }

    @Test
    fun `setAlbums should map to entities and insert them`() = runTest {
        val albums = listOf(
            Album(
                id = 1,
                title = "Test",
                imageUrl = "url",
                thumbnailUrl = "thumb"
            )
        )

        localDataSource.setAlbums(albums)

        coVerify {
            albumDao.insertAlbums(match {
                it.size == 1 && it[0] == albums[0].toDatabaseEntity()
            })
        }
    }
}
