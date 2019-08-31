package com.huanxin.oa.model.form;

import java.util.List;

public class FormSpan {
    int rowSpan;
    List<FormSpan> formSpans;

    public int getRowSpan() {
        return rowSpan;
    }

    public void setRowSpan(int rowSpan) {
        this.rowSpan = rowSpan;
    }

    public List<FormSpan> getFormSpans() {
        return formSpans;
    }

    public void setFormSpans(List<FormSpan> formSpans) {
        this.formSpans = formSpans;
    }
}
