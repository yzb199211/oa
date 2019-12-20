package com.huanxin.oa.form.model;

import java.io.Serializable;
import java.util.List;

public class FormModel implements Serializable {
    private boolean isEmpty;//是否为空
    private int row;//行
    private int col;//列
    private String title;//内容
    private int spanWidth = 1;//合并行数
    private int spanHeight = 1;//合并列数
    private boolean isParent = false;
    private int total;
    private String totalTitle;

    private int pid = 0;
    private int id;

    List<FormModel> child;
    List<List<FormModel>> rowData;

    public List<List<FormModel>> getRowData() {
        return rowData;
    }

    public void setRowData(List<List<FormModel>> rowData) {
        this.rowData = rowData;
    }

    public FormModel() {
    }

    public FormModel(int row, int col, String title, int spanHeight, boolean isParent, int pid, int id) {
        this.row = row;
        this.col = col;
        this.title = title;
        this.spanHeight = spanHeight;
        this.pid = pid;
        this.id = id;
        this.isParent = isParent;
    }

    /**
     * @param row
     * @param col
     * @param title
     * @param spanHeight
     * @param isParent
     */
    public FormModel(int row, int col, String title, int spanHeight, boolean isParent) {
        this.row = row;
        this.col = col;
        this.title = title;
        this.spanHeight = spanHeight;
        this.isParent = isParent;
    }

    public FormModel(int row, int col, String title, int spanHeight, boolean isParent, int pid, int id, List<FormModel> child) {
        this.row = row;
        this.col = col;
        this.title = title;
        this.spanHeight = spanHeight;
        this.isParent = isParent;
        this.pid = pid;
        this.id = id;
        this.child = child;
    }

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

    public int getSpanHeightTotal() {
        if (isParent) {
            int height = 0;
            if (child != null) {
                for (int i = 0; i < child.size(); i++) {
                    height = height + child.get(i).getSpanHeightTotal();
                }
                row = child.get(0).getRow();
                spanHeight = height;
            } else {
                if (rowData != null) {
                    row = rowData.get(0).get(0).getRow();
                    spanHeight = rowData.size();
                }
            }
            spanHeight = spanHeight + 1;
        }

        return spanHeight;
//        if (isParent) {
//            int height = 0;
//            if (rowData != null) {
//                spanHeight = rowData.size();
//            } else {
//                if (child != null)
//                    for (int i = 0; i < child.size(); i++) {
//                        height = height + child.get(i).getSpanHeightTotal();
//                    }
//                spanHeight = height+1;
//            }
//            return spanHeight;
//        } else {
//            return spanHeight;
//        }

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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getTotalTitle() {
        return totalTitle;
    }

    public void setTotalTitle(String totalTitle) {
        this.totalTitle = totalTitle;
    }
}
