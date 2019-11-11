package com.huanxin.oa.form;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.huanxin.oa.R;
import com.huanxin.oa.form.model.FormBean;
import com.huanxin.oa.form.scroll.MyHorizontalScrollView;
import com.huanxin.oa.utils.PxUtil;

import java.util.List;


public class FormMerge extends LinearLayout {
    Context context;

    int width;
    int itemWidth;

    List<FormBean.ReportColumnsBean> columnsTitle;


    MyHorizontalScrollView scrollTitle;
    MyHorizontalScrollView scollForm;
    RecyclerView rvForm;
    LinearLayout llTitlte;

    HorizontalScrollListener horizontalScrollListener = new HorizontalScrollListener();

    public Handler mHandler = new Handler();

    public FormMerge(Context context) {
        this(context, null);
    }

    public FormMerge(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.form_merge, this, true);
        init();
    }

    private void init() {
        itemWidth = PxUtil.getWidth(context) / 5;
        setOrientation(VERTICAL);
        setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));
        initView();
    }

    private void initView() {
        llTitlte = findViewById(R.id.ll_title);
        rvForm = findViewById(R.id.rv_form);
        scrollTitle = findViewById(R.id.scroll_title);
        scollForm = findViewById(R.id.scroll_form);
        setScorll();
        initHandler();
    }

    private void initHandler() {
        //设置横向初始位置，延迟500，不然无效
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollTitle.scrollTo(0, 0);
            }
        }, 500);
    }

    private void setScorll() {
        setScorllHorListener();
    }

    private void setScorllHorListener() {
        scrollTitle.setOnHorizontalScrollListener(horizontalScrollListener);
        scollForm.setOnHorizontalScrollListener(horizontalScrollListener);
    }

    private class HorizontalScrollListener implements MyHorizontalScrollView.OnHorizontalScrollListener {
        @Override
        public void onHorizontalScrolled(MyHorizontalScrollView view, int l, int t, int oldl, int oldt) {
            if (view == scrollTitle) {
                scollForm.scrollTo(l, t);
            } else {
                scrollTitle.scrollTo(l, t);
            }
        }

    }

    private void setTitles() {
        Log.e("pos", columnsTitle.size() + "");
        for (int i = 0; i < columnsTitle.size(); i++) {

            if (i == 0)
                llTitlte.addView(getTitleView(columnsTitle.get(i).getSFieldsdisplayName(), true));
            else
                llTitlte.addView(getTitleView(columnsTitle.get(i).getSFieldsdisplayName(), false));
        }
    }

    private TextView getTitleView(String title, boolean isFirst) {
        TextView textView = new TextView(context);
        textView.setLayoutParams(getTitleParams(isFirst));
        textView.setText(title);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(context.getColor(R.color.white));
        textView.setTextSize(14);
        textView.setBackgroundColor(context.getColor(R.color.blue));
        return textView;
    }

    private LinearLayout.LayoutParams getTitleParams(boolean isFirst) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(itemWidth, ViewGroup.LayoutParams.MATCH_PARENT);
        params.rightMargin = 1;
        params.topMargin = 1;
        params.bottomMargin = 1;
        if (isFirst)
            params.leftMargin = 1;
        return params;
    }

    public void setColumnsTitle(List<FormBean.ReportColumnsBean> columnsTitle) {
        this.columnsTitle = columnsTitle;
        Log.d("size", columnsTitle.size() + "");
        width = columnsTitle.size() * itemWidth;
    }

    public void build() {
        setTitles();
    }
}
