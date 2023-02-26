package com.example.kinopoisk_test.presentation.fragments.main

import androidx.lifecycle.LifecycleCoroutineScope
import com.example.kinopoisk_test.common.logger.MyLogger
import com.example.kinopoisk_test.common.status.RequestStatus
import com.example.kinopoisk_test.domain.model.TopFilmList
import com.example.kinopoisk_test.domain.model.TopFilmPreview
import com.example.kinopoisk_test.domain.model.TopFilmPreviewFavourite
import com.example.kinopoisk_test.presentation.fragments.BaseViewModel
import com.example.kinopoisk_test.presentation.model.TopFilmInfoVo
import com.example.kinopoisk_test.presentation.model.TopFilmPreviewVo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import kotlinx.coroutines.delay

class TopFilmPreviewListViewModel : BaseViewModel() {

    private var page: Int = 0
    private var pageCount: Int = 1
    val isLastPage: Boolean
        get() = page >= pageCount

    private val _filmList: BehaviorSubject<List<TopFilmPreviewVo>> = BehaviorSubject.create()
    val filmList = _filmList


    private val filmHashMap = HashMap<Int, TopFilmPreviewFavourite>()

    private var getTopFilmListDisposable: Disposable? = null
    private var getTopFilmListFromDbDisposable: Disposable? = null
    private var getFavouriteListDisposable: Disposable? = null
    private var addToFavouriteListDisposable: Disposable? = null
    private var getTopFilmInfoDisposable: Disposable? = null


    private fun getTopFilmList(isRefresh: Boolean = false) {
        if (isRefresh) {
            MyLogger.log("getTopFilmList -> refresh")
            page = 0
            _filmList.onNext(emptyList())
            filmHashMap.clear()
        }

        if (page < pageCount || isRefresh) {
            getTopFilmListDisposable = restUseCases.getTopFilmList(++page, isRefresh)
                .zipWith(databaseUseCases.getFavouriteList()) { listFromNet, listFromFavourite ->
                    handleFavouriteCombine(listFromNet, listFromFavourite)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ requestStatus ->
                    when (requestStatus) {
                        is RequestStatus.Loading -> {
                            MyLogger.log("private fun getTopFilmList(isRefresh: Boolean = false) -> loading, failure")
                            _isLoading.onNext(true)
                        }
                        is RequestStatus.Success -> {
                            MyLogger.log("private fun getTopFilmList(isRefresh: Boolean = false) -> success")
                            _isLoading.onNext(false)
                            requestStatus.successData.also { data ->
                                pageCount = data.pagesCount

                                val value = arrayListOf<TopFilmPreviewVo>().apply {
                                    _filmList.value?.let { addAll(it) }
                                    addAll(topFilmPreviewFormatter.format(data.films))
                                }
                                _filmList.onNext(value)

                                topFilmPreviewFormatter.formatToFavourite(data.films).map {
                                    filmHashMap[it.filmId] = it
                                }
                            }

                            getTopFilmListDisposable?.dispose()
                        }
                    }
                }, {
                    MyLogger.log("getTopFilmList(isRefresh: Boolean = false) error", it)
                    filmHashMap.clear()
                    getTopFilmListDisposable?.dispose()

                    getTopFilmListFromDb()
                })
        }
//        else {
//            getTopFilmListFromDb()
//        }
    }

    private fun handleFavouriteCombine(
        listFromNet: RequestStatus<TopFilmList>,
        listFromFavourite: RequestStatus<List<TopFilmPreviewFavourite>>
    ): RequestStatus<TopFilmList> {
        return when (listFromNet) {
            is RequestStatus.Loading -> {
                MyLogger.log("private fun handleFavouriteCombine -> loading, failure")
                listFromNet
            }
            is RequestStatus.Success -> {
                when (listFromFavourite) {
                    is RequestStatus.Loading -> {
                        MyLogger.log("private fun handleFavouriteCombine -> loading, failure")
                        listFromNet
                    }
                    is RequestStatus.Success -> {
                        MyLogger.log("private fun handleFavouriteCombine -> success")
                        val favouritesMap = listFromFavourite.successData.associateBy {
                            it.filmId
                        }
                        RequestStatus.Success(TopFilmList(
                            listFromNet.successData.pagesCount,
                            listFromNet.successData.films.map { preview ->
                                TopFilmPreview(
                                    preview.filmId,
                                    preview.nameRu,
                                    preview.posterUrlPreview,
                                    preview.year,
                                    preview.genre,
                                    favouritesMap.containsKey(preview.filmId)
                                )
                            }
                        ))
                    }
                }
            }
        }
    }

    private fun getTopFilmListFromDb() {
        getTopFilmListFromDbDisposable = databaseUseCases.getTopFilmPreviewList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                when (it) {
                    is RequestStatus.Loading -> {
                        _isLoading.onNext(true)
                    }
                    is RequestStatus.Success -> {
                        _isLoading.onNext(false)
                        page = pageCount
                        _filmList.onNext(topFilmPreviewFormatter.format(it.successData))
                        getTopFilmListFromDbDisposable?.dispose()
                    }
                }
            }, {
                MyLogger.log("private fun getTopFilmListFromDb()", it)
                getTopFilmListFromDbDisposable?.dispose()
                _isError.onNext(true)
            })
    }

    private fun getFavouriteList() {
        getFavouriteListDisposable = databaseUseCases.getFavouriteList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ requestStatus ->
                when (requestStatus) {
                    is RequestStatus.Loading -> {
                        MyLogger.log("private fun getFavouriteList() -> loading, failure")
                        _isLoading.onNext(true)
                    }
                    is RequestStatus.Success -> {
                        MyLogger.log("private fun getFavouriteList() -> success")
                        requestStatus.successData.also { data ->
                            _isLoading.onNext(false)
                            page = pageCount
                            _filmList.onNext(topFilmPreviewFormatter.format(data))
                            getFavouriteListDisposable?.dispose()
                        }
                    }
                }
            }, {
                MyLogger.log("getTopFilmList() error", it)
                getFavouriteListDisposable?.dispose()
                _isError.onNext(true)
            })
    }

    fun addToFavouriteList(filmId: Int) {
        _isLoading.onNext(true)

        filmHashMap[filmId]?.let { film ->
            addToFavouriteListDisposable = databaseUseCases.inputToFavouriteList(film)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _isLoading.onNext(false)
                    addToFavouriteListDisposable?.dispose()
                }, {
                    MyLogger.log("addToFavouriteList() error", it)
                    addToFavouriteListDisposable?.dispose()
                    _isError.onNext(true)
                })
        } ?: run {
            MyLogger.log("filmHashMap empty")
            addToFavouriteListDisposable?.dispose()
            _isError.onNext(true)
        }
    }

    private val _filmInfo: BehaviorSubject<TopFilmInfoVo> = BehaviorSubject.create()
    val filmInfo = _filmInfo

    fun getTopFilmInfo(id: Int) {
        getTopFilmInfoDisposable = restUseCases.getTopFilmInfo(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ requestStatus ->
                when(requestStatus) {
                    is RequestStatus.Loading -> {
                        _isLoading.onNext(true)
                    }
                    is RequestStatus.Success -> {
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

    fun onInitRefresh() {
        getTopFilmList(true)
    }

    fun onUiAfterStartLoading(
        startLoadingIsVisible: Boolean,
        lifecycleScope: LifecycleCoroutineScope
    ) {
        lifecycleScope.launchWhenStarted {
            if (startLoadingIsVisible) {
                delay(LOADING_DURATION)
            }

            getTopFilmList()
        }
    }

    fun onSetFavourite(function: () -> Unit) {
        getFavouriteList()
        function.invoke()
    }

    fun onSetPopular(
        isRefresh: Boolean,
        makeRequest: Boolean,
        function: () -> Unit
    ) {
        if (makeRequest)
            getTopFilmList(isRefresh)
        function.invoke()
    }

    fun onLoadMoreItems() {
        _isLoading.onNext(true)
        getTopFilmList()
    }

    fun onPreviewUiBind() {
        _isLoading.onNext(false)
    }

    override fun onCleared() {
        getTopFilmListDisposable = null
        getTopFilmListFromDbDisposable = null
        getFavouriteListDisposable = null
        addToFavouriteListDisposable = null
        getTopFilmInfoDisposable = null
        super.onCleared()
    }

    companion object {
        private const val LOADING_DURATION = 2500L
    }

}