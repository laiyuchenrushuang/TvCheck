package com.seatrend.vendor.tvcheck.Utils

import android.util.Log
import com.example.vendor.nextapp.http.RequestService
import com.example.vendor.nextapp.http.interceptor.RequestEncryptInterceptor
import com.example.vendor.nextapp.http.interceptor.RequestHeaderInterceptor
import com.example.vendor.nextapp.http.interceptor.ResponseDecryptInterceptor
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import okio.Buffer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import java.io.IOException
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit

/**
 * Created by ly on 2020/3/23 17:19
 *
 *
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
class RetrofitUtils private constructor() {


    companion object {
        const val BASE_HEAD = "http://"
        const val BASE_IP = "11.1.1.73"
        const val BASE_PORT = "8086"

        const val MY_TAG = "Http[lylog] "


        ////http://11.1.1.73:8086/ProvinceService2/trffweb/services/TmriOutNewAccess?wsdl
        const val BASE_URL = "$BASE_HEAD$BASE_IP:$BASE_PORT/"


        private const val CONNECT_TIME_OUT = 20L
        private const val WRITE_TIME_OUT = 20L
        private const val READ_TIME_OUT = 20L

        private var retrofitUtils: RetrofitUtils? = null

        //自定义log显示
        private var interceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
            try {
                val text = URLDecoder.decode(message, "utf-8")
                Log.i(MY_TAG, text)
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
                Log.i(MY_TAG, message)
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY)


        //自定义okhttp 保护措施
        private var okHttpClient = OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(interceptor)
            .addInterceptor(RequestHeaderInterceptor())         //拦截器
//            .addInterceptor(RequestEncryptInterceptor())
//            .addInterceptor(ResponseDecryptInterceptor())
            .build()


        //单例
        val instance: RetrofitUtils
            get() {
                if (retrofitUtils == null) {
                    synchronized(RetrofitUtils::class.java) {
                        if (retrofitUtils == null) {
                            retrofitUtils = RetrofitUtils()
                        }
                    }
                }
                return retrofitUtils!!
            }

        private var retrofit: Retrofit? = null

        @Synchronized
        fun getRetrofit(ShowUrl: String): Retrofit {
            retrofit = Retrofit.Builder()
                .baseUrl(ShowUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())  //转换器
                .build()
            return retrofit!!
        }
    }

    //定制一个类型 为了调用者代码简洁
    val default: RequestService
        get() {
            val retrofit = getRetrofit(BASE_URL)
            return retrofit.create(RequestService::class.java)
        }

    // 定制的request类型
    fun <T> getApiService(ShowUrl: String, service: Class<T>): T {
        val retrofit = getRetrofit(ShowUrl)
        return retrofit.create(service)
    }

}
