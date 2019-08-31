package com.huanxin.oa.model.form;

import java.util.List;

public class FormModel {
    String text;
    String textColor;
    int textSize;
    int weight;
    int span;

    public FormModel(String text, List<FormModel> formModels) {
        this.text = text;
        this.formModels = formModels;
    }

    List<FormModel> formModels;

    public FormModel() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public List<FormModel> getFormModels() {
        return formModels;
    }

    public void setFormModels(List<FormModel> formModels) {
        this.formModels = formModels;
    }

    public int getSpan() {
        return span;
    }

    public void setSpan(int span) {
        this.span = span;
    }
}
