package com.huanxin.oa.model.review.detial;

import java.util.List;

public class ReviewDetail {

    /**
     * success : true
     * message :
     */
    private boolean success;
    private String message;
    private List<TablesBean> tables;

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

    public List<TablesBean> getTables() {
        return tables;
    }

    public void setTables(List<TablesBean> tables) {
        this.tables = tables;
    }


}
