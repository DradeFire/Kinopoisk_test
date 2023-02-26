package com.example.kinopoisk_test.data.repository

import com.example.kinopoisk_test.data.api.RetrofitApi
import com.example.kinopoisk_test.data.dto.topinfo.mapper.TopFilmInfoMapper
import com.example.kinopoisk_test.data.dto.tops.mapper.TopFilmListMapper
import com.example.kinopoisk_test.domain.model.TopFilmInfo
import com.example.kinopoisk_test.domain.model.TopFilmList
import com.example.kinopoisk_test.domain.repository.RestRepository
import retrofit2.Call

class RestRepositoryImpl: RestRepository {

    private val topFilmListMapper = TopFilmListMapper()
    private val topFilmInfoMapper = TopFilmInfoMapper()

    override fun getTopFilmList(page: Int): TopFilmList {
        val topFilmList = RetrofitApi.retrofit.getTopFilmList(page = page).execute().body()
            ?: return TopFilmList.empty()
        return topFilmListMapper.map(topFilmList)
    }

    override fun getTopFilmInfo(id: Int): TopFilmInfo {
        val topFilmInfo = RetrofitApi.retrofit.getTopFilmInfo(id = id).execute().body()
            ?: return TopFilmInfo.empty()
        return topFilmInfoMapper.map(topFilmInfo)
    }
}