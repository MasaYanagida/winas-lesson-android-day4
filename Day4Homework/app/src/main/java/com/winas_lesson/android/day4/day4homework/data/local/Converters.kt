package com.winas_lesson.android.day4.day4homework.data.local

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromStringList(list: List<String>): String {
        return list.joinToString(",")
    }
    @TypeConverter
    fun fromStringToList(string: String): List<String> {
        return string.split(",")
    }
}