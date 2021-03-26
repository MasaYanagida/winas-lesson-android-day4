package com.winas_lesson.android.day4.Day4Sample.data.api

import com.winas_lesson.android.day4.Day4Sample.data.dto.FeedListDto
import com.winas_lesson.android.day4.Day4Sample.data.model.Content
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface SampleApiService {
    @GET("single.json")
    fun getSingle(): Deferred<Response<Content>>

    @GET("list-1.json")
    fun getList(): Deferred<Response<List<Content>>>

    @GET("list-1.json")
    fun getList1(): Deferred<Response<List<Content>>>

    @GET("list-2.json")
    fun getList2(): Deferred<Response<List<Content>>>

    @GET("list-3.json")
    fun getList3(): Deferred<Response<List<Content>>>

    @GET("list-4.json")
    fun getList4(): Deferred<Response<List<Content>>>

    @GET("list-with-error-code.json")
    fun getListWithErrorCode(): Deferred<Response<FeedListDto<Content>>>
}
