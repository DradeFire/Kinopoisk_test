package com.example.kinopoisk_test.domain.repository

import com.example.kinopoisk_test.domain.model.TopFilmInfo
import com.example.kinopoisk_test.domain.model.TopFilmList
import retrofit2.Call

interface RestRepository {

    fun getTopFilmList(page: Int): TopFilmList

    fun getTopFilmInfo(id: Int): TopFilmInfo
}