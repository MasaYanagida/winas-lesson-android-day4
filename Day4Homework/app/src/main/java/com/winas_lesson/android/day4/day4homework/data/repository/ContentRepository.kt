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
        val rand = arrayOf(1, 2, 3, 4).random()
        val response = when (rand) {
            1 -> Repository.apiInterface.getList1()
            2 -> Repository.apiInterface.getList2()
            3 -> Repository.apiInterface.getList3()
            4 -> Repository.apiInterface.getList4()
            else -> Repository.apiInterface.getList1()
        }
        GlobalScope.async {
            response.await()
            Handler(Looper.getMainLooper()).post {
                val response = response.getCompleted()
                if (response.isSuccessful) {
                    val data = response.body()
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
