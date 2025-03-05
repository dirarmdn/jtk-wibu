package com.example.jtkwibu.ui

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.jtkwibu.viewmodel.BookmarkViewModel
import com.example.jtkwibu.viewmodel.DetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeDetailScreen(
    malId: Int,
    detailViewModel: DetailViewModel = hiltViewModel(),
    bookmarkViewModel: BookmarkViewModel = hiltViewModel(),
    onBackPressed: () -> Unit
) {
    val animeDetail by detailViewModel.animeDetail.collectAsState()

    LaunchedEffect(malId) {
        detailViewModel.getAnimeById(malId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(animeDetail?.title ?: "Anime Detail") },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    animeDetail?.let { anime ->
                        bookmarkViewModel.toggleBookmark(anime.malId, anime.isBookmarked)
                    }
                }
            ) {
                Icon(
                    imageVector = if (animeDetail?.isBookmarked == true) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if (animeDetail?.isBookmarked == true) Color.Red else Color.Gray
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            if (animeDetail == null) {
                CircularProgressIndicator()
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(animeDetail?.imageUrl),
                        contentDescription = animeDetail?.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .background(Color.LightGray),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    AnimatedVisibility(visible = true, enter = fadeIn() + slideInVertically()) {
                        Text(
                            text = animeDetail?.title ?: "Unknown",
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}
