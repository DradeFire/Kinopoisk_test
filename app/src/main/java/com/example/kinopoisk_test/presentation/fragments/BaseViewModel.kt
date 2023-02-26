package com.example.kinopoisk_test.presentation.fragments

import androidx.lifecycle.ViewModel
import com.example.kinopoisk_test.domain.usecases.DatabaseUseCases
import com.example.kinopoisk_test.domain.usecases.RestUseCases
import com.example.kinopoisk_test.presentation.model.formatters.TopFilmInfoFormatter
import com.example.kinopoisk_test.presentation.model.formatters.TopFilmPreviewFormatter
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject

abstract class BaseViewModel : ViewModel() {

    protected val topFilmInfoFormatter = TopFilmInfoFormatter()
    protected val topFilmPreviewFormatter = TopFilmPreviewFormatter()

    protected val restUseCases = RestUseCases()
    protected val databaseUseCases = DatabaseUseCases()

    protected val _isError: PublishSubject<Boolean> = PublishSubject.create()
    val isError = _isError

    protected val _isLoading: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)
    val isLoading = _isLoading

}