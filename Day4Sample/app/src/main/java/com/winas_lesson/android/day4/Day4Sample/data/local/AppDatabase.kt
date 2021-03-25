package com.winas_lesson.android.day4.Day4Sample.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.winas_lesson.android.day4.Day4Sample.data.local.daos.ContentDao
import com.winas_lesson.android.day4.Day4Sample.data.model.Content

@Database(entities = [Content::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contentDao(): ContentDao
}
