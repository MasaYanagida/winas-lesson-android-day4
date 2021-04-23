package com.winas_lesson.android.day4.day4homework.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.winas_lesson.android.day4.day4homework.data.local.daos.AccountDao
import com.winas_lesson.android.day4.day4homework.data.local.daos.ContentDao
import com.winas_lesson.android.day4.day4homework.data.model.Account
import com.winas_lesson.android.day4.day4homework.data.model.Content

@Database(entities = [Content::class, Account::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contentDao(): ContentDao
    abstract fun accountDao(): AccountDao
}
