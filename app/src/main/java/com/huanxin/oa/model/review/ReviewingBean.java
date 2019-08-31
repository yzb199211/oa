package com.huanxin.oa.model.review;

public class ReviewingBean {
    /**
     * iRecNo : 726
     * iFormid : 2002
     * sAppContent : 【撤销审批申请,123434】客户订单:iRecNo 940
     * iBillRecNo : 940
     * iType : 3
     * sBillType : 客户订单
     * "dInputDate":"2017-11-09T10:05:08"
     * "sIcon":"icon-order"
     */

    private int iRecNo;
    private int iFormid;
    private String sAppContent;
    private int iBillRecNo;
    private int iType;
    private String sBillType;
    private String sIcon;
    private String dInputDate;

    public String getdInputDate() {
        return dInputDate;
    }

    public void setdInputDate(String dInputDate) {
        this.dInputDate = dInputDate;
    }

    public String getsIcon() {
        return sIcon;
    }

    public void setsIcon(String sIcon) {
        this.sIcon = sIcon;
    }

    public int getIRecNo() {
        return iRecNo;
    }

    public void setIRecNo(int iRecNo) {
        this.iRecNo = iRecNo;
    }

    public int getIFormid() {
        return iFormid;
    }

    public void setIFormid(int iFormid) {
        this.iFormid = iFormid;
    }

    public String getSAppContent() {
        return sAppContent;
    }

    public void setSAppContent(String sAppContent) {
        this.sAppContent = sAppContent;
    }

    public int getIBillRecNo() {
        return iBillRecNo;
    }

    public void setIBillRecNo(int iBillRecNo) {
        this.iBillRecNo = iBillRecNo;
    }

    public int getIType() {
        return iType;
    }

    public void setIType(int iType) {
        this.iType = iType;
    }

    public String getSBillType() {
        return sBillType;
    }

    public void setSBillType(String sBillType) {
        this.sBillType = sBillType;
    }
}
