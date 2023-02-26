package com.example.kinopoisk_test.data.api

import com.example.kinopoisk_test.common.consts.Consts.API.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitApi {

    val retrofit: Api = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(Api::class.java)

}