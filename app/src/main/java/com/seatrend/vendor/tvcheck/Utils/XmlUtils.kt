package com.seatrend.vendor.tvcheck.Utils

import java.io.UnsupportedEncodingException
import java.lang.Exception
import java.net.URLEncoder

/**
 * Created by ly on 2020/3/31 13:06
 */
class XmlUtils {

    companion object {

         fun Map2Xml_Request(map: Map<String, String>): Any? {
            var flagName = "QueryCondition"
            val sb = StringBuffer()
            sb.append("<?xml version=\"1.0\" encoding=\"GBK\"?><root>")
            sb.append("<$flagName>")
            mapToXML(map, sb)
            sb.append("</$flagName></root>")
            try {
                return sb.toString()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return null
        }

        fun mapToXML(map: Map<*, *>, sb: StringBuffer) {
            val set = map.keys
            val it = set.iterator()
            while (it.hasNext()) {
                val key = it.next() as String
                var value: Any? = map[key]
                if (null == value)
                    value = ""
                if (value.javaClass.name == "java.util.ArrayList") {
                    val list = map[key] as java.util.ArrayList<*>
                    sb.append("<$key>")
                    for (i in list.indices) {
                        val hm = list[i] as java.util.HashMap<*, *>
                        mapToXML(hm, sb)
                    }
                    sb.append("</$key>")
                } else {
                    if (value is java.util.HashMap<*, *>) {
                        sb.append("<$key>")
                        mapToXML((value as java.util.HashMap<*, *>?)!!, sb)
                        sb.append("</$key>")
                    } else {
                        try {
                            sb.append(
                                "<" + key + ">" + URLEncoder.encode(
                                    StringUtils.isNull(value),
                                    "utf-8"
                                ) + "</" + key + ">"
                            )
                        } catch (e: UnsupportedEncodingException) {
                            e.printStackTrace()
                        }

                    }
                }

            }
        }
    }
}