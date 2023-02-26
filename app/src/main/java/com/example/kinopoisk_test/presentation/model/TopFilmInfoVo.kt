package com.example.kinopoisk_test.presentation.model

import android.text.Spannable
import android.text.SpannableString

data class TopFilmInfoVo(
    val posterUrl: String,
    val title: String,
    val description: String,
    val genre: Spannable,
    val country: Spannable
) {
    companion object {
        fun empty() = TopFilmInfoVo(
            "",
            "",
            "",
            SpannableString.valueOf(""),
            SpannableString.valueOf("")
        )
    }
}