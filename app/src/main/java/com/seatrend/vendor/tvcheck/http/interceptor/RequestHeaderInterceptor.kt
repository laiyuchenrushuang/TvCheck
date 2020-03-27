package com.example.vendor.nextapp.http.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Created by ly on 2020/3/25 13:22
 *
 * HEADER 拦截器
 */
internal class RequestHeaderInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val request = original.newBuilder()
            .addHeader("Content-Type", "application/json; charset=UTF-8")
            .method(original.method(), original.body())
            .build()

        return chain.proceed(request)
    }
}