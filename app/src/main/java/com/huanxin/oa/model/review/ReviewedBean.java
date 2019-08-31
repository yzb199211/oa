package com.huanxin.oa.model.review;

import java.util.List;

public class ReviewedBean {
    private int iRecNo;
    private int iFormid;
    private String sAppContent;
    private int iBillRecNo;
    private String sBillType;
    private String dDealDate;

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

    public String getSBillType() {
        return sBillType;
    }

    public void setSBillType(String sBillType) {
        this.sBillType = sBillType;
    }

    public String getDDealDate() {
        return dDealDate;
    }

    public void setDDealDate(String dDealDate) {
        this.dDealDate = dDealDate;
    }
}



