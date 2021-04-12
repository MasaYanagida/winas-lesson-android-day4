package com.winas_lesson.android.day4.day4homework.data.local

import androidx.room.*
import com.winas_lesson.android.day4.day4homework.data.model.Account
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {

    @Query("SELECT * FROM account")
    fun getAccount(): Account

    @Query("DELETE FROM account")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAccount(account: Account)
}