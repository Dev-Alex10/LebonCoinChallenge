package com.example.leboncoinchallenge.ui.home

import com.example.leboncoinchallenge.data.domain.model.Album

sealed interface HomeViewState {
    data object Loading : HomeViewState

    data class Success(
        val albums: List<Album>
    ) : HomeViewState

    data class Error(val errorMessage: String) : HomeViewState
}