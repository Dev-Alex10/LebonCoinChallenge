package com.example.leboncoinchallenge.data.datasources.remote

import com.example.leboncoinchallenge.data.remote.LebonCoinAPI
import com.example.leboncoinchallenge.data.remote.LebonRemoteDataSource
import com.example.leboncoinchallenge.data.remote.album.model.AlbumAPI
import com.example.leboncoinchallenge.data.remote.album.model.toDomain
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class LebonRemoteDataSourceTest {

    private val lebonCoinAPI: LebonCoinAPI = mockk()
    private lateinit var remoteDataSource: LebonRemoteDataSource

    @Before
    fun setUp() {
        remoteDataSource = LebonRemoteDataSource(lebonCoinAPI)
    }

    @Test
    fun `getAlbums should return mapped domain albums when API succeeds`() = runTest {
        val apiAlbums = listOf(
            AlbumAPI(
                id = 1,
                title = "API",
                imageUrl = "url",
                thumbnailUrl = "thumb",
                albumId = 1
            )
        )
        coEvery { lebonCoinAPI.getAlbums() } returns apiAlbums

        val result = remoteDataSource.getAlbums()

        assertTrue(result.isSuccess)
        assertEquals(apiAlbums.map { it.toDomain() }, result.getOrNull())
    }

    @Test
    fun `getAlbums should return failure when API throws`() = runTest {
        val exception = RuntimeException("API error")
        coEvery { lebonCoinAPI.getAlbums() } throws exception

        val result = remoteDataSource.getAlbums()

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}
