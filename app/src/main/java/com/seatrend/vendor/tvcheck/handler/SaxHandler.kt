package com.seatrend.vendor.tvcheck.handler

import com.seatrend.vendor.tvcheck.entity.Data
import org.xml.sax.Attributes
import org.xml.sax.helpers.DefaultHandler
import android.R.attr.start
import android.util.Log


/**
 * Created by ly on 2020/3/31 13:39
 */
class SaxHandler : DefaultHandler() {


    var flag = ""

    private var mHG_Data = ArrayList<Data>()   //合格集合

    private var mBHG_Data = ArrayList<Data>()  // 不合格集合

    private var code = ""

    fun getHgData(): ArrayList<Data> {
        return mHG_Data
    }

    fun getBHgData(): ArrayList<Data> {
        return mBHG_Data
    }

    fun getCode(): String {
        return code
    }

    override fun startDocument() {
        super.startDocument()
    }

    override fun endDocument() {
        super.endDocument()
    }

    override fun startElement(uri: String?, localName: String?, qName: String?, attributes: Attributes?) {
        super.startElement(uri, localName, qName, attributes)

        Log.d("lylog","1")
        if ("code" == localName) {
            flag = localName
        }

    }

    override fun endElement(uri: String?, localName: String?, qName: String?) {
        super.endElement(uri, localName, qName)

        Log.d("lylog","3")
        if ("code" == localName) {
            flag = ""
        }
    }

    override fun characters(ch: CharArray?, start: Int, length: Int) {
        super.characters(ch, start, length)
        Log.d("lylog","2")
        val data = String(ch!!, start, length)
        if ("code" == flag) {
            code = data
        }

    }
}