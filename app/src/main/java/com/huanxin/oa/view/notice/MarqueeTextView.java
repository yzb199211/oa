package com.huanxin.oa.view.notice;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.Nullable;

import com.huanxin.oa.R;


public class MarqueeTextView extends LinearLayout {

    private Context mContext;
    private ViewFlipper viewFlipper;
    private View marqueeTextView;
    private String[] textArrays;
    private MarqueeTextViewClickListener marqueeTextViewClickListener;
    private int textColor;

    /**
     * 设置字体颜色
     *
     * @param textColor
     */
    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    /**
     * 构造方法
     *
     * @param context
     */
    public MarqueeTextView(Context context) {
        super(context);
        mContext = context;
        initBasicView();
    }

    /**
     * * 构造方法
     *
     * @param context
     * @param attrs
     */

    public MarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initBasicView();
    }

    /**
     * 设置需要加载的字符串和添加监听事件
     *
     * @param textArrays
     * @param marqueeTextViewClickListener
     */
    public void setTextArraysAndClickListener(String[] textArrays, @Nullable MarqueeTextViewClickListener marqueeTextViewClickListener) {//1.设置数据源；2.设置监听回调（将textView点击事件传递到目标界面进行操作）
        this.textArrays = textArrays;
        this.marqueeTextViewClickListener = marqueeTextViewClickListener;
        initMarqueeTextView(textArrays, marqueeTextViewClickListener);
    }

    /**
     * 设置字符串
     *
     * @param textArrays
     */
    public void setTextArrays(String[] textArrays) {
        this.textArrays = textArrays;
        if (textArrays.length == 0) {
            return;
        }
        viewFlipper.removeAllViews();
        intiView();
    }

    /**
     * 设置点击监听事件
     *
     * @param marqueeTextViewClickListener
     */
    public void setClickListener(MarqueeTextViewClickListener marqueeTextViewClickListener) {
        try {
            this.marqueeTextViewClickListener = marqueeTextViewClickListener;
            if (textArrays.length == 0 || textArrays == null) {
                return;
            }
            for (int i = 0; i < textArrays.length; i++) {
                final int position = i;
                viewFlipper.getChildAt(position).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        marqueeTextViewClickListener.onClick(v, position);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 加载布局，初始化ViewFlipper组件及效果
     */
    public void initBasicView() {
        marqueeTextView = LayoutInflater.from(mContext).inflate(R.layout.layout_marquee_textview, null);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        addView(marqueeTextView, layoutParams);
        viewFlipper = marqueeTextView.findViewById(R.id.viewFlipper);
        viewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.slide_in_bottom));//设置上下的动画效果（自定义动画，所以改左右也很简单）
        viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.slide_out_top));
        viewFlipper.startFlipping();
    }

    /**
     * 初始化字符串和监听事件
     *
     * @param textArrays
     * @param marqueeTextViewClickListener
     */
    public void initMarqueeTextView(String[] textArrays, @Nullable MarqueeTextViewClickListener marqueeTextViewClickListener) {
        if (textArrays.length == 0) {
            return;
        }
        viewFlipper.removeAllViews();
        intiView();

    }

    /**
     * viewFlipper添加TextView 为了减少代码量，添加marqueeTextViewClickListener==null判断来添加Onclick
     */
    private void intiView() {
        try {
            int i = 0;
            while (i < textArrays.length) {
                LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                viewFlipper.addView(getTextView(i), lp);
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化TextView
     *
     * @param position
     * @return
     */
    private TextView getTextView(int position) {
        TextView textView = new TextView(mContext);
        textView.setText(textArrays[position]);
        if (textColor != 0)
            textView.setTextColor(textColor);
        else
            textView.setTextColor(Color.parseColor("#666666"));
        if (marqueeTextViewClickListener != null)
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    marqueeTextViewClickListener.onClick(v, position);
                }
            });
        return textView;
    }


    /**
     * 释放资源
     */
    public void releaseResources() {
        if (marqueeTextView != null) {
            if (viewFlipper != null) {
                viewFlipper.stopFlipping();
                viewFlipper.removeAllViews();
                viewFlipper = null;
            }
            marqueeTextView = null;
        }
    }

}