package com.example.kinopoisk_test.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.kinopoisk_test.common.consts.Consts.DATABASE.DATABASE_PREVIEW_LIST_TABLE_NAME

@Entity(tableName = DATABASE_PREVIEW_LIST_TABLE_NAME)
data class TopFilmPreview(
    @PrimaryKey val filmId: Int,
    val nameRu: String,
    val posterUrlPreview: String,
    val year: String,
    val genre: String,
    val isFavourite: Boolean = false
)
