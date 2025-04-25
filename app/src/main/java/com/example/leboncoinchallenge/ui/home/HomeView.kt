package com.example.leboncoinchallenge.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.leboncoinchallenge.R

@Composable
fun HomeView(
    viewModel: HomeViewModel = hiltViewModel(),
    modifier: Modifier
) {
    val context = LocalContext.current
    val state = viewModel.uiState.collectAsState().value
    when (state) {
        is HomeViewState.Error ->
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column {
                    Text(text = state.errorMessage)
                    Button(onClick = {
                        viewModel.retry()
                    }) {
                        Text("Retry")
                    }
                }
            }

        is HomeViewState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(modifier = Modifier.size(48.dp))
            }
        }

        is HomeViewState.Success -> {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 160.dp),
                modifier = modifier
                    .fillMaxSize(),
            ) {
                items(state.albums) { album ->
                    Column(
                        modifier = Modifier.height(260.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .height(120.dp)
                                .padding(all = 8.dp),
                            contentAlignment = Alignment.TopCenter,
                        ) {
                            Text(
                                text = album.title,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        AsyncImage(
                            model = ImageRequest.Builder(context = context)
                                .data(album.thumbnailUrl)
                                .crossfade(true)
                                .build(),
                            contentDescription = album.title,
                            modifier = Modifier
                                .size(120.dp)
                                .padding(top = 8.dp),
                            error = painterResource(R.drawable.error),
                            onLoading = {
                                Icons.Outlined.Refresh
                            },
                        )
                    }
                }
            }
        }
    }
}