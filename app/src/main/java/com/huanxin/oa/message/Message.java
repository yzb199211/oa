package com.huanxin.oa.message;

public class Message {
    String strTitle, strDate, strContent;
    int img;

    public Message(String strTitle, String strDate, String strContent, int img) {
        this.strTitle = strTitle;
        this.strDate = strDate;
        this.strContent = strContent;
        this.img = img;
    }

    public String getStrTitle() {
        return strTitle;
    }

    public void setStrTitle(String strTitle) {
        this.strTitle = strTitle;
    }

    public String getStrDate() {
        return strDate;
    }

    public void setStrDate(String strDate) {
        this.strDate = strDate;
    }

    public String getStrContent() {
        return strContent;
    }

    public void setStrContent(String strContent) {
        this.strContent = strContent;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
