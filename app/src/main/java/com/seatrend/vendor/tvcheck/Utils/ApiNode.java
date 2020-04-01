package com.seatrend.vendor.tvcheck.Utils;

/**
 * Created by ly on 2020/3/30 14:05
 */
import android.util.Log;
import java.util.Map;
/**
 * Created by Frank on 2016/12/9.
 * 拼接http请求的头文件
 */
public class ApiNode {

    public static   final String QUERYCONDITION = "QueryCondition";

    // 转义字符-> <xxx>
    public static String toStart2(String name) {
        return "<" + name + ">";
    }
    // 转义字符-> </xxx>
    public static String toEnd2(String name) {
        return "</" + name + ">";
    }
    // 正常字符-> <xxx>
    public static String toStart(String name) {
        return "<" + name + ">";
    }
    // 正常字符-> </xxx>
    public static String toEnd(String name) {
        return "</" + name + ">";
    }

    public static String getParameter(String namespace, Map<String, String> map) {
        StringBuffer sbf = new StringBuffer();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sbf.append(ApiNode.toStart(entry.getKey()));
            sbf.append(entry.getValue());
            sbf.append(ApiNode.toEnd(entry.getKey()));
        }
        String str = "<?xml version=\"1.0\" encoding=\"GBK\"?>" +
                "  <root>" +
                "    <" + namespace + ">" + sbf.toString() +
                "    </" + namespace + ">" +
                "  </root>";
        return str;
    }
}
