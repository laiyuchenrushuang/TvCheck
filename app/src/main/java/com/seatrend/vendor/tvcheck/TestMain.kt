package com.seatrend.vendor.tvcheck

import com.example.vendor.nextapp.http.RequestService
import com.seatrend.vendor.tvcheck.Utils.ApiNode
import com.seatrend.vendor.tvcheck.Utils.RetrofitUtils
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

/**
 * Created by ly on 2020/3/30 14:08
 */
class TestMain {

    companion object{
        @JvmStatic
        fun main(args: Array<String>) {

            val alphas = "QWERTYUIOPASDFGHJKLZXCVBNM" // 26个字母
            val nulber = "1234567890" //  10个数字
            val carID = "川 A "

            System.out.println(carID+alphas.random()+alphas.random()+alphas.random()+alphas.random()+nulber.random())

        }
    }
}