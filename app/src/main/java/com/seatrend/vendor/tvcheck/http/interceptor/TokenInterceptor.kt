package com.example.vendor.nextapp.http.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


/**
 * Created by ly on 2020/3/25 13:24
 *
 * 全局自动刷新Token的拦截器
 */
class TokenInterceptor : Interceptor {

    companion object {
        //搞个全局的最新TOKEN
        val NEWEST_TOKEN = ""
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        if (isTokenExpired(response)) {//根据和服务端的约定判断token过期
            //同步请求方式，获取最新的Token
            val newSession = getNewToken()
            //使用新的Token，创建新的请求
            val newRequest = chain.request()
                .newBuilder()
                    //不一定这样加，和后台配对
                .header("Authorization", "$newSession")
                .build()
            //重新请求
            return chain.proceed(newRequest)
        }
        return response
    }

    //获取最新的TOKEN
    private fun getNewToken(): String {

        //网络请求..

        return ""
    }

    /**
     * 根据Response，判断Token是否失效
     * {
     * 这个需要与服务器沟通才能定
     * }
     * @param response
     * @return
     */
    private fun isTokenExpired(response: Response): Boolean {
        return response.code() === 301
    }
}