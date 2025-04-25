package com.example.leboncoinchallenge.data

import com.example.leboncoinchallenge.data.domain.model.Album
import com.example.leboncoinchallenge.data.local.LebonLocalDataSource
import com.example.leboncoinchallenge.data.remote.LebonRemoteDataSource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class LebonCoinRepositoryImplTest {

    private val remoteDataSource: LebonRemoteDataSource = mockk(relaxed = true)
    private val localDataSource: LebonLocalDataSource = mockk(relaxed = true)

    private lateinit var repository: LebonCoinRepositoryImpl

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = LebonCoinRepositoryImpl(remoteDataSource, localDataSource)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getAlbums should return local albums without fetching remote when local is not empty`() =
        runTest {
            val localAlbums = listOf(
                Album(
                    id = 1,
                    title = "Local",
                    imageUrl = "url",
                    thumbnailUrl = "thumb"
                )
            )
            coEvery { localDataSource.getAlbums() } returns flowOf(localAlbums)

            val result = repository.getAlbums().first()

            assertEquals(localAlbums, result)
            coVerify(exactly = 0) { remoteDataSource.getAlbums() }
            coVerify(exactly = 0) { localDataSource.setAlbums(any()) }
        }

    @Test
    fun `getAlbums should fetch from remote and save locally when local is empty`() = runTest {
        val remoteAlbums = listOf(Album(2, "Remote", "url2", "thumb2"))

        val localData = MutableStateFlow<List<Album>>(emptyList())
        coEvery { localDataSource.getAlbums() } returns localData
        coEvery { remoteDataSource.getAlbums() } returns Result.success(remoteAlbums)
        coEvery { localDataSource.setAlbums(remoteAlbums) } answers {
            // Simulate caching albums by updating the flow
            localData.value = remoteAlbums
        }

        val result = repository.getAlbums().first()

        assertEquals(remoteAlbums, result)
        coVerify(exactly = 1) { remoteDataSource.getAlbums() }
        coVerify(exactly = 1) { localDataSource.setAlbums(remoteAlbums) }
    }

}