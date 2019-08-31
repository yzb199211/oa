package com.huanxin.oa;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayout;
import com.huanxin.oa.utils.PxUtil;
import com.huanxin.oa.view.MineItemView;
import com.huanxin.oa.view.review.ClickListener;
import com.huanxin.oa.view.review.ReviewSelectItem;
import com.huanxin.oa.view.review.ReviewTabView;

import java.util.ArrayList;
import java.util.List;

public class UserCenterActivity extends AppCompatActivity {
    MineItemView mineItemView;
    ReviewSelectItem selectItem;
    RelativeLayout rlTop;
    ReviewTabView tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_center);

        mineItemView = findViewById(R.id.mineItemView);
        selectItem = findViewById(R.id.review_select_item);
        rlTop = findViewById(R.id.rl_top);
        tabLayout = findViewById(R.id.tab_view);
        List<String> list = new ArrayList<>();
        list.add("生日");
        list.add("审核");
        list.add("提醒");
        list.add("财务");
        list.add("生产");
        list.add("生产");
        tabLayout.addTabs(list);
        tabLayout.setTabSelectListener(new ReviewTabView.TabSelectListener() {
            @Override
            public void tabSelect(TabLayout.Tab tab) {
                Log.e("selectedTab", tab.getPosition() + "");
            }
        });

        selectItem.setCustomOnClickListener(new ClickListener() {
            @Override
            public void Click(View v) {
                setPop();
            }
        });
    }

    private void setPop() {
        View view = LayoutInflater.from(this).inflate(R.layout.pop_layout, null, true);
        int height = PxUtil.getHeight(this) - rlTop.getHeight() - PxUtil.getStatusBarHeight(this);
        PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, height);
        popupWindow.showAsDropDown(rlTop);
    }

}
