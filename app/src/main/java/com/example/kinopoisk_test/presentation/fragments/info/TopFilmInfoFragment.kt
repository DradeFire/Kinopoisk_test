package com.example.kinopoisk_test.presentation.fragments.info

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.kinopoisk_test.R
import com.example.kinopoisk_test.common.logger.MyLogger
import com.example.kinopoisk_test.databinding.FragmentTopFilmInfoBinding
import com.example.kinopoisk_test.presentation.fragments.BaseFragment
import com.example.kinopoisk_test.presentation.model.TopFilmInfoVo
import io.reactivex.rxjava3.disposables.Disposable

class TopFilmInfoFragment(
    private val kinoId: Int,
) : BaseFragment<FragmentTopFilmInfoBinding, TopFilmInfoViewModel>() {

    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentTopFilmInfoBinding
        get() = FragmentTopFilmInfoBinding::inflate
    override val viewModelClass: Class<TopFilmInfoViewModel>
        get() = TopFilmInfoViewModel::class.java
    override val screenName: String
        get() = SCREEN_NAME


    override fun initUI() {
        super.initUI()
        showLoadingScreen()
        closeToolBar()
        viewModel?.firstInitUI(kinoId)
    }

    override fun initButtons() {
        super.initButtons()
        binding?.apply {
            error.btErrorButton.setOnClickListener {
                viewModel?.onErrorButtonClicked(kinoId)
            }
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
                    }
                }, {
                    MyLogger.log("isError : Exception", it)
                }),

                viewmodel.isLoading.subscribe({ isLoading ->
                    MyLogger.log("isLoading -> $isLoading")
                    if (isLoading) {
                        showLoadingScreen()
                        closeErrorScreen()
                    } else {
                        closeLoadingScreen()
                    }
                }, {
                    MyLogger.log("isLoading : Exception", it)
                }),

                viewmodel.filmInfo.subscribe({ filmInfo ->
                    MyLogger.log("filmInfo -> $filmInfo")
                    closeLoadingScreen()
                    closeErrorScreen()
                    bindInfoUi(filmInfo)
                }, {
                    MyLogger.log("filmInfo : Exception", it)
                })
            )
        }
    }

    private fun bindInfoUi(filmInfo: TopFilmInfoVo) {
        binding?.infoContent?.apply {
            closeLoadingScreen()
            closeErrorScreen()

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

    override fun showLoadingScreen() {
        binding?.apply {
            loadingProgBar.isVisible = true
        }
    }

    override fun showErrorScreen() {
        binding?.apply {
            error.root.isVisible = true
        }
    }

    override fun closeLoadingScreen() {
        binding?.apply {
            loadingProgBar.isVisible = false
        }
    }

    override fun closeErrorScreen() {
        binding?.apply {
            error.root.isVisible = false
        }
    }

    override fun closeOtherViews() {
        binding?.infoContent?.apply {
            txCountry.isVisible = false
            txGenres.isVisible = false
            txDescription.isVisible = false
            txTitle.isVisible = false
            imPoster.isVisible = false
        }
    }

    override fun showToolBar() {
        activityBinding?.toolBar?.root?.isVisible = true
    }

    override fun closeToolBar() {
        activityBinding?.toolBar?.root?.isVisible = false
    }

    companion object {
        private const val SCREEN_NAME = "TopFilmInfoFragment"
    }

}