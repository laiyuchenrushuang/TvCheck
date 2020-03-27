package com.example.vendor.nextapp.http.interceptor

import android.util.Log
import com.google.gson.Gson
import com.seatrend.vendor.tvcheck.Utils.AESUtils
import com.seatrend.vendor.tvcheck.Utils.FastJsonUtils
import com.seatrend.vendor.tvcheck.Utils.RetrofitUtils
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.RequestBody
import okhttp3.Response
import okio.Buffer
import org.json.JSONObject
import java.net.URLDecoder
import java.nio.charset.Charset

/**
 * Created by ly on 2020/3/25 13:22
 *
 *  输入流加密拦截器（AES）
 */
class RequestEncryptInterceptor : Interceptor {

    @Synchronized
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        var charset = Charset.forName("UTF-8")
        val method = request.method().toLowerCase().trim()

        val url = request.url()
        /*本次请求的接口地址*/
        val apiPath = "${url.scheme()}://${url.host()}:${url.port()}${url.encodedPath()}".trim()
        /*服务端的接口地址*/
        val serverPath = "${url.scheme()}://${url.host()}/".trim()

        /*如果请求的不是服务端的接口，不加密*/
        if (!serverPath.startsWith(RetrofitUtils.BASE_HEAD + RetrofitUtils.BASE_IP)) {
            Log.i(TAG, "OK---> " + TYPE.NO_EN)
            return chain.proceed(request)
        }

        /*如果请求方式是Get或者Delete，此时请求数据是拼接在请求地址后面的*/
        if (method == REQUEST.GET || method == REQUEST.DELETE) {

            /*如果有请求数据 则加密*/
            if (url.encodedQuery() != null) {
                try {
                    val queryparamNames = request.url().encodedQuery()
                    //这种方案正解哈，为了满足自己测试需要 重新处理
//                        val encryptqueryparamNames = AESUtils.encrypt(queryparamNames)
//                        //拼接加密后的url，参数字段自己跟后台商量，这里我用param，后台拿到数据先对param进行解密，解密后的数据就是请求的数据
//                        val newUrl = "$apiPath?param=$encryptqueryparamNames"
//                        //构建新的请求
//                        request = request.newBuilder().url(newUrl).build()

                    // 为了测试进行处理 start

                    if (queryparamNames != null && queryparamNames.contains("&")) { //复合数据类型
                        //分层加密
                        val sb = StringBuffer()
                        val StringList = queryparamNames.split("&")

                        for (indexStr in StringList) {

                            val strSmall = indexStr.split("=")
                            if (StringList.last() != indexStr) {
                                sb.append(strSmall[0] + "=" + AESUtils.encrypt(strSmall[1]) + "&")
                            } else {
                                sb.append(strSmall[0] + "=" + AESUtils.encrypt(strSmall[1]))
                            }
                        }
                        val newUrl = "$apiPath?${sb}"

                        Log.i(TAG, "OK---> " + TYPE.EN_ONE)
                        request = request.newBuilder().url(newUrl).build()
                    } else {
                        val strSmall = queryparamNames!!.split("=")
                        val newUrl = "$apiPath?${strSmall[0] + "=" + AESUtils.encrypt(strSmall[1])}"

                        Log.i(TAG, "OK---> " + TYPE.EN_ONE)
                        request = request.newBuilder().url(newUrl).build()
                    }

                    // 为了测试进行处理 end


                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.i(TAG, "ERROR---> " + TYPE.EN_ONE)
                    return chain.proceed(request)
                }
            }
        } else {
            //不是Get和Delete请求时，则请求数据在请求体中
            val requestBody = request.body()

            /*判断请求体是否为空  不为空则执行以下操作*/
            if (requestBody != null) {
                val contentType = requestBody.contentType()
                if (contentType != null) {
                    charset = contentType.charset(charset)
                    /*如果是二进制上传  则不进行加密*/
                    if (contentType.type().toLowerCase() == "multipart") {
                        Log.i(TAG, "OK---> " + TYPE.EN_TWO_2)
                        return chain.proceed(request)
                    }
                }

                /*获取请求的数据*/
                try {
                    val buffer = Buffer()
                    requestBody.writeTo(buffer)
                    val requestData = URLDecoder.decode(buffer.readString(charset).trim(), "utf-8")

                    //原本正解方案
//                    val encryptData = AESUtils.encrypt(requestData)
//                    /*构建新的请求体*/
//                    val newRequestBody = RequestBody.create(contentType, encryptData)
//
//                    /*构建新的requestBuilder*/
//                    val newRequestBuilder = request.newBuilder()
//                    //根据请求方式构建相应的请求
//                    when (method) {
//                        REQUEST.POST -> newRequestBuilder.post(newRequestBody)
//                        REQUEST.PUT -> newRequestBuilder.put(newRequestBody)
//                    }
//                    Log.i(TAG, "OK---> " + TYPE.EN_TWO)
//                    request = newRequestBuilder.build()


                    //为了测试 start

                    Log.d(TAG, " request  body =  " + requestData)
                    val mapList = FastJsonUtils.jsonToMap(requestData)

                    val builder = FormBody.Builder()

                    for (map in mapList) {
                        builder.add(map.key.toString().trim(), AESUtils.encrypt(map.value.toString().trim()))
                    }
                    val newRequestBody = builder.build()
                    val newRequestBuilder = request.newBuilder()

                    Log.d(TAG, " request  body =  " + getbodyStr(newRequestBody))
                    when (method) {
                        REQUEST.POST -> newRequestBuilder.post(newRequestBody)
                        REQUEST.PUT -> newRequestBuilder.put(newRequestBody)
                    }
                    request = newRequestBuilder.build()
                    //为了测试 end
                } catch (e: Exception) {
                    return chain.proceed(request)
                }
            }
        }
        return chain.proceed(request)
    }

    fun getbodyStr( b:RequestBody):String{
        val buffer = Buffer()
        b.writeTo(buffer)
        return URLDecoder.decode(buffer.readString(Charset.forName("UTF-8")).trim(), "utf-8")
    }

    companion object {
        private const val TAG = "${RetrofitUtils.MY_TAG} REQ"
    }


    enum class TYPE {
        NO_EN,  //无需加密
        EN_ONE, //get delete 不是请求体类型
        EN_TWO, // 请求体类型
        EN_TWO_2 //请求体二进制类型
    }

    internal class REQUEST {
        companion object {
            const val GET = "get"
            const val POST = "post"
            const val PUT = "put"
            const val DELETE = "delete"
        }
    }
}