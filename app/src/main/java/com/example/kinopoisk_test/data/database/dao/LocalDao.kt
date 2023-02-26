package com.example.kinopoisk_test.data.database.dao

import androidx.room.*
import com.example.kinopoisk_test.common.consts.Consts.DATABASE.DATABASE_FAVOURITE_LIST_TABLE_NAME
import com.example.kinopoisk_test.common.consts.Consts.DATABASE.DATABASE_FILM_INFO_TABLE_NAME
import com.example.kinopoisk_test.common.consts.Consts.DATABASE.DATABASE_PREVIEW_LIST_TABLE_NAME
import com.example.kinopoisk_test.domain.model.TopFilmInfo
import com.example.kinopoisk_test.domain.model.TopFilmPreview
import com.example.kinopoisk_test.domain.model.TopFilmPreviewFavourite
import io.reactivex.rxjava3.core.Completable

@Dao
interface LocalDao {

    @Query("SELECT * FROM $DATABASE_FILM_INFO_TABLE_NAME WHERE kinopoiskId = :kinopoiskId")
    fun getTopFilmInfo(kinopoiskId: Int): TopFilmInfo

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inputTopFilmInfo(topFilmInfo: TopFilmInfo) : Completable


    @Query("SELECT * FROM $DATABASE_PREVIEW_LIST_TABLE_NAME")
    fun getTopFilmPreviewList(): List<TopFilmPreview>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inputTopFilmPreview(topFilmPreview: TopFilmPreview) : Completable

    @Query("DELETE FROM $DATABASE_PREVIEW_LIST_TABLE_NAME")
    fun deleteTopFilmPreviewList() : Completable


    @Query("SELECT * FROM $DATABASE_FAVOURITE_LIST_TABLE_NAME")
    fun getFavouriteList(): List<TopFilmPreviewFavourite>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inputToFavouriteList(topFilmPreviewFavourite: TopFilmPreviewFavourite) : Completable

}