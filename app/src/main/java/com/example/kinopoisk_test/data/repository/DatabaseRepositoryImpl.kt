package com.example.kinopoisk_test.data.repository

import com.example.kinopoisk_test.data.database.db.LocalDatabase
import com.example.kinopoisk_test.domain.model.TopFilmInfo
import com.example.kinopoisk_test.domain.model.TopFilmPreview
import com.example.kinopoisk_test.domain.model.TopFilmPreviewFavourite
import com.example.kinopoisk_test.domain.repository.DatabaseRepository
import io.reactivex.rxjava3.core.Completable

class DatabaseRepositoryImpl : DatabaseRepository {

    override fun getTopFilmInfo(kinopoiskId: Int): TopFilmInfo =
        LocalDatabase.database.getDao().getTopFilmInfo(kinopoiskId)

    override fun inputTopFilmInfo(topFilmInfo: TopFilmInfo) : Completable =
        LocalDatabase.database.getDao().inputTopFilmInfo(topFilmInfo)


    override fun getTopFilmPreviewList(): List<TopFilmPreview> =
        LocalDatabase.database.getDao().getTopFilmPreviewList()


    override fun inputTopFilmTopFilmPreview(topFilmPreview: TopFilmPreview): Completable =
        LocalDatabase.database.getDao().inputTopFilmPreview(topFilmPreview)


    override fun deleteTopFilmPreview(): Completable =
        LocalDatabase.database.getDao().deleteTopFilmPreviewList()


    override fun getFavouriteList(): List<TopFilmPreviewFavourite> =
        LocalDatabase.database.getDao().getFavouriteList()

    override fun inputToFavouriteList(topFilmPreviewFavourite: TopFilmPreviewFavourite): Completable =
        LocalDatabase.database.getDao().inputToFavouriteList(topFilmPreviewFavourite)


}