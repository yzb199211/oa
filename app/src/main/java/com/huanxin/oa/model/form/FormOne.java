package com.huanxin.oa.model.form;

import java.util.List;

public class FormOne {
    String text;
    String textColor;
    int textSize;
    int weight;
    List<FormTwo> formTwos;

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

    public List<FormTwo> getFormTwos() {
        return formTwos;
    }

    public void setFormTwos(List<FormTwo> formTwos) {
        this.formTwos = formTwos;
    }
}
