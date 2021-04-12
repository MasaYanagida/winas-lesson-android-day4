package com.winas_lesson.android.day4.day4homework.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

enum class Gender(val key: Int) {
    UNKNOWN(0), MALE(1), FEMALE(2);
    val title: String
        get() {
            return when (this) {
                UNKNOWN -> "Unknown"
                MALE -> "Male"
                FEMALE -> "Female"
            }
        }
}

@Entity(tableName = "content")
@JsonClass(generateAdapter = true)
data class Content (
    @PrimaryKey var id: Int = 0,
    val name: String = "",
    val address: String = "",
    @ColumnInfo(name = "gender_id") var genderId: Int = Gender.UNKNOWN.key
)
