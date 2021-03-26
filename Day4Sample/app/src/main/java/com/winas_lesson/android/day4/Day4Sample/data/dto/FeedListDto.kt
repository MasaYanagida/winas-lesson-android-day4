package com.winas_lesson.android.day4.Day4Sample.data.dto

import com.squareup.moshi.Json
import com.winas_lesson.android.day4.Day4Sample.interfaces.Feedable

data class FeedListDto<T>(
    val data: List<T>,
    @Json(name = "total_count") val totalCount: Int,
    @Json(name = "error_code") val errorCode: Int
) where T: Feedable
