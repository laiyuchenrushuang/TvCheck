package com.seatrend.vendor.tvcheck.fragment

import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.seatrend.vendor.tvcheck.R
import com.seatrend.vendor.tvcheck.Utils.CalenderUtils
import com.seatrend.vendor.tvcheck.adpter.BhgAdapter
import com.seatrend.vendor.tvcheck.adpter.HgAdapter
import com.seatrend.vendor.tvcheck.entity.Data
import com.seatrend.vendor.tvcheck.thread.ThreadPoolManager
import kotlinx.android.synthetic.main.fragment_check.*
import kotlinx.coroutines.*

/**
 * Created by ly on 2020/3/26 10:45
 */
class CheckFragment : BaseFragment() {

    var adapterHg :HgAdapter ?=null
    var adapterBhg :BhgAdapter ?=null

    override fun getLayoutView(inflater: LayoutInflater?, container: ViewGroup?): View {
        return inflater!!.inflate(R.layout.fragment_check, container, false)
    }

    override fun initView() {
        initRecycleView()
        initData()
    }

    private fun initRecycleView() {

        //testData
        var mData = ArrayList<Data>()
        var data2 = Data()
        data2.hphm= "川 A 125421"
        data2.aj= "合格"
        data2.hj= "不合格"

        var data1 = Data()
        data1.hphm= "川 A 125421"
        data1.aj= "合格"
        data1.hj= "不合格"

        var data3 = Data()
        data3.hphm= "川 A 125421"
        data3.aj= "合格"
        data3.hj= "不合格"

        mData.add(data1)
        mData.add(data2)
        mData.add(data3)
        mData.add(data1)
        mData.add(data2)
        mData.add(data2)
        mData.add(data2)
        mData.add(data2)
        mData.add(data2)



        list_hg!!.layoutManager = LinearLayoutManager(context)
        adapterHg = HgAdapter(context, mData)
        list_hg.adapter = adapterHg



        list_bhg!!.layoutManager = LinearLayoutManager(context)
        adapterBhg = BhgAdapter(context, mData)
        list_bhg.adapter = adapterBhg

    }

    private fun initData() {
        //获取时间
        getTime()
    }

    private fun getTime() {
        GlobalScope.launch(Dispatchers.Default) {
            while (true) {
                postDelay()
                runBlocking {
                    withContext(Dispatchers.Main) { refreshTime() }
                }
            }
        }
    }

    private fun refreshTime() {
        tv_time.text = CalenderUtils.getTime()
    }

    suspend fun postDelay() {
        delay(1000)
    }


}