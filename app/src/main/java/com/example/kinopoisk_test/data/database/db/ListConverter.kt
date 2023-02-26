package com.example.kinopoisk_test.data.database.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class ListConverter {

    @TypeConverter
    fun fromStringList(stringList: List<String>): String {
        val gson = Gson()
        val type: Type = object : TypeToken<List<String>>() {}.type
        return gson.toJson(stringList, type)
    }

    @TypeConverter // note this annotation
    fun toStringList(string: String): List<String> {
        val gson = Gson()
        val type: Type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(string, type)
    }
}