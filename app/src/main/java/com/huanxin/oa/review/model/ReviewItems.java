package com.huanxin.oa.review.model;

public class ReviewItems {
    String imgSrc;
    String text;
    int textColor;
    int textSize;
    boolean textBold;

    public ReviewItems() {

    }

    public ReviewItems(String imgSrc, String text, int textColor, int textSize, boolean textBold) {
        this.imgSrc = imgSrc;
        this.text = text;
        this.textColor = textColor;
        this.textSize = textSize;
        this.textBold = textBold;
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
}
