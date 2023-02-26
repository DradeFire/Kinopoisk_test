package com.example.kinopoisk_test.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import com.example.kinopoisk_test.R
import com.example.kinopoisk_test.app.App
import com.example.kinopoisk_test.databinding.ActivityMainBinding
import com.example.kinopoisk_test.presentation.screens.Screens
import com.github.terrakok.cicerone.androidx.AppNavigator

class MainActivity : AppCompatActivity() {

    private val navigator = AppNavigator(this, R.id.container)

    var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (binding == null) {
            binding = ActivityMainBinding.inflate(layoutInflater)
        }
        setContentView(binding?.root)

        (applicationContext as? App)?.router?.newRootScreen(Screens.topFilmPreviewListFragment())
    }

    override fun onBackPressed() {
        binding?.toolBar?.root?.isVisible = true
        super.onBackPressed()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        (applicationContext as? App)?.navigatorHolder?.setNavigator(navigator)
    }

    override fun onPause() {
        (applicationContext as? App)?.navigatorHolder?.removeNavigator()
        super.onPause()
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

}