package com.example.kinopoisk_test.presentation.model

data class TopFilmPreviewVo(
    val filmId: Int,
    val posterUrl: String,
    val title: String,
    val descWithYear: String,
    val isFavourite: Boolean
) {
    companion object {
        fun empty() = TopFilmPreviewVo(
            -1,
            "",
            "",
            "",
            false
        )
    }
}
