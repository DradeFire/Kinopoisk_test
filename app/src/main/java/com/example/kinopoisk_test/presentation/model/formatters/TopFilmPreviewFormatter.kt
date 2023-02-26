package com.example.kinopoisk_test.presentation.model.formatters

import com.example.kinopoisk_test.domain.model.TopFilmPreview
import com.example.kinopoisk_test.domain.model.TopFilmPreviewFavourite
import com.example.kinopoisk_test.presentation.model.TopFilmPreviewVo

class TopFilmPreviewFormatter {

    @JvmName("format_from_TopFilmPreview")
    fun format(previews: List<TopFilmPreview>): List<TopFilmPreviewVo> {
        return previews.map {
            TopFilmPreviewVo(
                it.filmId,
                it.posterUrlPreview,
                it.nameRu,
                "${it.genre} (${it.year})",
                it.isFavourite
            )
        }
    }

    @JvmName("format_from_TopFilmPreviewFavourite")
    fun format(previews: List<TopFilmPreviewFavourite>): List<TopFilmPreviewVo> {
        return previews.map {
            TopFilmPreviewVo(
                it.filmId,
                it.posterUrlPreview,
                it.nameRu,
                "${it.genre} (${it.year})",
                true
            )
        }
    }

    fun formatToFavourite(previews: List<TopFilmPreview>): List<TopFilmPreviewFavourite> {
        return previews.map {
            TopFilmPreviewFavourite(
                it.filmId,
                it.nameRu,
                it.posterUrlPreview,
                it.year,
                it.genre
            )
        }
    }

}