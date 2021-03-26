package com.winas_lesson.android.day4.Day4Sample.data.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.winas_lesson.android.day4.Day4Sample.interfaces.Feedable

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

//// (1) basic
//data class Content (
//    var id: Int = 0,
//    val name: String = "",
//    val address: String = "",
//    var genderId: Int = Gender.UNKNOWN.key
//)
//
//// (2) json data bindable
//@JsonClass(generateAdapter = true)
//data class Content (
//    var id: Int = 0,
//    val name: String = "",
//    val address: String = "",
//    @Json(name = "gender") var genderId: Int = Gender.UNKNOWN.key
//)
//
//// (3) room (SQLite) accessible
//@Entity(
//    tableName = "contents",
//    indices = []
//)
//data class Content (
//    @PrimaryKey @ColumnInfo(name = "id") var id: Int = 0,
//    @ColumnInfo(name = "name") val name: String = "",
//    @ColumnInfo(name = "address") val address: String = "",
//    @ColumnInfo(name = "gender_id") var genderId: Int = Gender.UNKNOWN.key
//)

//@JsonClass(generateAdapter = true)
//data class Content (
//    var id: Int = 0,
//    val name: String = "",
//    val address: String = "",
//    @Json(name = "gender") var genderId: Int = Gender.UNKNOWN.key
//) : Feedable {
//    val gender: Gender
//        get() {
//            val genders = Gender.values().filter { it.key == genderId }
//            return if (genders.isNotEmpty()) genders[0] else Gender.UNKNOWN
//        }
//    val debugDescription: String
//        get() {
//            return "ID= $id, name= $name, address= $address, gender= $gender"
//        }
//}


@Entity(
    tableName = "contents",
    indices = []
)
@JsonClass(generateAdapter = true)
data class Content (
    @PrimaryKey @ColumnInfo(name = "id") var id: Int = 0,
    @ColumnInfo(name = "name") val name: String = "",
    @ColumnInfo(name = "address") val address: String = "",
    @ColumnInfo(name = "gender_id") @Json(name = "gender") var genderId: Int = Gender.UNKNOWN.key
) : Feedable, Parcelable {
    val gender: Gender
        get() {
            val genders = Gender.values().filter { it.key == genderId }
            return if (genders.isNotEmpty()) genders[0] else Gender.UNKNOWN
        }
    val debugDescription: String
        get() {
            return "ID= $id, name= $name, address= $address, gender= $gender"
        }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(address)
        parcel.writeInt(genderId)
    }
    override fun describeContents(): Int {
        return 0
    }
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt())

    companion object CREATOR : Parcelable.Creator<Content> {
        override fun createFromParcel(parcel: Parcel): Content {
            return Content(parcel)
        }
        override fun newArray(size: Int): Array<Content?> {
            return arrayOfNulls(size)
        }
    }
}
