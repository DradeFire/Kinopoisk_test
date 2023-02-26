package com.example.kinopoisk_test.domain.usecases

import com.example.kinopoisk_test.common.status.RequestStatus
import com.example.kinopoisk_test.data.repository.RestRepositoryImpl
import com.example.kinopoisk_test.domain.model.TopFilmInfo
import com.example.kinopoisk_test.domain.model.TopFilmList
import com.example.kinopoisk_test.domain.repository.RestRepository
import io.reactivex.rxjava3.core.Observable

class RestUseCases {

    private val restRepository: RestRepository = RestRepositoryImpl()
    private val databaseUseCases: DatabaseUseCases = DatabaseUseCases()

    fun getTopFilmList(
        page: Int,
        isRefresh: Boolean
    ): Observable<RequestStatus<TopFilmList>> = Observable.create {
        try {
            it.onNext(RequestStatus.Loading())
            val response = restRepository.getTopFilmList(page)

            if (isRefresh)
                databaseUseCases.deleteTopFilmPreviewList()

            response.films.map { film ->
                databaseUseCases.inputTopFilmPreview(film).subscribe()
            }

            it.onNext(RequestStatus.Success(response))
        } catch (e: Throwable) {
            it.onError(e)
        }
    }

    fun getTopFilmInfo(
        id: Int
    ): Observable<RequestStatus<TopFilmInfo>> = Observable.create {
        try {
            it.onNext(RequestStatus.Loading())

            val response = restRepository.getTopFilmInfo(id)
            databaseUseCases.inputTopFilmInfo(response)

            it.onNext(RequestStatus.Success(response))
        } catch (e: Throwable) {
            it.onError(e)



//            databaseUseCases.getTopFilmInfo(id)
//                .subscribe({ el ->
//                    it.onNext(el)
//                }, {
//
//                })
        }
    }

}