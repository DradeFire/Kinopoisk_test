package com.example.kinopoisk_test.presentation.model.formatters

import android.graphics.Typeface.BOLD
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import com.example.kinopoisk_test.domain.model.TopFilmInfo
import com.example.kinopoisk_test.presentation.model.TopFilmInfoVo

class TopFilmInfoFormatter {

    fun format(info: TopFilmInfo): TopFilmInfoVo {
        return TopFilmInfoVo(
            info.posterUrl,
            info.filmTitle,
            info.description,
            SpannableString.valueOf("Жанры: ${formatGenres(info.genres)}").apply {
                setSpan(StyleSpan(BOLD), 0, 5, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
            },
            SpannableString.valueOf("Страны: ${info.country}").apply {
                setSpan(StyleSpan(BOLD), 0, 6, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
            }
        )
    }

    private fun formatGenres(genres: List<String>): String {
        var str = ""
        genres.mapIndexed { index, genre ->
            str += genre
            if (index != genres.lastIndex){
                str += ", "
            }
        }
        return str
    }

}