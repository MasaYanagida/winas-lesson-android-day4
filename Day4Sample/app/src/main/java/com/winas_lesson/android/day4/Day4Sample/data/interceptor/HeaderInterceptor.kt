package com.winas_lesson.android.day4.Day4Sample.data.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    companion object {
        const val HEADER_AUTH_TOKEN = "Authorization"
    }
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        builder.addHeader(HEADER_AUTH_TOKEN, "Bearer qawsedrftgyhujikolpedrftgyh")
        return chain.proceed(builder.build())
    }
}
