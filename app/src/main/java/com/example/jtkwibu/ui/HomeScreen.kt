package com.example.jtkwibu.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.jtkwibu.data.AnimeEntity

@Composable
fun HomeScreen(
    onAnimeClick: (Int) -> Unit,
    viewModel: com.example.jtkwibu.viewmodel.HomeViewModel = androidx.hilt.navigation.compose.hiltViewModel()
) {
    val animeList = viewModel.animeList.collectAsLazyPagingItems()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(animeList.itemCount, key = { index -> animeList[index]?.malId ?: index }) { index ->
            animeList[index]?.let { anime ->
                NetflixAnimeItem(anime = anime, onClick = {
                    Log.d("HomeScreen", "Navigating to anime ${anime.malId}")
                    onAnimeClick(anime.malId)
                })
            }
        }
    }
}




@Composable
fun NetflixAnimeItem(anime: AnimeEntity, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable {
                Log.d("NetflixAnimeItem", "Clicked on: ${anime.malId}")
                onClick()
            },
        shape = MaterialTheme.shapes.medium,
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(anime.imageUrl)
                    .crossfade(true)
                    .listener(
                        onStart = { Log.d("Coil", "Mulai load gambar: ${anime.imageUrl}") },
                        onSuccess = { _, _ -> Log.d("Coil", "Gambar berhasil dimuat!") },
                        onError = { _, throwable -> Log.e("Coil", "Gagal memuat gambar") }
                    )
                    .build(),
                contentDescription = anime.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .align(Alignment.BottomCenter)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black)
                        )
                    )
            )
            Text(
                text = anime.title,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(4.dp),
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1
            )
        }
    }
}
