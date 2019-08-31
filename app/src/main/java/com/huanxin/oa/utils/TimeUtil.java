package com.huanxin.oa.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
    /**
     * 获取时间格式，type=1时间格式为yyyy-MM-dd HH:mm
     *
     * @param date
     * @param type
     * @return
     */
    public static String getDate(String date, int type) throws Exception {
        if (type == 1) {
            date = date.substring(0, 16);
            date = date.replace("T", " ");
            return date;
        } else
            return date;
    }


    public static String getTime(Date date) {//可根据需要自行截取数据显示
//        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    public static String getTime() {
        SimpleDateFormat simpleDateFormat;
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
//获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    public static String getDate(String string) {
        return string.substring(0, 10);
    }
}
