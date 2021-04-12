package com.winas_lesson.android.day4.day4homework.data.local

import androidx.room.*
import com.winas_lesson.android.day4.day4homework.data.model.Content
import kotlinx.coroutines.flow.Flow

@Dao
interface ContentDao {

    @Query("SELECT * FROM content")
    fun getAllContents(): Flow<List<Content>>

    @Query("DELETE FROM content")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllContents(contents: List<Content>)
}