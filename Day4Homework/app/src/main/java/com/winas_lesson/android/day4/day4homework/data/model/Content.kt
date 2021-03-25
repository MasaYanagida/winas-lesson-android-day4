package com.winas_lesson.android.day4.day4homework.data.model

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

data class Content (
    var id: Int = 0,
    val name: String = "",
    val address: String = "",
    var genderId: Int = Gender.UNKNOWN.key
)
