package com.example.kinopoisk_test.domain.repository

import com.example.kinopoisk_test.domain.model.TopFilmInfo
import com.example.kinopoisk_test.domain.model.TopFilmPreview
import com.example.kinopoisk_test.domain.model.TopFilmPreviewFavourite
import io.reactivex.rxjava3.core.Completable

interface DatabaseRepository {

    fun getTopFilmInfo(kinopoiskId: Int): TopFilmInfo

    fun inputTopFilmInfo(topFilmInfo: TopFilmInfo): Completable


    fun getTopFilmPreviewList(): List<TopFilmPreview>

    fun inputTopFilmTopFilmPreview(topFilmPreview: TopFilmPreview): Completable

    fun deleteTopFilmPreview(): Completable


    fun getFavouriteList(): List<TopFilmPreviewFavourite>

    fun inputToFavouriteList(topFilmPreviewFavourite: TopFilmPreviewFavourite): Completable

}