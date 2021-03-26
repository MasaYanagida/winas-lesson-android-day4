package com.winas_lesson.android.day4.day4homework.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.winas_lesson.android.day4.day4homework.data.model.Content
import kotlinx.coroutines.flow.Flow

@Dao
interface ContentDao {
    @Query("SELECT * FROM content")
    fun getAllContents(): Flow<List<Content>>

    @Query("DELETE FROM content")
    fun deleteAll()

    @Insert
    fun add(data: Content)

    @Insert
    fun addAll(data: List<Content>)
}
