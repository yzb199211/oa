package com.huanxin.oa.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.huanxin.oa.BaseActivity;
import com.huanxin.oa.R;
import com.huanxin.oa.main.adapter.TabAdapter;
import com.huanxin.oa.main.fragment.ContactFragment;
import com.huanxin.oa.main.fragment.FormFragment;
import com.huanxin.oa.main.fragment.MainFragment;
import com.huanxin.oa.main.fragment.MineFragment;
import com.huanxin.oa.model.login.TablesBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    private final String TAG = "MainActivity";
    //Tab 文字
    private final int[] TAB_TITLES = new int[]{R.string.title_main, R.string.title_contact, R.string.title_find, R.string.title_my};
    List<String> titles = new ArrayList<>();
    //Tab 图片
    private final int[] TAB_IMGS = new int[]{R.drawable.tab_main, R.drawable.tab_contact, R.drawable.tab_find, R.drawable.tab_mine};
    List<Fragment> fragments = new ArrayList<>();
    TabAdapter mAdapter;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.tb_choose)
    TabLayout tbChoose;
    String initData;
    String mainData;
    String mineData;
    String numData;
    String userid;

    public String getMainData() {
        return mainData;
    }

    public String getMineData() {
        return mineData;
    }

    public String getUserid() {
        return userid;
    }

    public String getNumData() {
        return numData;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        inti();
        setTabs(tbChoose, this.getLayoutInflater(), TAB_TITLES, TAB_IMGS);
    }

    private void inti() {
        Intent intent = getIntent();
        initData = intent.getStringExtra("data");
        numData = intent.getStringExtra("num");
        TablesBean tablesBean = new Gson().fromJson(initData, TablesBean.class);
        mainData = new Gson().toJson(tablesBean.getMenu());
        mineData = new Gson().toJson(tablesBean.getPerson());
        userid = tablesBean.getPerson().get(0).getSCode();
        setViewPager();
    }

    private void setViewPager() {
//        Log.e(TAG, initData);
        fragments.add(new MainFragment());
        fragments.add(new ContactFragment());
        fragments.add(new FormFragment());
        fragments.add(new MineFragment());
        titles.add(getString(R.string.title_main));
        titles.add(getString(R.string.title_contact));
        titles.add(getString(R.string.title_form));
        titles.add(getString(R.string.title_my));
        mAdapter = new TabAdapter(getSupportFragmentManager(), fragments, titles);
        viewpager.setAdapter(mAdapter);
        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tbChoose));
        tbChoose.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewpager));

    }

    /**
     * @description: 设置添加Tab
     */
    private void setTabs(TabLayout tabLayout, LayoutInflater inflater, int[] tabTitlees, int[] tabImgs) {
        for (int i = 0; i < tabImgs.length; i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            View view = inflater.inflate(R.layout.item_tab, null);
            tab.setCustomView(view);
            TextView tvTitle = view.findViewById(R.id.tv_tab);
            tvTitle.setText(tabTitlees[i]);
            ImageView imgTab = view.findViewById(R.id.img_tab);
            imgTab.setImageResource(tabImgs[i]);
            tabLayout.addTab(tab);
        }
    }


}
