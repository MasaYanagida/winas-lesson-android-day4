package com.winas_lesson.android.day4.day4homework.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "account")
data class Account (
    @PrimaryKey
    @ColumnInfo(name = "user_id")
    var userId: String,
    var password: String
)
