package com.huanxin.oa.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.huanxin.oa.R;
import com.huanxin.oa.utils.ImageLoaderUtil;

public class MineItemView extends LinearLayout {
    int leftSrc;
    boolean leftVisiable;
    float leftWidth;
    float leftHeight;
    float leftPadding;
    String centerText;
    int centerTextColor;
    float centerTextSize;
    float centerWidth;
    boolean centerTextIsBlod;
    boolean rightVisible;
    String centerContentText;
    int centerContentTextColor;
    float centerContentTextSize;
    float centerContentTextWidth;
    boolean centerContentIsBlod;
    ImageView ivLeft;
    ImageView ivRight;
    TextView tvCenter;
    TextView tvContent;


    public MineItemView(Context context) {
        this(context, null);
    }

    public MineItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MineItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.item_mine, this, true);
        ivLeft = findViewById(R.id.iv_type);
        tvCenter = findViewById(R.id.tv_title);
        ivRight = findViewById(R.id.iv_more);
        tvContent = findViewById(R.id.tv_content);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MineItemView);
        leftSrc = array.getResourceId(R.styleable.MineItemView_leftSrc, 0);
        leftVisiable = array.getBoolean(R.styleable.MineItemView_leftVisible, true);
        leftWidth = array.getDimension(R.styleable.MineItemView_leftWidth, context.getResources().getDimension(R.dimen.dp_40));
        leftHeight = array.getDimension(R.styleable.MineItemView_leftHeight, context.getResources().getDimension(R.dimen.dp_40));
        leftPadding = array.getDimension(R.styleable.MineItemView_leftPadding, context.getResources().getDimension(R.dimen.dp_5));
        centerText = array.getString(R.styleable.MineItemView_centerText);
        centerTextColor = array.getColor(R.styleable.MineItemView_centerTextColor, Color.BLACK);
        centerTextSize = array.getDimension(R.styleable.MineItemView_centerTextSize, R.dimen.sp_16);
        centerWidth = array.getDimension(R.styleable.MineItemView_centerWidth, 0);
        centerContentText = array.getString(R.styleable.MineItemView_centerContentText);
        centerContentTextColor = array.getColor(R.styleable.MineItemView_centerCotentTextColor, context.getColor(R.color.default_content_color));
        centerContentTextSize = array.getDimension(R.styleable.MineItemView_centerContengtTextSize, context.getResources().getDimension(R.dimen.sp_16));
        centerContentTextWidth = array.getDimension(R.styleable.MineItemView_centerContengtTextSize, 0);
        rightVisible = array.getBoolean(R.styleable.MineItemView_rightVisible, false);
        array.recycle();

    }


    public int getLeftSrc() {
        return leftSrc;
    }

    public void setLeftSrc(int leftSrc) {
        this.leftSrc = leftSrc;
        ImageLoaderUtil.loadDrawableImg(ivLeft, leftSrc);
    }

    public boolean isLeftVisiable() {
        return leftVisiable;
    }

    //设置左边图标是否显示
    public void setLeftVisiable(boolean leftVisiable) {
        this.leftVisiable = leftVisiable;
        if (leftVisiable == true)
            ivLeft.setVisibility(VISIBLE);
        else
            ivLeft.setVisibility(INVISIBLE);
    }

    public float getLeftWidth() {
        return leftWidth;
    }

    //设置左边图标宽高，不建议使用
    public void setLeftWidth(float leftWidth) {
        this.leftWidth = leftWidth;
    }

    public float getLeftHeight() {
        return leftHeight;
    }

    public void setLeftHeight(float leftHeight) {
        this.leftHeight = leftHeight;
    }

    public float getLeftPadding() {
        return leftPadding;
    }

    //设置左边图表内边距
    public void setLeftPadding(float leftPadding) {
        this.leftPadding = leftPadding;
        int padding = (int) leftPadding;
        ivLeft.setPadding(padding, padding, padding, padding);
    }

    //设置标题名称
    public String getCenterText() {
        return centerText;
    }

    public void setCenterText(String centerText) {
        this.centerText = centerText;
        tvCenter.setText(centerText);
    }

    public int getCenterTextColor() {
        return centerTextColor;
    }

    //设置中间字体颜色
    public void setCenterTextColor(int centerTextColor) {
        this.centerTextColor = centerTextColor;
        tvCenter.setTextColor(centerTextColor);
    }

    public float getCenterTextSize() {
        return centerTextSize;

    }

    //设置中间字体大小
    public void setCenterTextSize(float centerTextSize) {
        this.centerTextSize = centerTextSize;
        tvCenter.setTextSize(centerTextSize);
    }

    public float getCenterWidth() {
        return centerWidth;
    }

    //设置中间宽度
    public void setCenterWidth(float centerWidth) {
        this.centerWidth = centerWidth;
    }

    //设置标题是否为粗体
    public void setCenterTextIsBlod(boolean centerTextIsBlod) {
        this.centerTextIsBlod = centerTextIsBlod;
        if (centerTextIsBlod == true)
            tvCenter.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        else
            tvCenter.setTypeface(Typeface.DEFAULT);
    }

    public boolean isCenterTextIsBlod() {
        return centerTextIsBlod;
    }


    public boolean isRightVisible() {
        return rightVisible;
    }

    //设置右边箭头是否显示
    public void setRightVisible(boolean rightVisible) {
        this.rightVisible = rightVisible;
        if (rightVisible == true)
            ivRight.setVisibility(VISIBLE);
        else
            ivRight.setVisibility(INVISIBLE);
    }

    public String getCenterContentText() {
        return centerContentText;
    }

    //设置内容文字
    public void setCenterContentText(String centerContentText) {
        this.centerContentText = centerContentText;
        tvContent.setText(centerContentText);
    }

    public int getCenterContentTextColor() {
        return centerContentTextColor;
    }

    //设置内容字体颜色
    public void setCenterContentTextColor(int centerContentTextColor) {
        this.centerContentTextColor = centerContentTextColor;
        tvContent.setTextColor(centerContentTextColor);
    }

    public float getCenterContentTextSize() {
        return centerContentTextSize;
    }

    //设置内容字体大小
    public void setCenterContentTextSize(float centerContentTextSize) {
        this.centerContentTextSize = centerContentTextSize;
        tvContent.setTextSize(centerContentTextSize);
    }

    public float getCenterContentTextWidth() {
        return centerContentTextWidth;
    }

    //设置内容长度，已设置权重，更改需要去掉权重
    public void setCenterContentTextWidth(float centerContentTextWidth) {
        this.centerContentTextWidth = centerContentTextWidth;
    }

    //设置内容是否为粗体
    public void setCenterContentIsBlod(boolean centerContentIsBlod) {
        this.centerContentIsBlod = centerContentIsBlod;
        if (centerContentIsBlod == true)
            tvContent.setTypeface(Typeface.DEFAULT_BOLD);
        else
            tvContent.setTypeface(Typeface.DEFAULT);
    }

    public boolean isCenterContentIsBlod() {
        return centerContentIsBlod;
    }
}
