package com.huanxin.oa.model.review.detial;


public class OpinionBean {
    /**
     * sCheckIdeal : 已有其他人审批
     * sName : 管理员
     * messtype : 通过
     * dDealDate : 2019-04-26T15:25:14
     */

    private String sCheckIdeal;
    private String sName;
    private String messtype;
    private String dDealDate;

    public String getSCheckIdeal() {
        return sCheckIdeal;
    }

    public void setSCheckIdeal(String sCheckIdeal) {
        this.sCheckIdeal = sCheckIdeal;
    }

    public String getSName() {
        return sName;
    }

    public void setSName(String sName) {
        this.sName = sName;
    }

    public String getMesstype() {
        return messtype;
    }

    public void setMesstype(String messtype) {
        this.messtype = messtype;
    }

    public String getDDealDate() {
        return dDealDate;
    }

    public void setDDealDate(String dDealDate) {
        this.dDealDate = dDealDate;
    }
}