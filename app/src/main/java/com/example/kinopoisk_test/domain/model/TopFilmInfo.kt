package com.example.kinopoisk_test.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.kinopoisk_test.common.consts.Consts.DATABASE.DATABASE_FILM_INFO_TABLE_NAME
import com.example.kinopoisk_test.data.database.db.ListConverter

@Entity(tableName = DATABASE_FILM_INFO_TABLE_NAME)
data class TopFilmInfo(
    @PrimaryKey val kinopoiskId: Int,
    val country: String,
    val description: String,
    @TypeConverters(value = [ListConverter::class]) val genres: List<String>,
    val filmTitle: String,
    val posterUrl: String,
    val year: Int
) {
    companion object {
        fun empty() = TopFilmInfo(
            -1,
            "",
            "",
            emptyList(),
            "",
            "",
            -1
        )
    }
}
