package com.seatrend.vendor.tvcheck

import android.content.Intent
import com.seatrend.vendor.tvcheck.thread.ThreadPoolManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by ly on 2020/3/26 13:51
 */
class LoginActivity : BaseActivity() {
    override fun initView() {

//        ThreadPoolManager.instance.createSchedulePool(Runnable {
//            startActivity(Intent(this, MainActivity::class.java))
//            finish()
//        }, 2000)

        GlobalScope.launch(Dispatchers.Default) {
            delay(2000)
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_login
    }
}