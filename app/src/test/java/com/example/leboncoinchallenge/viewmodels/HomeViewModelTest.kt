package com.example.leboncoinchallenge.viewmodels

import com.example.leboncoinchallenge.data.LebonCoinRepository
import com.example.leboncoinchallenge.data.domain.model.Album
import com.example.leboncoinchallenge.ui.home.HomeViewModel
import com.example.leboncoinchallenge.ui.home.HomeViewState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    private val repository: LebonCoinRepository = mockk()
    private lateinit var viewModel: HomeViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `uiState should emit Loading then Success when repository returns albums`() = runTest {
        val fakeAlbums = listOf(
            Album(
                id = 1, title = "Test", thumbnailUrl = "thumbUrl", imageUrl = "imageUrl"
            )
        )
        coEvery { repository.getAlbums() } returns flowOf(fakeAlbums)

        viewModel = HomeViewModel(repository)

        val stateList = mutableListOf<HomeViewState>()
        val job = launch {
            viewModel.uiState.toList(stateList)
        }
        advanceUntilIdle()

        assertEquals(HomeViewState.Loading, stateList[0])
        assertEquals(HomeViewState.Success(fakeAlbums), stateList[1])

        job.cancel()
    }

    @Test
    fun `uiState should emit Loading then Error when repository throws exception`() = runTest {
        val exception = RuntimeException("Network error")

        coEvery { repository.getAlbums() } returns flow { throw exception }
        viewModel = HomeViewModel(repository)

        val stateList = mutableListOf<HomeViewState>()
        val job = launch {
            viewModel.uiState.toList(stateList)
        }

        advanceUntilIdle()

        assertEquals(HomeViewState.Loading, stateList[0])
        assertTrue(stateList[1] is HomeViewState.Error)
        assertEquals(
            "An error occurred ${exception.message}",
            (stateList[1] as HomeViewState.Error).errorMessage
        )

        job.cancel()
    }

    @Test
    fun `retry should re-trigger the uiState flow`() = runTest {
        val albumsFirst =
            listOf(Album(id = 1, title = "First", imageUrl = "url1", thumbnailUrl = "thumb1"))
        val albumsSecond =
            listOf(Album(id = 2, title = "Second", imageUrl = "url2", thumbnailUrl = "thumb2"))

        val albumFlow = MutableStateFlow(albumsFirst)
        coEvery { repository.getAlbums() } returns albumFlow

        viewModel = HomeViewModel(repository)

        val stateList = mutableListOf<HomeViewState>()
        val job = launch {
            viewModel.uiState.toList(stateList)
        }

        advanceUntilIdle()

        // Change the data and trigger retry
        albumFlow.value = albumsSecond
        viewModel.retry()

        advanceUntilIdle()

        val successStates = stateList.filterIsInstance<HomeViewState.Success>()
        assertEquals(2, successStates.size)
        assertEquals(albumsFirst, successStates[0].albums)
        assertEquals(albumsSecond, successStates[1].albums)

        job.cancel()
    }

}
