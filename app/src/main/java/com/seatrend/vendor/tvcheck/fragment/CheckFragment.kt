package com.seatrend.vendor.tvcheck.fragment

import android.support.v4.app.ActivityCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.seatrend.vendor.tvcheck.R
import com.seatrend.vendor.tvcheck.Utils.*
import com.seatrend.vendor.tvcheck.adpter.BhgAdapter
import com.seatrend.vendor.tvcheck.adpter.HgAdapter
import com.seatrend.vendor.tvcheck.entity.Data
import kotlinx.android.synthetic.main.fragment_check.*
import kotlinx.coroutines.*
import org.ksoap2.SoapEnvelope
import org.ksoap2.serialization.SoapObject
import org.ksoap2.serialization.SoapSerializationEnvelope
import org.ksoap2.transport.HttpTransportSE
import java.lang.Exception
import java.net.URLDecoder
import android.util.Xml
import com.seatrend.vendor.tvcheck.thread.ThreadPoolManager
import java.io.StringReader
import org.xmlpull.v1.XmlPullParser
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap




/**
 * Created by ly on 2020/3/26 10:45
 */
class CheckFragment : BaseFragment() {

    var adapterHg: HgAdapter? = null
    var adapterBhg: BhgAdapter? = null

    val RESULT_OK = "1" // 合格为1

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
        var mData2 = ArrayList<Data>()

        testData(mData)

        testData1(mData2)
//
//        showLog(mData.toString())
//        showLog(mData2.toString())


        list_hg!!.layoutManager = LinearLayoutManager(context)
        adapterHg = HgAdapter(context, mData)
        list_hg.adapter = adapterHg




        list_bhg!!.layoutManager = LinearLayoutManager(context)
        adapterBhg = BhgAdapter(context, mData2)
        list_bhg.adapter = adapterBhg


        var  hg_i = 0
        var  bhg_i = 0
        ThreadPoolManager.instance.createSchedulePool(Runnable {

                if(hg_i  < mData.size){
                    GlobalScope.launch (Dispatchers.Main){

                        var l_hg =  list_hg.layoutManager as LinearLayoutManager
                        l_hg.scrollToPositionWithOffset(hg_i,5)
                        hg_i += 5
                    }

                }else{
                    hg_i=0
                }
                if(bhg_i  < mData2.size){
                    GlobalScope.launch (Dispatchers.Main){

                        var l_bhg =  list_bhg.layoutManager as LinearLayoutManager
                        l_bhg.scrollToPositionWithOffset(bhg_i,5)
                        bhg_i += 5
                    }

                }else{
                    bhg_i=0
                }

        },0,5000)




        GlobalScope.launch(Dispatchers.Default) {
//            pb_1.visibility = View.VISIBLE
//            nextSoap()
        }
    }

    private fun testData(mData: ArrayList<Data>) {

        for(index in 0 until  50){
            var data2 = Data()
            data2.hphm= getRandom()
            data2.aj= "1"
            data2.hj= "1"
            mData.add(data2)
        }
    }

    private fun testData1(mData: ArrayList<Data>) {

        for(index in 0 until  50){
            val hgstr = "12"
            var data2 = Data()
            data2.hphm= getRandom()
            data2.aj= ""+hgstr.random()
            data2.hj= ""+hgstr.random()
            if("1" == data2.aj){
                data2.hj= "2"
            }

            mData.add(data2)
        }
    }

    fun getRandom() :String{
        val alphas = "QWERTYUIOPASDFGHJKLZXCVBNM1234567890" // 26个字母
        val nulber = "1234567890" //  10个数字
        val carID = "川 A"

        return carID+alphas.random()+alphas.random()+alphas.random()+alphas.random()+nulber.random()
    }

//    private fun getHt() {
//        var map = HashMap<String,String>()
//        map["jyjgbh"] = "5100000114"
//        var requestStr = ApiNode.getParameter(ApiNode.QUERYCONDITION,map)
////        val soapObject = SoapObject("http://endpoint.webservice.pda.seatrend.com","queryObjectOut")
////        soapObject.addProperty("jkid", "18W07")
////        soapObject.addProperty("xtlb", "18")
////        soapObject.addProperty("jkxlh", "123")
////        soapObject.addProperty("cjsqbh", "")
////        soapObject.addProperty("dwjgdm","")
////        soapObject.addProperty("dwmc", "")
////        soapObject.addProperty("yhbz", "")
////        soapObject.addProperty("yhxm", "")
////        soapObject.addProperty("zdbs", "127.0.0.1")
////        soapObject.addProperty("QueryXmlDoc", Map2Xml_Request(map))
//
//
//        //http://11.1.1.73:8086/ProvinceService2/trffweb/services/TmriOutNewAccess?wsdl
//        RetrofitUtils.instance.getApiService("http://11.1.1.73:8086/",RequestService::class.java)
//            .getCall(requestStr).enqueue(object :Callback<ResponseBody>{
//                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                    showLog("ERROR")
//
//                }
//
//                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                    showLog(response.toString())
////                    var result = response.body().toString()
////                    result = URLDecoder.decode(result, "utf-8");
//
//                }
//
//            })
//
//
//
//        thread {
////            nextSoap()
//        }
//
//
////        soapObject.addProperty("xmlDoc", Map2XmlUtils.Map2Xml_Request(dataMap, getBaseMap(context), isQuery))
//    }

    private fun nextSoap() {
        try {
            var map = HashMap<String, String>()
            map["jyjgbh"] = "5100000114"
            val soapObject = SoapObject("http://endpoint.webservice.pda.seatrend.com", "queryObjectOut")
            soapObject.addProperty("jkid", "18W07")
            soapObject.addProperty("xtlb", "18")
            soapObject.addProperty("jkxlh", "123")
            soapObject.addProperty("cjsqbh", "")
            soapObject.addProperty("dwjgdm", "")
            soapObject.addProperty("dwmc", "")
            soapObject.addProperty("yhbz", "")
            soapObject.addProperty("yhxm", "")
            soapObject.addProperty("zdbs", "127.0.0.1")
            soapObject.addProperty("QueryXmlDoc", XmlUtils.Map2Xml_Request(map))

            val envelope = SoapSerializationEnvelope(SoapEnvelope.VER11)
            envelope.bodyOut = soapObject
            envelope.dotNet = false

            var ht = HttpTransportSE(
                "http://11.1.1.73:8086/ProvinceService2/trffweb/services/TmriOutNewAccess?wsdl",
                12 * 10 * 1000
            )
            ht.call("", envelope)
            val obj = envelope.response
            var result = obj.toString()

            result = URLDecoder.decode(result, "utf-8")

            showLog(" result =   $result")
            setdata(result)
        } catch (e: Exception) {
            showToast(resources.getString(R.string.error_network))
        }

    }

    private var mHG_Data = ArrayList<Data>()   //合格集合

    private var mBHG_Data = ArrayList<Data>()  // 不合格集合


    private fun setdata(result: String?) {


        val parser = Xml.newPullParser()
        parser.setInput( StringReader(result))

        var event = parser.eventType

        var hmhm =""
        var hjjl =""
        var ajjl =""

        while (event != XmlPullParser.END_DOCUMENT){
            val nodeName  = parser.name


            when(event){
                XmlPullParser.START_TAG->{
                    if("hphm" == nodeName){
                        hmhm = parser.nextText()
                    }else if("ajjl" == nodeName){
                        ajjl = parser.nextText()
                    }else if ("hjjl" == nodeName){
                        hjjl = parser.nextText()
                    }
                }

                XmlPullParser.END_TAG->{
                    if ("screen" == nodeName) {
                        val data = Data()
                        data.aj =ajjl
                        data.hj = hjjl
                        data.hphm = hmhm
                        showLog(data.toString())
                        if(ajjl == hjjl && ajjl == RESULT_OK){
                            mHG_Data.add(data)
                        }else{
                            mBHG_Data.add(data)
                        }
                    }
                }

            }
            event = parser.next()
        }

        GlobalScope.launch(Dispatchers.Main){
            pb_1.visibility = View.GONE
            adapterHg!!.setData(mHG_Data)
            adapterBhg!!.setData(mBHG_Data)
        }


    }

    private fun initData() {
        //获取时间
        getTime()
    }

    var mTimeCoroutine: Job? = null

    private fun getTime() {
        mTimeCoroutine = GlobalScope.launch(Dispatchers.Default) {
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

    override fun onDestroy() {
        super.onDestroy()
        mTimeCoroutine!!.cancel()
    }

}