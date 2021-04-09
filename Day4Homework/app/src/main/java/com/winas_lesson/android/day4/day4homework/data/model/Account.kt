package com.winas_lesson.android.day4.day4homework.data.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.winas_lesson.android.day4.day4homework.interfaces.Feedable

@Entity(
    tableName = "account",
    indices = []
)
@JsonClass(generateAdapter = true)
data class Account (
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "name") val name: String = "",
    @ColumnInfo(name = "password") val password: String = ""
) : Feedable, Parcelable {
    val debugDescription: String
        get() {
            return "ID= $id, name= $name, password= $password"
        }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(password)
    }
    override fun describeContents(): Int {
        return 0
    }
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "")

    companion object CREATOR : Parcelable.Creator<Content> {
        override fun createFromParcel(parcel: Parcel): Content {
            return Content(parcel)
        }
        override fun newArray(size: Int): Array<Content?> {
            return arrayOfNulls(size)
        }
    }
}

