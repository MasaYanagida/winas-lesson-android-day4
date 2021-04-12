package com.winas_lesson.android.day4.day4homework.data.repository

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.winas_lesson.android.day4.day4homework.App
import com.winas_lesson.android.day4.day4homework.data.api.SampleApiService
import com.winas_lesson.android.day4.day4homework.data.local.AppDatabase
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class Repository {
    companion object {
        val localDb: AppDatabase = AppDatabase.getDatabase(App.context)

        val apiInterface: SampleApiService = Retrofit
            .Builder()
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                )
            )
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(createOkHttpClient())
            .baseUrl("http://cs267.xbit.jp/~w065038/app/winas/")
            .build()
            .create(SampleApiService::class.java)

        val content = ContentRepository()

        private fun createOkHttpClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .connectTimeout(30000L, TimeUnit.MILLISECONDS)
                .readTimeout(30000L, TimeUnit.MILLISECONDS)
                .writeTimeout(30000L, TimeUnit.MILLISECONDS)
                .build()
        }
    }
}
