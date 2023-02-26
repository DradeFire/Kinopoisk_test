package com.example.kinopoisk_test.common.logger

import android.util.Log

object MyLogger {

    private const val TAG = "MY_LOG"

    fun log(message: String, e: Throwable? = null) {
        Log.d(TAG, message, e)
    }

}