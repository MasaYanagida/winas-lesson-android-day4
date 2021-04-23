package com.winas_lesson.android.day4.day4homework.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
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

@Entity(
    tableName = "content",
    indices = []
)
@JsonClass(generateAdapter = true)
data class Content (
    @PrimaryKey @ColumnInfo(name = "id") var id: Int = 0,
    @ColumnInfo(name = "name") val name: String = "",
    @ColumnInfo(name = "address") val address: String = "",
    @ColumnInfo(name = "gender_id") @Json(name = "gender") var genderId: Int = Gender.UNKNOWN.key
) {
    val gender: Gender
        get() {
            val genders = Gender.values().filter { it.key == genderId }
            return if (genders.isNotEmpty()) genders[0] else Gender.UNKNOWN
        }
    val debugDescription: String
        get() {
            return "ID= $id, name= $name, address= $address, gender= $gender"
        }
}
