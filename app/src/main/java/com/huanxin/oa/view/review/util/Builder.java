package com.huanxin.oa.view.review.util;

import android.content.Context;

import androidx.annotation.ColorRes;

import com.huanxin.oa.review.model.ReviewSelect;
import com.huanxin.oa.view.review.GridDecoration;

import java.util.List;

public class Builder {
    private Context c;
    public int horColor;
    public int verColor;
    public int dividerHorSize;
    public int dividerVerSize;
    public int marginLeft, marginRight;
    public boolean isShowLastDivider = false;
    public boolean isExistHeadView = false;
    public List<ReviewSelect> list;

    public Builder(Context c) {
        this.c = c;
    }

    /**
     * 设置divider的颜色
     *
     * @param color
     * @return
     */
    public Builder color(@ColorRes int color) {
        this.horColor = c.getResources().getColor(color);
        this.verColor = c.getResources().getColor(color);
        return this;
    }

    /**
     * 单独设置横向divider的颜色
     *
     * @param horColor
     * @return
     */
    public Builder horColor(@ColorRes int horColor) {
        this.horColor = c.getResources().getColor(horColor);
        return this;
    }

    /**
     * 单独设置纵向divider的颜色
     *
     * @param verColor
     * @return
     */
    public Builder verColor(@ColorRes int verColor) {
        this.verColor = c.getResources().getColor(verColor);
        return this;
    }

    /**
     * 设置divider的宽度
     *
     * @param size
     * @return
     */
    public Builder size(int size) {
        this.dividerHorSize = size;
        this.dividerVerSize = size;
        return this;
    }

    /**
     * 设置横向divider的宽度
     *
     * @param horSize
     * @return
     */
    public Builder horSize(int horSize) {
        this.dividerHorSize = horSize;
        return this;
    }

    /**
     * 设置纵向divider的宽度
     *
     * @param verSize
     * @return
     */
    public Builder verSize(int verSize) {
        this.dividerVerSize = verSize;
        return this;
    }

    /**
     * 设置剔除HeadView的RecyclerView左右两边的外间距
     *
     * @param marginLeft
     * @param marginRight
     * @return
     */
    public Builder margin(int marginLeft, int marginRight) {
        this.marginLeft = marginLeft;
        this.marginRight = marginRight;
        return this;
    }

    /**
     * 最后一行divider是否需要显示
     *
     * @param isShow
     * @return
     */
    public Builder showLastDivider(boolean isShow) {
        this.isShowLastDivider = isShow;
        return this;
    }

    /**
     * 是否包含HeadView
     *
     * @param isExistHead
     * @return
     */
    public Builder isExistHead(boolean isExistHead) {
        this.isExistHeadView = isExistHead;
        return this;
    }

    public Builder setList(List<ReviewSelect> list) {
        this.list = list;
        return this;
    }

    public GridDecoration build() {
        return new GridDecoration(this);
    }

}