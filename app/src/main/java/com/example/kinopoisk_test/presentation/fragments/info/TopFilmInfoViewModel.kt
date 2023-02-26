package com.example.kinopoisk_test.presentation.fragments.info

import com.example.kinopoisk_test.common.logger.MyLogger
import com.example.kinopoisk_test.common.status.RequestStatus
import com.example.kinopoisk_test.presentation.fragments.BaseViewModel
import com.example.kinopoisk_test.presentation.model.TopFilmInfoVo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject

class TopFilmInfoViewModel : BaseViewModel() {

    private val _filmInfo: BehaviorSubject<TopFilmInfoVo> = BehaviorSubject.create()
    val filmInfo = _filmInfo

    private var getTopFilmInfoDisposable: Disposable? = null

    private fun getTopFilmInfo(id: Int) {
        getTopFilmInfoDisposable = restUseCases.getTopFilmInfo(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ requestStatus ->
                when(requestStatus) {
                    is RequestStatus.Loading -> {
                        MyLogger.log("fun getTopFilmInfo(id: Int) -> loading, failure")
                        _isLoading.onNext(true)
                    }
                    is RequestStatus.Success -> {
                        MyLogger.log("fun getTopFilmInfo(id: Int) -> success")
                        _isLoading.onNext(false)
                        requestStatus.successData.also { data ->
                            _filmInfo.onNext(topFilmInfoFormatter.format(data))
                        }
                        getTopFilmInfoDisposable?.dispose()
                    }
                }
            }, {
                MyLogger.log("fun getTopFilmInfo(id: Int)", it)
                getTopFilmInfoDisposable?.dispose()
                _isError.onNext(true)
            })
    }

    fun onErrorButtonClicked(kinoId: Int) {
        getTopFilmInfo(kinoId)
    }

    fun firstInitUI(kinoId: Int) {
        getTopFilmInfo(kinoId)
    }

    override fun onCleared() {
        getTopFilmInfoDisposable = null
        super.onCleared()
    }

}