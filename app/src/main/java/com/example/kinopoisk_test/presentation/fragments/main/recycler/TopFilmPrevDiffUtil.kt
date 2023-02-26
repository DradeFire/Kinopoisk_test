package com.example.kinopoisk_test.presentation.fragments.main.recycler

import androidx.recyclerview.widget.DiffUtil
import com.example.kinopoisk_test.presentation.model.TopFilmPreviewVo

class TopFilmPrevDiffUtil(
    private val oldList: List<TopFilmPreviewVo>,
    private val newList: List<TopFilmPreviewVo>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].filmId == newList[newItemPosition].filmId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldList[oldItemPosition]
        val new = newList[newItemPosition]
        return old.isFavourite == new.isFavourite &&
                old.title == new.title &&
                old.descWithYear == new.descWithYear &&
                old.posterUrl == new.posterUrl
    }
}