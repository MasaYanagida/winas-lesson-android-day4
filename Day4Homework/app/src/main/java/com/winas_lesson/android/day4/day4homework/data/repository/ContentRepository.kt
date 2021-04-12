package com.winas_lesson.android.day4.day4homework.data.repository

import android.os.Handler
import android.os.Looper
import com.winas_lesson.android.day4.day4homework.data.model.Content
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class ContentRepository {
    fun getList(
        completion: (contents: List<Content>) -> Unit,
        failure: () -> Unit
    ) {
        val response = Repository.apiInterface.getList()
        GlobalScope.async {
            response.await()
            Handler(Looper.getMainLooper()).post {
                val completedResponse = response.getCompleted()
                if (completedResponse.isSuccessful) {
                    val data = completedResponse.body()
                    if (data != null) {
                        completion(data)
                    } else {
                        failure()
                    }
                } else {
                    failure()
                }
            }
        }
    }
}
