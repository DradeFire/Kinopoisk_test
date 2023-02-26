package com.example.kinopoisk_test.common.consts

object Consts {

    object API {
        const val BASE_URL = "https://kinopoiskapiunofficial.tech"

        const val API_TOP_FILM_LIST = "api/v2.2/films/top"
        const val API_TOP_FILM_INFO = "api/v2.2/films/{id}"

        const val API_TOP_FILM_LIST_QUERY_TYPE = "TOP_250_BEST_FILMS"

        const val API_KEY = "e30ffed0-76ab-4dd6-b41f-4c9da2b2735b"
    }

    object DATABASE {
        const val DATABASE_NAME = "database_local.db"

        const val DATABASE_FILM_INFO_TABLE_NAME = "films_info"
        const val DATABASE_PREVIEW_LIST_TABLE_NAME = "previews"
        const val DATABASE_FAVOURITE_LIST_TABLE_NAME = "favourites"
    }


}