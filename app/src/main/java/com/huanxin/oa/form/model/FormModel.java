package com.huanxin.oa.form.model;

import java.util.List;

public class FormModel {
    private boolean isEmpty;//是否为空
    private int row;//行
    private int col;//列
    private String title;//内容
    private int spanWidth = 1;//合并行数
    private int spanHeight = 1;//合并列数
    private boolean isParent = false;

    private int pid=0;
    private int id;

    List<FormModel> child;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<FormModel> getChild() {
        return child;
    }

    public void setChild(List<FormModel> child) {
        this.child = child;
    }

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

    public int getCol() {
        return col;
    }

    public void setCol(int column) {
        this.col = column;
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
