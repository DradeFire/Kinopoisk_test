package com.example.kinopoisk_test.presentation.fragments.main.recycler

import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kinopoisk_test.R
import com.example.kinopoisk_test.common.logger.MyLogger
import com.example.kinopoisk_test.databinding.ItemPreviewElementBinding
import com.example.kinopoisk_test.presentation.fragments.main.TopFilmPreviewListViewModel
import com.example.kinopoisk_test.presentation.model.TopFilmPreviewVo
import com.example.kinopoisk_test.presentation.screens.Screens
import com.github.terrakok.cicerone.Router
import kotlin.collections.ArrayList

class TopFilmPreviewListAdapter(
    val previews: ArrayList<TopFilmPreviewVo>,
    private val router: Router,
    private val viewModel: TopFilmPreviewListViewModel,
    private val orientation: Int
) : RecyclerView.Adapter<TopFilmPreviewListAdapter.TopFilmPreviewListViewHolder>() {

    var filterString = ""

    class TopFilmPreviewListViewHolder(
        private val binding: ItemPreviewElementBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            topFilmPreviewVo: TopFilmPreviewVo,
            router: Router,
            viewModel: TopFilmPreviewListViewModel,
            orientation: Int
        ) = with(binding) {
            txPreviewGenreAndYear.text = topFilmPreviewVo.descWithYear
            txPreviewTitle.text = topFilmPreviewVo.title

            Glide.with(binding.root.context)
                .load(topFilmPreviewVo.posterUrl)
                .error(R.drawable.ic_error_image_load)
                .into(imPreviewPoster)

            imFavourite.isVisible = topFilmPreviewVo.isFavourite

            root.setOnLongClickListener {
                MyLogger.log("${topFilmPreviewVo.filmId}\"${topFilmPreviewVo.title}\" -> long clicked")
                viewModel.addToFavouriteList(topFilmPreviewVo.filmId)
                imFavourite.isVisible = true
                true
            }

            root.setOnClickListener {
                when(orientation) {
                    Configuration.ORIENTATION_LANDSCAPE -> {
                        MyLogger.log("ORIENTATION_LANDSCAPE -> ${topFilmPreviewVo.filmId}\"${topFilmPreviewVo.title}\" -> clicked")
                        viewModel.getTopFilmInfo(topFilmPreviewVo.filmId)
                    }
                    else -> {
                        MyLogger.log("ORIENTATION_PORTRAIT -> ${topFilmPreviewVo.filmId}\"${topFilmPreviewVo.title}\" -> clicked")
                        router.navigateTo(Screens.topFilmInfoFragment(topFilmPreviewVo.filmId))
                    }
                }
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TopFilmPreviewListViewHolder =
        TopFilmPreviewListViewHolder(
            ItemPreviewElementBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: TopFilmPreviewListViewHolder, position: Int) {
        holder.bind(previews[position], router, viewModel, orientation)
    }

    override fun getItemCount() = previews.size

    fun setData(previewList: List<TopFilmPreviewVo>) {
        MyLogger.log("filterString -> $filterString")
        val filteredPreviewList = if (filterString.isNotBlank()) {
            previewList.filter { topFilmPreviewVo: TopFilmPreviewVo ->
                topFilmPreviewVo.title.lowercase().startsWith(
                    filterString.lowercase()
                )
            }
        } else {
            previewList
        }

        val diffUtil = TopFilmPrevDiffUtil(this.previews, filteredPreviewList)
        val result = DiffUtil.calculateDiff(diffUtil)

        this.previews.clear()
        this.previews.addAll(filteredPreviewList)
        result.dispatchUpdatesTo(this)
    }

}