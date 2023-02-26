package com.example.kinopoisk_test.data.dto.tops.mapper

import com.example.kinopoisk_test.data.dto.topinfo.GenreDto
import com.example.kinopoisk_test.data.dto.tops.FilmDto
import com.example.kinopoisk_test.data.dto.tops.TopFilmListDto
import com.example.kinopoisk_test.domain.model.TopFilmList
import com.example.kinopoisk_test.domain.model.TopFilmPreview
import org.junit.Assert.*

import org.junit.Test

class TopFilmListMapperTest {

    private val topFilmListMapper = TopFilmListMapper()

    @Test
    fun map() {
        val res1 = topFilmListMapper.map(value1)
        val res2 = topFilmListMapper.map(value2)

        assertEquals(expected1, res1)
        assertEquals(expected2, res2)
        assertNotEquals(res1, res2)
    }

    private companion object {
        val value1 = TopFilmListDto(
            films = listOf(
                FilmDto(
                    filmId = 1,
                    nameRu = "1",
                    posterUrlPreview = "1",
                    year = "1",
                    genres = listOf(
                        GenreDto("1"),
                        GenreDto("2"),
                        GenreDto("3")
                    )
                ),
                FilmDto(
                    filmId = -1,
                    nameRu = "",
                    posterUrlPreview = "",
                    year = "",
                    genres = listOf(
                        GenreDto(""),
                        GenreDto(""),
                        GenreDto("")
                    )
                )
            ),
            pagesCount = Int.MAX_VALUE
        )
        val value2 = TopFilmListDto(
            films = listOf(),
            pagesCount = Int.MIN_VALUE
        )

        val expected1 = TopFilmList(
            pagesCount = Int.MAX_VALUE,
            films = listOf(
                TopFilmPreview(
                    filmId = 1,
                    nameRu = "1",
                    posterUrlPreview = "1",
                    year = "1",
                    genre = "1",
                    isFavourite = false
                ),
                TopFilmPreview(
                    filmId = -1,
                    nameRu = "",
                    posterUrlPreview = "",
                    year = "",
                    genre = "",
                    isFavourite = false
                )
            )
        )
        val expected2 = TopFilmList(
            pagesCount = Int.MIN_VALUE,
            films = listOf()
        )
    }

}

//fun map(topList: TopFilmListDto): TopFilmList = with(topList) {
//    return TopFilmList(
//        pagesCount,
//        films.map { film ->
//            TopFilmPreview(
//                film.filmId,
//                film.nameRu,
//                film.posterUrlPreview,
//                film.year,
//                film.genres.first().genre
//            )
//        }
//    )
//}