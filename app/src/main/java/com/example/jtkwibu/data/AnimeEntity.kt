package com.example.jtkwibu.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "anime_table")
data class AnimeEntity(
    @PrimaryKey val malId: Int,
    val title: String,
    val imageUrl: String?,
    val isBookmarked: Boolean = false
)

fun AnimeNetworkModel.toEntity(): AnimeEntity {
    return AnimeEntity(
        malId = this.malId,
        title = this.title,
        imageUrl = this.images.jpg.imageUrl,
    )
}
