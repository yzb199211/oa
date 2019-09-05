package com.huanxin.oa.model.main;

public class FormChildMenusBean {
    /**
     * iMenuID : 249
     * sMenuName : 销售订单列表
     * iSerial : 0
     * iFormID : 10000021
     * sIcon :
     * sAppStyle : 树形表格
     * iShowChart : 1
     * iIsUnion : 0
     */

    private int iMenuID;
    private String sMenuName;
    private int iSerial;
    private int iFormID;
    private String sIcon;
    private String sAppStyle;
    private int iShowChart;
    private int iIsUnion;
    private int iPageGetData;


    public boolean isTitle() {
        return isTitle;
    }

    public void setTitle(boolean title) {
        isTitle = title;
    }

    private boolean isTitle = false;

    public int getIMenuID() {
        return iMenuID;
    }

    public void setIMenuID(int iMenuID) {
        this.iMenuID = iMenuID;
    }

    public String getSMenuName() {
        return sMenuName;
    }

    public void setSMenuName(String sMenuName) {
        this.sMenuName = sMenuName;
    }

    public int getISerial() {
        return iSerial;
    }

    public void setISerial(int iSerial) {
        this.iSerial = iSerial;
    }

    public int getIFormID() {
        return iFormID;
    }

    public void setIFormID(int iFormID) {
        this.iFormID = iFormID;
    }

    public String getSIcon() {
        return sIcon;
    }

    public void setSIcon(String sIcon) {
        this.sIcon = sIcon;
    }

    public String getSAppStyle() {
        return sAppStyle;
    }

    public void setSAppStyle(String sAppStyle) {
        this.sAppStyle = sAppStyle;
    }

    public int getIShowChart() {
        return iShowChart;
    }

    public void setIShowChart(int iShowChart) {
        this.iShowChart = iShowChart;
    }

    public int getIIsUnion() {
        return iIsUnion;
    }

    public void setIIsUnion(int iIsUnion) {
        this.iIsUnion = iIsUnion;
    }

    public int getiPageGetData() {
        return iPageGetData;
    }

    public void setiPageGetData(int iPageGetData) {
        this.iPageGetData = iPageGetData;
    }
}