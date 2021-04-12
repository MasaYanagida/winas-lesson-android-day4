package com.winas_lesson.android.day4.day4homework.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.winas_lesson.android.day4.day4homework.data.model.Account
import com.winas_lesson.android.day4.day4homework.data.model.Content

@Database(entities = [Content::class, Account::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun accountDao(): AccountDao
    abstract fun contentDao(): ContentDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "winas_db"
                ).build()
                INSTANCE = instance

                instance
            }
        }
    }
}
