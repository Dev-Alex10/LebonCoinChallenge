package com.example.leboncoinchallenge.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.leboncoinchallenge.R
import com.example.leboncoinchallenge.data.LebonCoinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModel @Inject constructor(
    @ApplicationContext context: Context, private val repository: LebonCoinRepository
) : ViewModel() {

    val retryTrigger = MutableSharedFlow<Unit>(extraBufferCapacity = 1)

    val uiState: StateFlow<HomeViewState> =
        retryTrigger.onStart { emit(Unit) } // Emit an initial value to start the flow
            .flatMapLatest {
                repository.getAlbums().map { albums -> HomeViewState.Success(albums) }
                    .onStart<HomeViewState> { emit(HomeViewState.Loading) }.catch { error ->
                        emit(
                            HomeViewState.Error(
                                errorMessage = context.getString(
                                    R.string.an_error_occurred, error.message
                                )
                            )
                        )
                    }
            }.stateIn(
                scope = viewModelScope,
                started = WhileSubscribed(5000),
                initialValue = HomeViewState.Loading,
            )

    fun retry() {
        retryTrigger.tryEmit(Unit)
    }
}
