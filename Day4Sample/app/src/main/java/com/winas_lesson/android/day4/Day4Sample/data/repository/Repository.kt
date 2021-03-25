package com.winas_lesson.android.day4.Day4Sample.data.repository

import androidx.room.Room
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.winas_lesson.android.day4.Day4Sample.App
import com.winas_lesson.android.day4.Day4Sample.data.api.SampleApiService
import com.winas_lesson.android.day4.Day4Sample.data.interceptor.HeaderInterceptor
import com.winas_lesson.android.day4.Day4Sample.data.interceptor.ResponseInterceptor
import com.winas_lesson.android.day4.Day4Sample.data.local.AppDatabase
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class Repository {
    companion object {
        val localDb = Room.databaseBuilder(
            App.context,
            AppDatabase::class.java, "sample-database"
        ).build()

        val apiInterface = Retrofit
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
                .addInterceptor(HeaderInterceptor())
                .addInterceptor(ResponseInterceptor())
                .connectTimeout(30000L, TimeUnit.MILLISECONDS)
                .readTimeout(30000L, TimeUnit.MILLISECONDS)
                .writeTimeout(30000L, TimeUnit.MILLISECONDS)
                .build()
        }
    }
}
