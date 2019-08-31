package com.huanxin.oa.view.review;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;
import com.huanxin.oa.R;

import java.util.List;

public class ReviewTabView extends TabLayout {
    List<String> titles;
    TabSelectListener tabSelectListener;

    public void setTabSelectListener(TabSelectListener tabSelectListener) {
        this.tabSelectListener = tabSelectListener;
    }

    public ReviewTabView(Context context) {
        super(context);
        init(context);
    }


    public ReviewTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ReviewTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setBackgroundColor(context.getColor(R.color.white));
    }

    /**
     * 添加tab
     *
     * @param titles
     */
    public void addTabs(List<String> titles) {
        this.titles = titles;
        removeAllTabs();
        if (titles.size() < 6)
            setTabMode(MODE_FIXED);
        else
            setTabMode(MODE_SCROLLABLE);
        for (int i = 0; i < titles.size(); i++) {
            addTab(newTab().setText(titles.get(i)));
        }
        setListener();

    }

    private void setListener() {
        setOnTabSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onTabSelected(Tab tab) {

                tabSelectListener.tabSelect(tab);
            }

            @Override
            public void onTabUnselected(Tab tab) {

            }

            @Override
            public void onTabReselected(Tab tab) {

            }
        });
    }

    public interface TabSelectListener {
        void tabSelect(Tab tab);
    }

}
