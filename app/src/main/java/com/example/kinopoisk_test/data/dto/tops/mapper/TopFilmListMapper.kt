package com.example.kinopoisk_test.data.dto.tops.mapper

import com.example.kinopoisk_test.data.dto.tops.TopFilmListDto
import com.example.kinopoisk_test.domain.model.TopFilmList
import com.example.kinopoisk_test.domain.model.TopFilmPreview

class TopFilmListMapper {

    fun map(topList: TopFilmListDto): TopFilmList = with(topList) {
        return TopFilmList(
            pagesCount,
            films.map { film ->
                TopFilmPreview(
                    film.filmId,
                    film.nameRu,
                    film.posterUrlPreview,
                    film.year,
                    film.genres.first().genre
                )
            }
        )
    }
}