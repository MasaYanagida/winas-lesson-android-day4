package com.winas_lesson.android.day4.Day4Sample.data.repository

import android.os.Handler
import android.os.Looper
import com.winas_lesson.android.day4.Day4Sample.data.model.Content
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import retrofit2.Response

class ContentRepository {
    fun getSingle(
        completion: (content: Content) -> Unit,
        failure: () -> Unit
    ) {
        val response = Repository.apiInterface.getSingle()
        GlobalScope.async {
            response.await()
            Handler(Looper.getMainLooper()).post {
                val response = response.getCompleted()
                // TODO
            }
        }
    }
    fun getList(
        completion: (contents: List<Content>) -> Unit,
        failure: () -> Unit
    ) {
        val response = Repository.apiInterface.getList()
        GlobalScope.async {
            response.await()
            Handler(Looper.getMainLooper()).post {
                val response = response.getCompleted()
                // TODO
            }
        }
    }
}
