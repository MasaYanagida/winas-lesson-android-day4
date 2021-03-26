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
    fun getList(
        completion: (contents: List<Content>) -> Unit,
        failure: () -> Unit
    ) {
        //val response = Repository.apiInterface.getList()
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

    fun getListWithErrorCode(
        completion: (contents: List<Content>, totalCount: Int, errorCode: Int) -> Unit,
        failure: () -> Unit
    ) {
        val response = Repository.apiInterface.getListWithErrorCode()
        GlobalScope.async {
            response.await()
            Handler(Looper.getMainLooper()).post {
                val response = response.getCompleted()
                if (response.isSuccessful) {
                    val dto = response.body()
                    if (dto != null) {
                        completion(dto.data, dto.totalCount, dto.errorCode)
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
