package com.huanxin.oa.form.model;

import android.graphics.Typeface;
import android.view.Gravity;

import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DimenRes;

import com.huanxin.oa.R;

public class FormChartBean {
    int row = 0;
    int col = 0;
    @ColorRes
    int backgroundColor = R.color.white;
    @ColorRes
    int textColor = R.color.default_content_color;
    @DimenRes
    int textSize = R.dimen.sp_14;
    @DimenRes
    int padding = R.dimen.dp_0;
    @DimenRes
    int paddingLeft = R.dimen.dp_0;
    @DimenRes
    int paddingTop = R.dimen.dp_0;
    @DimenRes
    int paddingRight = R.dimen.dp_0;
    @DimenRes
    int paddingBottom = R.dimen.dp_0;

    int gravity = 0;

    String text = "";
    boolean isTitle = false;

    Typeface typeface = Typeface.DEFAULT;


    public int getRow() {
        return row;
    }

    public FormChartBean setRow(int row) {
        this.row = row;
        return this;
    }

    public int getCol() {
        return col;
    }

    public FormChartBean setCol(int col) {
        this.col = col;
        return this;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public FormChartBean setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public int getTextColor() {
        return textColor;
    }

    public FormChartBean setTextColor(int textColor) {
        this.textColor = textColor;
        return this;
    }

    public int getTextSize() {
        return textSize;
    }

    public FormChartBean setTextSize(int textSize) {
        this.textSize = textSize;
        return this;
    }

    public String getText() {
        return text;
    }

    public FormChartBean setText(String text) {
        this.text = text;
        return this;
    }

    public boolean isTitle() {
        return isTitle;
    }

    public FormChartBean setTitle(boolean title) {
        isTitle = title;
        return this;
    }

    public Typeface getTypeface() {
        return typeface;
    }

    public FormChartBean setTypeface(Typeface typeface) {
        this.typeface = typeface;
        return this;
    }

    public int getPadding() {
        return padding;
    }

    public FormChartBean setPadding(int padding) {
        this.padding = padding;
        this.paddingLeft = padding;
        this.paddingRight = padding;
        this.paddingTop = padding;
        this.paddingBottom = padding;
        return this;
    }

    public int getPaddingLeft() {
        return paddingLeft;
    }

    public FormChartBean setPaddingLeft(int paddingLeft) {
        this.paddingLeft = paddingLeft;
        return this;
    }

    public int getPaddingTop() {
        return paddingTop;
    }

    public FormChartBean setPaddingTop(int paddingTop) {
        this.paddingTop = paddingTop;
        return this;
    }

    public int getPaddingRight() {
        return paddingRight;
    }

    public FormChartBean setPaddingRight(int paddingRight) {
        this.paddingRight = paddingRight;
        return this;
    }

    public int getPaddingBottom() {
        return paddingBottom;
    }

    public FormChartBean setPaddingBottom(int paddingBottom) {
        this.paddingBottom = paddingBottom;
        return this;
    }

    public int getGravity() {
        return gravity;
    }

    public FormChartBean setGravity(int gravity) {
        this.gravity = gravity;
        return this;
    }

}
