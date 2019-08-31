package com.huanxin.oa.model.review;

import java.util.List;

public class ReviewBean {


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
