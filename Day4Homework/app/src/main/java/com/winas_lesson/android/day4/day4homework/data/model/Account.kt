package com.winas_lesson.android.day4.day4homework.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "account",
    indices = []
)
data class Account (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "user_id") val userId: String = "",
    @ColumnInfo(name = "password") val password: String = ""
)
