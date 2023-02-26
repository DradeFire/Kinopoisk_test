package com.example.kinopoisk_test.domain.model

data class TopFilmList(
    val pagesCount: Int,
    val films: List<TopFilmPreview>
) {
    companion object {
        fun empty() = TopFilmList(
            1,
            emptyList()
        )
    }
}
