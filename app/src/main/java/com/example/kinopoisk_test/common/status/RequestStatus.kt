package com.example.kinopoisk_test.common.status

sealed class RequestStatus <T> {

    class Loading<T>: RequestStatus<T>()

    class Success<T>(val successData: T) : RequestStatus<T>()
}
