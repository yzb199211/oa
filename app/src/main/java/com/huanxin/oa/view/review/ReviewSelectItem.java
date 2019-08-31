package com.huanxin.oa.view.review;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;

import com.huanxin.oa.R;
import com.huanxin.oa.utils.PxUtil;

@SuppressLint("AppCompatCustomView")
public class ReviewSelectItem extends TextView {
    final static int NORMAL = 10;
    final static int SELECT = 11;
    ClickListener clickListener;
    int normalColor;
    int selectColor;
    int textNormalColor;
    int textSelectColor;

    boolean isSelect = false;

    public ReviewSelectItem(Context context) {
        this(context, null);
    }

    public ReviewSelectItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        inti(context, attrs);
    }

    private void inti(Context context, AttributeSet attrs) {
        intiTypeArray(context, attrs);
        setGravity(Gravity.CENTER);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100);
        setLayoutParams(layoutParams);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.Click(v);
                isSelect = !isSelect;
                setChoose(isSelect);
            }
        });
    }

    private void intiTypeArray(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.reviewSelectItem);
        normalColor = array.getColor(R.styleable.reviewSelectItem_normalColor, context.getColor(R.color.white));
        selectColor = array.getColor(R.styleable.reviewSelectItem_selectColor, context.getColor(R.color.default_content_color));
        textNormalColor = array.getColor(R.styleable.reviewSelectItem_textNormalColor, context.getColor(R.color.default_content_color));
        textSelectColor = array.getColor(R.styleable.reviewSelectItem_textSelectColor, context.getColor(R.color.white));
        array.recycle();
    }

    /**
     * 刷新item颜色
     *
     * @param Type
     */
    private void refreshColor(int Type) {
        if (Type == NORMAL) {
            setTextColor(textNormalColor);
            setBackgroundColor(normalColor);
        } else if (Type == SELECT) {
            setTextColor(textSelectColor);
            setBackgroundColor(selectColor);
        }
    }

    /**
     * 判断是否选中
     *
     * @param isSelect
     */
    private void setChoose(boolean isSelect) {
        if (isSelect == false) {
            refreshColor(NORMAL);
        } else {
            refreshColor(SELECT);
        }

    }

    public ReviewSelectItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inti(context, attrs);
    }

    /**
     * 这个方法等同于setOnClickListener
     *
     * @param clickListener 这个接口就是OnClickListener
     */
    public void setCustomOnClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    /**
     * 设置常规颜色
     *
     * @param normalColor
     */
    public void setNormalColor(int normalColor) {
        this.normalColor = normalColor;
        refreshColor(NORMAL);
    }

    /**
     * 设置选中颜色
     *
     * @param selectColor
     */
    public void setSelectColor(int selectColor) {
        this.selectColor = selectColor;
        refreshColor(SELECT);
    }

    /**
     * 设置常规字体颜色
     *
     * @param textNormalColor
     */
    public void setTextNormalColor(int textNormalColor) {
        this.textNormalColor = textNormalColor;
        refreshColor(NORMAL);
    }

    /**
     * 设置选中字体颜色
     *
     * @param textSelectColor
     */
    public void setTextSelectColor(int textSelectColor) {
        this.textSelectColor = textSelectColor;
        refreshColor(SELECT);
    }

    public int getNormalColor() {
        return normalColor;
    }

    public int getSelectColor() {
        return selectColor;
    }

    public int getTextNormalColor() {
        return textNormalColor;
    }

    public int getTextSelectColor() {
        return textSelectColor;
    }

    public boolean isSelect() {
        return isSelect;
    }
}
