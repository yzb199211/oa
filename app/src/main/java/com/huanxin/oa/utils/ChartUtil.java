package com.huanxin.oa.utils;

import org.json.JSONObject;

public class ChartUtil {
    public static String initString(String string) throws Exception {
        JSONObject jsonObject = new JSONObject(string);
        return jsonObject.optString("dataset");
    }
}
