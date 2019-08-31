package com.huanxin.oa.model.form;

import java.util.List;

public class FormTwo {
    String text;
    String textColor;
    int textSize;
    int weight;
    List<List<FormThree>> formThrees;

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

    public List<List<FormThree>> getFormThrees() {
        return formThrees;
    }

    public void setFormThrees(List<List<FormThree>> formThrees) {
        this.formThrees = formThrees;
    }
}
