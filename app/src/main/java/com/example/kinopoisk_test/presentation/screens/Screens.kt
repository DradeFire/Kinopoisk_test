package com.example.kinopoisk_test.presentation.screens

import com.example.kinopoisk_test.presentation.fragments.info.TopFilmInfoFragment
import com.example.kinopoisk_test.presentation.fragments.main.TopFilmPreviewListFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    fun topFilmPreviewListFragment() = FragmentScreen { TopFilmPreviewListFragment() }
    fun topFilmInfoFragment(kinoId: Int) = FragmentScreen { TopFilmInfoFragment(kinoId) }
}