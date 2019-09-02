package com.huanxin.oa.utils;

import com.huanxin.oa.form.model.FormBean;

import java.util.List;

public class LookupDataUtil {
    public static String getConditionData(List<FormBean.ReportConditionBean> list) {
        String data = "";
        for (int i = 0; i < list.size(); i++) {
            if (StringUtil.isNotEmpty(list.get(i).getSelectValue())) {
                if (StringUtil.isNotEmpty(data))
                    data = data + " and ";
                data = data + list.get(i).getSFieldName();
                data = data + " ";
                data = data + list.get(i).getSOpTion().replaceAll("%", "");
                data = data + " ";
                data = data + getValue(list.get(i).getSOpTion().toLowerCase(), list.get(i).getSFieldType().toUpperCase(), list.get(i).getSelectValue());
            }
        }

        return data;
    }


    public static String getValue(String option, String type, String data) {
        String value = "";
        switch (type) {
            case "S":
                value = isLike(option, data);
                break;
            case "D":
                value = isLike(option, data);
                break;
            case "DT":
                value = isLike(option, data);
                break;
            case "F":
                value = "("+data+")";
                break;
            case "B":
                value ="(" + data +")";
                break;
            default:
                value = isLike(option, data);
                break;
        }
        return value;
    }

    public static String isLike(String option, String data) {
        String value = "";
        switch (option) {
            case "%like":
                value = "(" + "\'" + "%" + data + "\'" + ")";
                break;
            case "like%":
                value = "(" + "\'" + data + "%" + "\'" + ")";
                break;
            case "%like%":
                value = "(" + "\'" + "%" + data + "%" + "\'" + ")";
                break;
            case "like":
                value = "(" + "\'" + "%" + data + "%" + "\'" + ")";
                break;
            default:
                value = "(" + "\'" + data + "\'" + ")";
                break;
        }
        return value;
    }
}
