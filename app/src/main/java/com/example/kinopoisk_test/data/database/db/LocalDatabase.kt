package com.example.kinopoisk_test.data.database.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.kinopoisk_test.common.consts.Consts.DATABASE.DATABASE_NAME
import com.example.kinopoisk_test.data.database.dao.LocalDao
import com.example.kinopoisk_test.domain.model.TopFilmInfo
import com.example.kinopoisk_test.domain.model.TopFilmPreview
import com.example.kinopoisk_test.domain.model.TopFilmPreviewFavourite

@Database(entities = [TopFilmInfo::class, TopFilmPreview::class, TopFilmPreviewFavourite::class], version = 1, exportSchema = false)
@TypeConverters(value = [ListConverter::class])
abstract class LocalDatabase: RoomDatabase() {
    abstract fun getDao(): LocalDao

    companion object {
        lateinit var database: LocalDatabase
            private set

        fun initDatabase(appContext: Context) {
            database = Room.databaseBuilder(
                appContext,
                LocalDatabase::class.java,
                DATABASE_NAME
            ).build()
        }
    }

}