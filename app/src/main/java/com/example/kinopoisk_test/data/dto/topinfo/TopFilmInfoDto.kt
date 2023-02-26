package com.example.kinopoisk_test.data.dto.topinfo

data class TopFilmInfoDto(
    val kinopoiskId: Int,
    val countries: List<CountryDto>,
    val description: String,
    val genres: List<GenreDto>,
    val nameRu: String,
    val posterUrl: String,
    val year: Int
)