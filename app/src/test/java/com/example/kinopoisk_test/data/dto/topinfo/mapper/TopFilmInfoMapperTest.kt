package com.example.kinopoisk_test.data.dto.topinfo.mapper

import com.example.kinopoisk_test.data.dto.topinfo.CountryDto
import com.example.kinopoisk_test.data.dto.topinfo.GenreDto
import com.example.kinopoisk_test.data.dto.topinfo.TopFilmInfoDto
import com.example.kinopoisk_test.domain.model.TopFilmInfo
import org.junit.Assert.*

import org.junit.Test

class TopFilmInfoMapperTest {

    private val topFilmInfoMapper = TopFilmInfoMapper()

    @Test
    fun map() {
        val res1 = topFilmInfoMapper.map(value1)
        val res2 = topFilmInfoMapper.map(value2)

        assertEquals(expected1, res1)
        assertEquals(expected2, res2)
        assertNotEquals(res1, res2)
    }

    private companion object {

        val value1 = TopFilmInfoDto(
            kinopoiskId = Int.MAX_VALUE,
            countries = listOf(),
            description = "",
            genres = listOf(),
            nameRu = "",
            posterUrl = "",
            year = Int.MAX_VALUE
        )
        val expected1 = TopFilmInfo(
            kinopoiskId = Int.MAX_VALUE,
            country = "Неизвестно",
            description = "",
            genres = listOf(),
            filmTitle = "",
            posterUrl = "",
            year = Int.MAX_VALUE
        )

        val value2 = TopFilmInfoDto(
            kinopoiskId = Int.MIN_VALUE,
            countries = listOf(
                CountryDto("moscow"),
                CountryDto("__________")
            ),
            description = "1111111111111111111111111111",
            genres = listOf(
                GenreDto("123"),
                GenreDto("")
            ),
            nameRu = "111",
            posterUrl = "321",
            year = Int.MIN_VALUE
        )
        val expected2 = TopFilmInfo(
            kinopoiskId = Int.MIN_VALUE,
            country = "moscow",
            description = "1111111111111111111111111111",
            genres = listOf("123", ""),
            filmTitle = "111",
            posterUrl = "321",
            year = Int.MIN_VALUE
        )
    }
}

//fun map(topList: TopFilmInfoDto): TopFilmInfo = with(topList) {
//    return TopFilmInfo(
//        kinopoiskId,
//        countries.firstOrNull()?.country ?: "Неизвестно",
//        description,
//        genres.map {
//            it.genre
//        },
//        nameRu,
//        posterUrl,
//        year
//    )
//}