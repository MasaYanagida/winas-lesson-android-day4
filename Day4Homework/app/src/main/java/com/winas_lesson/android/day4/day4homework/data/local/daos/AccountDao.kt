package com.winas_lesson.android.day4.day4homework.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.winas_lesson.android.day4.day4homework.data.model.Account
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {
    @Query("SELECT * FROM account")
    fun getAllContents(): Flow<List<Account>>

    @Query("DELETE FROM account")
    fun deleteAll()

    @Insert
    fun add(data: Account)

    @Insert
    fun addAll(data: List<Account>)
}
