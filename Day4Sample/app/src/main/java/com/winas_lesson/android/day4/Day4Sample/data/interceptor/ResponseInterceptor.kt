package com.winas_lesson.android.day4.Day4Sample.data.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class ResponseInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val res = chain.proceed(chain.request())
        if (res.isSuccessful) {
            res.header("Token", null)?.let { token ->
                if (!token.isBlank()) {
                    //Timber.d("#issue AuthorizationToken updated!! ${token}")
                }
            }
            return res
        }
        when (res.code) {
            // TODO : handle http status code
        }
        return res
    }
}
