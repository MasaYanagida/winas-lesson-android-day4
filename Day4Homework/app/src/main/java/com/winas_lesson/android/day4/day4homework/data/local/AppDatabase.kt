package com.winas_lesson.android.day4.day4homework.data.local

// TODO
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.winas_lesson.android.day4.day4homework.data.local.daos.ContentDao
import com.winas_lesson.android.day4.day4homework.data.model.Content
import com.winas_lesson.android.day4.day4homework.data.local.daos.AccountDao
import com.winas_lesson.android.day4.day4homework.data.model.Account



@Database(entities = [Content::class,Account::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contentDao(): ContentDao
    abstract fun accountDao(): AccountDao
}
