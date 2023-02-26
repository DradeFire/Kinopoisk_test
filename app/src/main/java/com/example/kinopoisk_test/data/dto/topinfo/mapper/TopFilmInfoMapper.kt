package com.example.kinopoisk_test.data.dto.topinfo.mapper

import com.example.kinopoisk_test.data.dto.topinfo.TopFilmInfoDto
import com.example.kinopoisk_test.domain.model.TopFilmInfo


class TopFilmInfoMapper {

    fun map(topList: TopFilmInfoDto): TopFilmInfo = with(topList) {
        return TopFilmInfo(
            kinopoiskId,
            countries.firstOrNull()?.country ?: "Неизвестно",
            description,
            genres.map {
                it.genre
            },
            nameRu,
            posterUrl,
            year
        )
    }

}