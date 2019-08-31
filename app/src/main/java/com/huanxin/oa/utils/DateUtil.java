package com.huanxin.oa.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public final String getFormatDateString(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date());
    }
}
