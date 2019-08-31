package com.huanxin.oa.main.utils;

public class MineItem {
    int id;
    int marginTop;
    int leftSrc;
    boolean leftVisiable = true;
    float leftWidth;
    float leftHeight;
    float leftPadding;
    String centerText = "";
    int centerTextColor;
    float centerTextSize;
    float centerWidth;
    boolean centerTextIsBlod = false;
    boolean rightVisible = false;
    String centerContentText = "";
    int centerContentTextColor;
    float centerContentTextSize;
    float centerContentTextWidth;
    boolean centerContentIsBlod = false;

    public MineItem(int id, int marginTop, int leftSrc, boolean leftVisiable, String centerText, boolean centerTextIsBlod, boolean rightVisible, String centerContentText, boolean centerContentIsBlod) {
        this.id = id;
        this.marginTop = marginTop;
        this.leftSrc = leftSrc;
        this.leftVisiable = leftVisiable;
        this.centerText = centerText;
        this.centerTextIsBlod = centerTextIsBlod;
        this.rightVisible = rightVisible;
        this.centerContentText = centerContentText;
        this.centerContentIsBlod = centerContentIsBlod;
    }

    public MineItem(int id, int marginTop, int leftSrc, boolean leftVisiable, float leftWidth, float leftHeight, float leftPadding, String centerText, int centerTextColor, float centerTextSize, float centerWidth, boolean centerTextIsBlod, boolean rightVisible, String centerContentText, int centerContentTextColor, float centerContentTextSize, float centerContentTextWidth, boolean centerContentIsBlod) {
        this.id = id;
        this.marginTop = marginTop;
        this.leftSrc = leftSrc;
        this.leftVisiable = leftVisiable;
        this.leftWidth = leftWidth;
        this.leftHeight = leftHeight;
        this.leftPadding = leftPadding;
        this.centerText = centerText;
        this.centerTextColor = centerTextColor;
        this.centerTextSize = centerTextSize;
        this.centerWidth = centerWidth;
        this.centerTextIsBlod = centerTextIsBlod;
        this.rightVisible = rightVisible;
        this.centerContentText = centerContentText;
        this.centerContentTextColor = centerContentTextColor;
        this.centerContentTextSize = centerContentTextSize;
        this.centerContentTextWidth = centerContentTextWidth;
        this.centerContentIsBlod = centerContentIsBlod;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMarginTop() {
        return marginTop;
    }

    public void setMarginTop(int marginTop) {
        this.marginTop = marginTop;
    }

    public int getLeftSrc() {
        return leftSrc;
    }

    public void setLeftSrc(int leftSrc) {
        this.leftSrc = leftSrc;
    }

    public boolean isLeftVisiable() {
        return leftVisiable;
    }

    public void setLeftVisiable(boolean leftVisiable) {
        this.leftVisiable = leftVisiable;
    }

    public float getLeftWidth() {
        return leftWidth;
    }

    public void setLeftWidth(float leftWidth) {
        this.leftWidth = leftWidth;
    }

    public float getLeftHeight() {
        return leftHeight;
    }

    public void setLeftHeight(float leftHeight) {
        this.leftHeight = leftHeight;
    }

    public float getLeftPadding() {
        return leftPadding;
    }

    public void setLeftPadding(float leftPadding) {
        this.leftPadding = leftPadding;
    }

    public String getCenterText() {
        return centerText;
    }

    public void setCenterText(String centerText) {
        this.centerText = centerText;
    }

    public int getCenterTextColor() {
        return centerTextColor;
    }

    public void setCenterTextColor(int centerTextColor) {
        this.centerTextColor = centerTextColor;
    }

    public float getCenterTextSize() {
        return centerTextSize;
    }

    public void setCenterTextSize(float centerTextSize) {
        this.centerTextSize = centerTextSize;
    }

    public float getCenterWidth() {
        return centerWidth;
    }

    public void setCenterWidth(float centerWidth) {
        this.centerWidth = centerWidth;
    }

    public boolean isCenterTextIsBlod() {
        return centerTextIsBlod;
    }

    public void setCenterTextIsBlod(boolean centerTextIsBlod) {
        this.centerTextIsBlod = centerTextIsBlod;
    }

    public boolean isRightVisible() {
        return rightVisible;
    }

    public void setRightVisible(boolean rightVisible) {
        this.rightVisible = rightVisible;
    }

    public String getCenterContentText() {
        return centerContentText;
    }

    public void setCenterContentText(String centerContentText) {
        this.centerContentText = centerContentText;
    }

    public int getCenterContentTextColor() {
        return centerContentTextColor;
    }

    public void setCenterContentTextColor(int centerContentTextColor) {
        this.centerContentTextColor = centerContentTextColor;
    }

    public float getCenterContentTextSize() {
        return centerContentTextSize;
    }

    public void setCenterContentTextSize(float centerContentTextSize) {
        this.centerContentTextSize = centerContentTextSize;
    }

    public float getCenterContentTextWidth() {
        return centerContentTextWidth;
    }

    public void setCenterContentTextWidth(float centerContentTextWidth) {
        this.centerContentTextWidth = centerContentTextWidth;
    }

    public boolean isCenterContentIsBlod() {
        return centerContentIsBlod;
    }

    public void setCenterContentIsBlod(boolean centerContentIsBlod) {
        this.centerContentIsBlod = centerContentIsBlod;
    }
}
