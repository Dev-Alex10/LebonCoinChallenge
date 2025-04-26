package com.example.leboncoinchallenge.ui.home

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.leboncoinchallenge.R
import com.example.leboncoinchallenge.data.domain.model.Album

@Composable
fun HomeView(
    viewModel: HomeViewModel = hiltViewModel(),
    modifier: Modifier
) {
    val state = viewModel.uiState.collectAsState().value
    when (state) {
        is HomeViewState.Error ->
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = state.errorMessage,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 8.dp),
                    )
                    Button(onClick = {
                        viewModel.retry()
                    }) {
                        Text(stringResource(R.string.retry))
                    }
                }
            }

        is HomeViewState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(modifier = Modifier.size(48.dp))
            }
        }

        is HomeViewState.Success -> {
            AlbumGrid(state.albums, modifier)
        }
    }
}

@Composable
fun AlbumGrid(albums: List<Album>, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 160.dp),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        items(albums) { album ->
            GridItem(album, context)
        }
    }
}

@Composable
fun GridItem(album: Album, context: Context) {
    var selectedImage by remember { mutableStateOf<Album?>(null) }

    Column(
        modifier = Modifier.height(260.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = album.title,
            fontWeight = FontWeight.Bold,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(bottom = 4.dp, start = 8.dp)
                .height(60.dp),
        )
        AsyncImage(
            model = ImageRequest.Builder(context = context)
                .data(album.thumbnailUrl)
                .crossfade(true)
                .build(),
            contentDescription = album.title,
            modifier = Modifier
                .size(150.dp)
                .clip(RoundedCornerShape(8.dp))
                .clickable {
                    selectedImage = album
                },
            error = painterResource(R.drawable.error),
            onLoading = {
                Icons.Outlined.Refresh
            },
        )
    }
    selectedImage?.let { image ->
        Dialog(
            onDismissRequest = { selectedImage = null },
            content = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .background(
                            MaterialTheme.colorScheme.background
                        )
                        .padding(8.dp)
                        .clickable(onClick = { selectedImage = null })
                ) {
                    Text(
                        text = image.title,
                        fontWeight = FontWeight.Bold
                    )
                    AsyncImage(
                        model = image.imageUrl,
                        contentDescription = stringResource(R.string.real_image),
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 600.dp)
                    )
                }
            })
    }
}