package com.huanxin.oa.form.model;

public class FormModel {
    private boolean isEmpty;//是否为空
    private int row;//行
    private int column;//列
    private String title;//内容
    private int spanWidth = 1;//合并行数
    private int spanHeight;//合并列数
    private boolean isParent;

    public boolean isParent() {
        return isParent;
    }

    public void setParent(boolean parent) {
        isParent = parent;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSpanWidth() {
        return spanWidth;
    }

    public void setSpanWidth(int spanWidth) {
        this.spanWidth = spanWidth;
    }

    public int getSpanHeight() {
        return spanHeight;
    }

    public void setSpanHeight(int spanHeight) {
        this.spanHeight = spanHeight;
    }

    public void addSpanHeight() {
        this.spanHeight = this.spanHeight + 1;
    }
}
