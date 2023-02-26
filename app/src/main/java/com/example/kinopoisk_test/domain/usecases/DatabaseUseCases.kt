package com.example.kinopoisk_test.domain.usecases

import com.example.kinopoisk_test.common.status.RequestStatus
import com.example.kinopoisk_test.data.repository.DatabaseRepositoryImpl
import com.example.kinopoisk_test.domain.model.TopFilmInfo
import com.example.kinopoisk_test.domain.model.TopFilmPreview
import com.example.kinopoisk_test.domain.model.TopFilmPreviewFavourite
import com.example.kinopoisk_test.domain.repository.DatabaseRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

class DatabaseUseCases {

    private val databaseRepository: DatabaseRepository = DatabaseRepositoryImpl()

    fun getTopFilmInfo(
        kinopoiskId: Int
    ): Observable<RequestStatus<TopFilmInfo>> = Observable.create {
        try {
            it.onNext(RequestStatus.Loading())
            val response = databaseRepository.getTopFilmInfo(kinopoiskId)
            it.onNext(RequestStatus.Success(response))
        } catch (e: Throwable) {
            it.onError(e)
        }
    }

    fun inputTopFilmInfo(
        topFilmInfo: TopFilmInfo
    ): Completable = Completable.create {
        try {
            databaseRepository.inputTopFilmInfo(topFilmInfo).subscribe {
                it.onComplete()
            }
        } catch (e: Throwable) {
            it.onError(e)
        }
    }

    fun getTopFilmPreviewList(): Observable<RequestStatus<List<TopFilmPreview>>> = Observable.create {
        try {
            it.onNext(RequestStatus.Loading())
            val response = databaseRepository.getTopFilmPreviewList()
            it.onNext(RequestStatus.Success(response))
        } catch (e: Throwable) {
            it.onError(e)
        }
    }

    fun inputTopFilmPreview(
        topFilmPreview: TopFilmPreview
    ): Completable = Completable.create {
        try {
            databaseRepository.inputTopFilmTopFilmPreview(topFilmPreview).subscribe {
                it.onComplete()
            }
        } catch (e: Throwable) {
            it.onError(e)
        }
    }

    fun deleteTopFilmPreviewList(): Completable = Completable.create {
        try {
            databaseRepository.deleteTopFilmPreview().subscribe {
                it.onComplete()
            }
        } catch (e: Throwable) {
            it.onError(e)
        }
    }

    fun getFavouriteList(): Observable<RequestStatus<List<TopFilmPreviewFavourite>>> = Observable.create {
        try {
            it.onNext(RequestStatus.Loading())
            val response = databaseRepository.getFavouriteList()
            it.onNext(RequestStatus.Success(response))
        } catch (e: Throwable) {
            it.onError(e)
        }
    }

    fun inputToFavouriteList(
        topFilmPreviewFavourite: TopFilmPreviewFavourite
    ): Completable = Completable.create {
        try {
            databaseRepository.inputToFavouriteList(topFilmPreviewFavourite).subscribe {
                it.onComplete()
            }
        } catch (e: Throwable) {
            it.onError(e)
        }
    }

}