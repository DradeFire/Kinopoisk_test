package com.example.kinopoisk_test.data.api

import com.example.kinopoisk_test.common.consts.Consts.API.API_KEY
import com.example.kinopoisk_test.common.consts.Consts.API.API_TOP_FILM_INFO
import com.example.kinopoisk_test.common.consts.Consts.API.API_TOP_FILM_LIST
import com.example.kinopoisk_test.common.consts.Consts.API.API_TOP_FILM_LIST_QUERY_TYPE
import com.example.kinopoisk_test.data.dto.topinfo.TopFilmInfoDto
import com.example.kinopoisk_test.data.dto.tops.TopFilmListDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET(API_TOP_FILM_LIST)
    fun getTopFilmList(
        @Header("X-API-KEY") apiKey: String = API_KEY,
        @Query("type") type: String = API_TOP_FILM_LIST_QUERY_TYPE,
        @Query("page") page: Int
    ) : Call<TopFilmListDto>

    @GET(API_TOP_FILM_INFO)
    fun getTopFilmInfo(
        @Header("X-API-KEY") apiKey: String = API_KEY,
        @Path("id") id: Int,
    ) : Call<TopFilmInfoDto>

}