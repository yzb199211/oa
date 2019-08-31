package com.huanxin.oa.review.model;

import java.util.List;

public class ReviewItem {
    int id;
    int formId;
    int billId;
    String imgSrc;
    String text;
    String content;
    int textColor;
    int textSize;
    boolean textBold;
    List<ReviewItems> list;

    public ReviewItem() {
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public int getBillId() {
        return billId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFormId() {
        return formId;
    }

    public void setFormId(int formId) {
        this.formId = formId;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public boolean isTextBold() {
        return textBold;
    }

    public void setTextBold(boolean textBold) {
        this.textBold = textBold;
    }

    public List<ReviewItems> getList() {
        return list;
    }

    public void setLists(List<ReviewItems> list) {
        list.clear();
        this.list = list;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
