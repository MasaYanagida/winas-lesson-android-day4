package com.winas_lesson.android.day4.day4homework.data.local

// TODO
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.winas_lesson.android.day4.day4homework.data.local.daos.ContentDao
import com.winas_lesson.android.day4.day4homework.data.model.Content

@Database(entities = [Content::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contentDao(): ContentDao
}
