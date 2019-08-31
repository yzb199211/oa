package com.huanxin.oa.model.message;

import java.util.List;

public class MessageBean {

    /**
     * success : true
     * message :
     * tables : {"RemindList":[{"iRecNo":"10","sTitle":"你有25个订单未提交","sFilters":"iOrderType=0 and (sUserID='master' or iStatus>1)","sAppMenuID":"","sImageUrl":""},{"iRecNo":"11","sTitle":"你有25个订单未提交","sFilters":"iOrderType=0 and (sUserID='master' or iStatus>1)","sAppMenuID":"","sImageUrl":""},{"iRecNo":"12","sTitle":"你有25个订单未提交","sFilters":"iOrderType=0 and (sUserID='master' or iStatus>1)","sAppMenuID":"","sImageUrl":""},{"iRecNo":"13","sTitle":"你有25个订单未提交","sFilters":"iOrderType=0 and (sUserID='master' or iStatus>1)","sAppMenuID":"249","sImageUrl":"2019-05/6670_SysRemind_13___ExtFile1_20190523092329_4182_QQ截图20170112094900.png"}]}
     * data : {}
     */

    private boolean success;
    private String message;
    private TablesBean tables;
    private DataBean data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TablesBean getTables() {
        return tables;
    }

    public void setTables(TablesBean tables) {
        this.tables = tables;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }



    public static class DataBean {
    }
}
