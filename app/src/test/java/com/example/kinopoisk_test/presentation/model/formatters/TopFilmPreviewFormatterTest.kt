package com.example.kinopoisk_test.presentation.model.formatters

import com.example.kinopoisk_test.domain.model.TopFilmPreview
import com.example.kinopoisk_test.domain.model.TopFilmPreviewFavourite
import com.example.kinopoisk_test.presentation.model.TopFilmPreviewVo
import org.junit.Assert.*

import org.junit.Test

class TopFilmPreviewFormatterTest {

    private val formatter = TopFilmPreviewFormatter()

    @Test
    fun format_from_TopFilmPreview() {
        val res1 = formatter.format(test1_value1)
        val res2 = formatter.format(test1_value2)

        assertEquals(test1_expected1, res1)
        assertEquals(test1_expected2, res2)
        assertNotEquals(res1, res2)
    }

    @Test
    fun format_from_TopFilmPreviewFavourite() {
        val res1 = formatter.format(test2_value1)
        val res2 = formatter.format(test2_value2)

        assertEquals(test2_expected1, res1)
        assertEquals(test2_expected2, res2)
        assertNotEquals(res1, res2)
    }

    @Test
    fun formatToFavourite() {
        val res1 = formatter.formatToFavourite(test3_value1)
        val res2 = formatter.formatToFavourite(test3_value2)

        assertEquals(test3_expected1, res1)
        assertEquals(test3_expected2, res2)
        assertNotEquals(res1, res2)
    }

    private companion object {
        val test1_value1 = listOf(
            TopFilmPreview(
                filmId = 1,
                nameRu = "1",
                posterUrlPreview = "1",
                year = "1",
                genre = "1",
                isFavourite = true
            ),
            TopFilmPreview(
                filmId = -1,
                nameRu = "",
                posterUrlPreview = "",
                year = "",
                genre = "",
                isFavourite = false
            )
        )
        val test1_value2 = listOf<TopFilmPreview>()
        val test1_expected1 = listOf(
            TopFilmPreviewVo(
                1,
                "1",
                "1",
                "1 (1)",
                true
            ),
            TopFilmPreviewVo(
                -1,
                "",
                "",
                " ()",
                false
            )
        )
        val test1_expected2 = listOf<TopFilmPreviewVo>()

        val test2_value1 = listOf(
            TopFilmPreviewFavourite(
                Int.MAX_VALUE,
                "111",
                "111",
                        "111",
                "123"
            ),
            TopFilmPreviewFavourite(
                Int.MIN_VALUE,
                "",
                "",
                "",
                ""
            )
        )
        val test2_value2= listOf<TopFilmPreviewFavourite>()
        val test2_expected1 = listOf(
            TopFilmPreviewVo(
                Int.MAX_VALUE,
                "111",
                "111",
                "123 (111)",
                true
            ),
            TopFilmPreviewVo(
                Int.MIN_VALUE,
                "",
                "",
                " ()",
                true
            )
        )
        val test2_expected2 = listOf<TopFilmPreviewVo>()

        val test3_value1 = listOf(
            TopFilmPreview(
                Int.MAX_VALUE,
                "123",
                "123",
                "123",
                "123",
                true
            ),
            TopFilmPreview(
                Int.MIN_VALUE,
                "",
                "",
                "",
                "",
                false
            )
        )
        val test3_value2 = listOf<TopFilmPreview>()
        val test3_expected1 = listOf(
            TopFilmPreviewFavourite(
                Int.MAX_VALUE,
                "123",
                "123",
                "123",
                "123"
            ),
            TopFilmPreviewFavourite(Int.MIN_VALUE,
                "",
                "",
                "",
                "")
        )
        val test3_expected2= listOf<TopFilmPreviewFavourite>()
    }
}

//@JvmName("format_from_TopFilmPreview")
//fun format(previews: List<TopFilmPreview>): List<TopFilmPreviewVo> {
//    return previews.map {
//        TopFilmPreviewVo(
//            it.filmId,
//            it.posterUrlPreview,
//            it.nameRu,
//            "${it.genre} (${it.year})",
//            it.isFavourite
//        )
//    }
//}
//
//@JvmName("format_from_TopFilmPreviewFavourite")
//fun format(previews: List<TopFilmPreviewFavourite>): List<TopFilmPreviewVo> {
//    return previews.map {
//        TopFilmPreviewVo(
//            it.filmId,
//            it.posterUrlPreview,
//            it.nameRu,
//            "${it.genre} (${it.year})",
//            true
//        )
//    }
//}
//
//fun formatToFavourite(previews: List<TopFilmPreview>): List<TopFilmPreviewFavourite> {
//    return previews.map {
//        TopFilmPreviewFavourite(
//            it.filmId,
//            it.nameRu,
//            it.posterUrlPreview,
//            it.year,
//            it.genre
//        )
//    }
//}