package com.example.kinopoisk_test.data.dto.tops

import com.example.kinopoisk_test.data.dto.topinfo.GenreDto

data class FilmDto(
    val filmId: Int,
    val nameRu: String,
    val posterUrlPreview: String,
    val year: String,
    val genres: List<GenreDto>
)