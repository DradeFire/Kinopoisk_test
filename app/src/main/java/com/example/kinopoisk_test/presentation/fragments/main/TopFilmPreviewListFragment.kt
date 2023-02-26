package com.example.kinopoisk_test.presentation.fragments.main

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.kinopoisk_test.R
import com.example.kinopoisk_test.common.logger.MyLogger
import com.example.kinopoisk_test.databinding.FragmentTopFilmPreviewListBinding
import com.example.kinopoisk_test.presentation.fragments.BaseFragment
import com.example.kinopoisk_test.presentation.fragments.main.recycler.PaginationListener
import com.example.kinopoisk_test.presentation.fragments.main.recycler.TopFilmPreviewListAdapter
import com.example.kinopoisk_test.presentation.model.TopFilmInfoVo
import com.example.kinopoisk_test.presentation.model.TopFilmPreviewVo

class TopFilmPreviewListFragment :
    BaseFragment<FragmentTopFilmPreviewListBinding, TopFilmPreviewListViewModel>() {

    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentTopFilmPreviewListBinding
        get() = FragmentTopFilmPreviewListBinding::inflate
    override val viewModelClass: Class<TopFilmPreviewListViewModel>
        get() = TopFilmPreviewListViewModel::class.java
    override val screenName: String
        get() = SCREEN_NAME

    private var isPopular = true


    override fun initUI() {
        super.initUI()
        uiAfterStartLoading()
        bindSearch()

        bindRefreshListener {
            viewModel?.onInitRefresh()
        }

        closeToolBar()
    }

    private fun bindRefreshListener(unit: () -> Unit) {
        binding?.swipeView?.setOnRefreshListener {
            unit.invoke()
        }
    }

    private fun uiAfterStartLoading() {
        binding?.startLoading?.root?.isVisible?.let {
            viewModel?.onUiAfterStartLoading(it, lifecycleScope)
        }
    }

    private fun bindSearch() {
        activityBinding?.toolBar?.inputSearch?.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // no-op
            }

            override fun onTextChanged(str: CharSequence?, p1: Int, p2: Int, p3: Int) {
                (binding?.rcPreviews?.adapter as? TopFilmPreviewListAdapter)?.filterString =
                    str.toString()
                viewModel?.filmList?.value?.let {
                    (binding?.rcPreviews?.adapter as? TopFilmPreviewListAdapter)?.setData(
                        it
                    )
                } ?: if (isPopular) {
                    setPopular()
                } else {
                    setFavourite()
                }
            }

            override fun afterTextChanged(str: Editable?) {
                //no-op
            }
        })
    }

    override fun initRecycler() {
        super.initRecycler()
        binding?.rcPreviews?.apply {
            router?.let { r ->
                viewModel?.let { vm ->
                    adapter = TopFilmPreviewListAdapter(
                        arrayListOf(),
                        r,
                        vm,
                        resources.configuration.orientation
                    )
                }
            }
            val lManager = LinearLayoutManager(requireContext()).also {
                layoutManager = it
            }
            addOnScrollListener(object : PaginationListener(lManager) {
                override fun isLastPage(): Boolean = viewModel?.isLastPage ?: true

                override fun isLoading(): Boolean = viewModel?.isLoading?.value ?: true

                override fun loadMoreItems() {
                    viewModel?.onLoadMoreItems()
                }

            })
        }
    }

    override fun initObservers() {
        super.initObservers()
        viewModel?.let { viewmodel ->
            compositeDisposable?.addAll(

                viewmodel.isError.subscribe({ isError ->
                    MyLogger.log("isError -> $isError")
                    if (isError) {
                        showErrorScreen()
                        closeLoadingScreen()
                        closeOtherViews()
                        closeStartScreen()
                    } else {
                        closeLoadingScreen()
                        closeErrorScreen()
                    }
                }, {
                    MyLogger.log("isError : Exception", it)
                }),

                viewmodel.isLoading.subscribe({ isLoading ->
                    MyLogger.log("isLoading -> $isLoading")
                    if (isLoading) {
                        showLoadingScreen()
                        closeErrorScreen()
                        closeStartScreen()
                    } else {
                        closeLoadingScreen()
                        closeErrorScreen()
                    }
                }, {
                    MyLogger.log("isLoading : Exception", it)
                }),

                viewmodel.filmList.subscribe({ filmList ->
                    MyLogger.log("filmList -> ${filmList.size} items")
                    closeLoadingScreen()
                    closeErrorScreen()
                    closeStartScreen()
                    bindPreviewUi(filmList)
                }, {
                    MyLogger.log("filmList : Exception", it)
                }),

                viewmodel.filmInfo.subscribe({ filmInfo ->
                    MyLogger.log("filmInfo -> $filmInfo")
                    closeLoadingScreen()
                    closeErrorScreen()
                    closeStartScreen()
                    bindInfoUI(filmInfo)
                }, {
                    MyLogger.log("filmInfo : Exception", it)
                })
            )
        }
    }

    private fun bindInfoUI(filmInfo: TopFilmInfoVo) {
        binding?.infoContent?.apply {
            root.isVisible = true

            txCountry.apply {
                isVisible = true
                text = filmInfo.country
            }
            txGenres.apply {
                isVisible = true
                text = filmInfo.genre
            }
            txDescription.apply {
                isVisible = true
                text = filmInfo.description
            }
            txTitle.apply {
                isVisible = true
                text = filmInfo.title
            }

            imPoster.isVisible = true
            Glide.with(requireContext())
                .load(filmInfo.posterUrl)
                .error(R.drawable.ic_error_image_load)
                .into(imPoster)
        }
    }

    private fun bindPreviewUi(filmList: List<TopFilmPreviewVo>) {
        (binding?.rcPreviews?.adapter as? TopFilmPreviewListAdapter)?.setData(filmList)

        showRcAndButtons()
        showToolBar()

        viewModel?.onPreviewUiBind()

        binding?.imNotFound?.isVisible =
            (binding?.rcPreviews?.adapter as? TopFilmPreviewListAdapter)?.previews?.isEmpty()
                ?: false
    }

    override fun initButtons() {
        super.initButtons()
        binding?.apply {
            btChoosePopular.setOnClickListener {
                onChoosePopularButtonClicked()
            }
            btChooseFavourite.setOnClickListener {
                onChooseFavouriteButtonClicked()
            }
            error.btErrorButton.setOnClickListener {
                onErrorButtonClicked()
            }
        }
        activityBinding?.apply {
            toolBar.btSearch.setOnClickListener {
                onSearchButtonClicked()
            }
            toolBar.btBack.setOnClickListener {
                onBackButtonClicked()
            }
        }
    }

    private fun onErrorButtonClicked() {
        setPopular(isRefresh = true)
    }

    private fun onChooseFavouriteButtonClicked() {
        if (isPopular) {
            setFavourite()
        }
    }

    private fun onChoosePopularButtonClicked() {
        if (!isPopular) {
            setPopular(isRefresh = true)
        }
    }

    private fun onSearchButtonClicked() {
        activityBinding?.toolBar?.apply {
            txToolbarTitle.isVisible = false
            btBack.isVisible = true
            btSearch.isVisible = false
            inputSearch.isVisible = true
        }
    }

    private fun onBackButtonClicked() {
        activityBinding?.toolBar?.apply {
            txToolbarTitle.isVisible = true
            btBack.isVisible = false
            btSearch.isVisible = true
            inputSearch.isVisible = false

            if (isPopular) {
                setPopular(true)
            } else {
                setFavourite()
            }

            inputSearch.setText("")
            (binding?.rcPreviews?.adapter as? TopFilmPreviewListAdapter)?.filterString = ""
        }
    }

    private fun setPopular(isRefresh: Boolean = false, makeRequest: Boolean = true) {
        viewModel?.onSetPopular(isRefresh, makeRequest) {
            isPopular = true
            binding?.apply {
                btChooseFavourite.apply {
                    background = resources.getDrawable(R.drawable.not_choosed_background)
                    setTextColor(resources.getColor(R.color.not_choosed))
                }
                btChoosePopular.apply {
                    background = resources.getDrawable(R.drawable.choosed_background)
                    setTextColor(resources.getColor(R.color.choosed))
                }

                bindRefreshListener {
                    viewModel?.onInitRefresh()
                }

            }

        }

    }

    private fun setFavourite() {
        viewModel?.onSetFavourite {
            isPopular = false
            binding?.apply {
                btChooseFavourite.apply {
                    background = resources.getDrawable(R.drawable.choosed_background)
                    setTextColor(resources.getColor(R.color.choosed))
                }
                btChoosePopular.apply {
                    background = resources.getDrawable(R.drawable.not_choosed_background)
                    setTextColor(resources.getColor(R.color.not_choosed))
                }

                bindRefreshListener {
                    binding?.swipeView?.isRefreshing = false
                }
            }
        }
    }

    private fun closeStartScreen() {
        binding?.startLoading?.root?.isVisible = false
    }

    override fun showLoadingScreen() {
        binding?.apply {
            loadingProgBar.isVisible = true
            imNotFound.isVisible = false
        }
    }

    override fun showErrorScreen() {
        binding?.apply {
            error.root.isVisible = true
            imNotFound.isVisible = false
        }
    }

    override fun closeLoadingScreen() {
        binding?.apply {
            loadingProgBar.isVisible = false
            imNotFound.isVisible = false
            swipeView.isRefreshing = false
        }
    }

    override fun closeErrorScreen() {
        binding?.apply {
            error.root.isVisible = false
            imNotFound.isVisible = false
        }
    }

    override fun closeOtherViews() {
        binding?.apply {
            startLoading.root.isVisible = false
            rcPreviews.isVisible = false
            btChooseFavourite.isVisible = false
            btChoosePopular.isVisible = false
            imNotFound.isVisible = false
        }
    }

    override fun showToolBar() {
        activityBinding?.toolBar?.apply {
            txToolbarTitle.text = if (isPopular) {
                POPULAR
            } else {
                FAVOURITE
            }
            root.isVisible = true
        }
    }

    override fun closeToolBar() {
        activityBinding?.toolBar?.root?.isVisible = false
    }

    private fun showRcAndButtons() {
        binding?.apply {
            rcPreviews.isVisible = true
            btChooseFavourite.isVisible = true
            btChoosePopular.isVisible = true
        }
    }

    private fun closeRcAndButtons() {
        binding?.apply {
            rcPreviews.isVisible = false
            btChooseFavourite.isVisible = false
            btChoosePopular.isVisible = false
        }
    }

    private fun showNotFoundScreen() {
        binding?.apply {
            imNotFound.isVisible = true
        }
    }

    private fun closeNotFoundScreen() {
        binding?.apply {
            imNotFound.isVisible = false
        }
    }


    companion object {
        private const val SCREEN_NAME = "TopFilmPreviewListFragment"

        private const val POPULAR = "Популярные"
        private const val FAVOURITE = "Избранное"
    }

}