package com.example.kinopoisk_test.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.kinopoisk_test.common.consts.Consts.DATABASE.DATABASE_FAVOURITE_LIST_TABLE_NAME

@Entity(tableName = DATABASE_FAVOURITE_LIST_TABLE_NAME)
data class TopFilmPreviewFavourite(
    @PrimaryKey val filmId: Int,
    val nameRu: String,
    val posterUrlPreview: String,
    val year: String,
    val genre: String,
)
