package com.winas_lesson.android.day4.day4homework.util

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import timber.log.Timber

inline fun <reified T> String.jsonToList(): List<T>? {
    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    val listMyData = Types.newParameterizedType(List::class.java, T::class.java)
    val jsonAdapter: JsonAdapter<List<T>> = moshi.adapter(listMyData)
    try {
        return jsonAdapter.fromJson(this)
    } catch (e: Exception) {
        Timber.d("ERROR: JsonParseError : ${e.message}")
    }
    return null
}

inline fun <reified T> String.jsonToObject() : T? {
    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    val jsonAdapter: JsonAdapter<T> = moshi.adapter(T::class.java)

    try {
        return jsonAdapter.fromJson(this)
    } catch (e: Exception) {
        Timber.d("ERROR: JsonParseError : ${e.message}")
    }
    return null
}
