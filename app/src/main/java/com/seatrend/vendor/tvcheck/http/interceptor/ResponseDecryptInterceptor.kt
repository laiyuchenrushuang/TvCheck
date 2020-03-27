package com.example.vendor.nextapp.http.interceptor

import android.util.Log
import com.seatrend.vendor.tvcheck.Utils.AESUtils
import com.seatrend.vendor.tvcheck.Utils.RetrofitUtils
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException
import java.nio.charset.Charset

/**
 * Created by ly on 2020/3/25 13:20
 *
 *   输出流拦截器（AES）
 */
class ResponseDecryptInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        //返回request
        val request = chain.request()
        //返回response
        var response = chain.proceed(request)
        //isSuccessful () ; 如果代码在[200..300]中，则返回true，这意味着请求已成功接收、理解和接受。
        if (response.isSuccessful) {
            //返回ResponseBody
            val responseBody = response.body()
            if (responseBody != null) {
                try {
                    //获取bodyString
                    val source = responseBody.source()
                    source.request(java.lang.Long.MAX_VALUE)
                    val buffer = source.buffer()
                    var charset: Charset? = Charset.forName("UTF-8")
                    val contentType = responseBody.contentType()
                    if (contentType != null) {
                        charset = contentType.charset(charset)
                    }
                    val bodyString = buffer.clone().readString(charset!!)
                    //我这里是AES解码  具体情况自己定义
                    //AES解码
                    val responseData = AESUtils.decrypt(bodyString)!!
                    //生成新的ResponseBody
                    val newResponseBody = ResponseBody.create(contentType, responseData.trim())
                    //response
                    response = response.newBuilder().body(newResponseBody).build()
                    Log.i(TAG, "<--- ${RESPONSE.DE_OK} $responseData")

                } catch (e: IOException) {
                    //如果发生异常直接返回
                    e.printStackTrace()
                    Log.i(TAG, "<--- "+RESPONSE.DE_ERROR1)
                    return response
                }

            } else {
                Log.i(TAG, "<--- "+RESPONSE.DE_ERROR2)
            }
        }
        return response
    }

    companion object {
        private const val TAG = "${RetrofitUtils.MY_TAG} RES"
    }

    enum class RESPONSE{
        DE_OK,  //成功
        DE_ERROR1,// 错误 IOEX
        DE_ERROR2 //错误 body is null
    }
}