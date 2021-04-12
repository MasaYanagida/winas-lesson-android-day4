package com.winas_lesson.android.day4.day4homework.data.api

import com.winas_lesson.android.day4.day4homework.data.model.Content
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface SampleApiService {
    @GET("list-1.json")
    fun getList(): Deferred<Response<List<Content>>>
}
