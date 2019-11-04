package com.huanxin.oa.main.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huanxin.oa.R;
import com.huanxin.oa.main.MainActivity;
import com.huanxin.oa.main.adapter.FontTextAdapter;
import com.huanxin.oa.main.adapter.MenuAdapter;
import com.huanxin.oa.main.adapter.MenuUsualAdapter;
import com.huanxin.oa.main.interfaces.OnItemClickListener;
import com.huanxin.oa.main.manager.NoScrollGvManager;
import com.huanxin.oa.main.utils.Menu;
import com.huanxin.oa.main.utils.MenuUtil;
import com.huanxin.oa.message.MessageActivity;
import com.huanxin.oa.model.login.MenuBean;
import com.huanxin.oa.model.login.NumBean;
import com.huanxin.oa.review.ReviewActivity;
import com.huanxin.oa.sign.SignActivity;
import com.huanxin.oa.utils.Toasts;
import com.huanxin.oa.view.cycle.CircleBanner;
import com.huanxin.oa.view.cycle.adapter.DataViewHolder;
import com.huanxin.oa.view.cycle.adapter.HolderCreator;
import com.huanxin.oa.view.cycle.bean.DataBean;
import com.huanxin.oa.view.notice.MarqueeTextView;
import com.huanxin.oa.view.notice.MarqueeTextViewClickListener;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    private CircleBanner<DataBean, DataViewHolder> mViewpager;
    private List<DataBean> mDataList = new ArrayList<>();
    private List<Integer> mPicResList = new ArrayList<>();
    private List<Menu> menus = new ArrayList<>();
    private List<Menu> menuUsuals = new ArrayList<>();
    private List<Integer> ids = new ArrayList<>();
    private String[] picUrls = {"http://pic15.nipic.com/20110628/1369025_192645024000_2.jpg",
            "http://pic1.nipic.com/2008-08-14/2008814183939909_2.jpg",
            "http://pic1.win4000.com/wallpaper/9/5450ae2fdef8a.jpg",
            "http://pic41.nipic.com/20140429/12728082_192158998000_2.jpg"};

    boolean isFrist = true;

    private TextView tvTitle;
    private MarqueeTextView mtvNotice;
    private RecyclerView rvMenu;
    private RecyclerView rvMenuUsual;
    private ScrollView scrollView;
    private RelativeLayout rlTop;

    private MenuAdapter menuAdapter;
    private MenuUsualAdapter menuUsualAdapter;
    private FontTextAdapter fontTextAdapter;

    private String initData;
    private String numData;
    private String userid;

    public MainFragment() {
        // Required empty public constructor
    }


    Intent intent = new Intent();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        initData = ((MainActivity) context).getMainData();
        numData = ((MainActivity) context).getNumData();
        userid = ((MainActivity) context).getUserid();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent.putExtra("userid", userid);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        // Inflate the layout for this fragment
        initView(view);
        if (isFrist) {
            initData();
            isFrist = false;
        }
        setMenu();
        setMenuUsual();
//        setMenuUsuals();
        setViewPager();
        setNotice();
        return view;
    }

    private void initView(View view) {
        mViewpager = view.findViewById(R.id.viewpager);
        mtvNotice = view.findViewById(R.id.mtv_notice);
        rvMenu = view.findViewById(R.id.rv_menu);
        rvMenuUsual = view.findViewById(R.id.rv_menu_usual);
        scrollView = view.findViewById(R.id.scrollView);
        rlTop = view.findViewById(R.id.rl_top);
        rlTop.setBackgroundColor(getActivity().getColor(R.color.main_activity_top));
        tvTitle = view.findViewById(R.id.tv_title);
//        tvTitle.setText(getActivity().getString(R.string.app_name1));
        tvTitle.setTextColor(getActivity().getColor(R.color.white));
    }

    private void setMenuUsuals() {
        fontTextAdapter = new FontTextAdapter(menuUsuals, getActivity());
        rvMenuUsual.setAdapter(fontTextAdapter);
        NoScrollGvManager manager = new NoScrollGvManager(getActivity(), 4);
        manager.setAutoMeasureEnabled(true);
        rvMenuUsual.setHasFixedSize(true);
        manager.setScrollEnabled(false);
        rvMenuUsual.setLayoutManager(manager);
        fontTextAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
    }

    /**
     * 设置常用菜单
     */
    private void setMenuUsual() {
        menuUsualAdapter = new MenuUsualAdapter(menuUsuals, getActivity());
        rvMenuUsual.setAdapter(menuUsualAdapter);
        NoScrollGvManager manager = new NoScrollGvManager(getActivity(), 4);
        manager.setAutoMeasureEnabled(true);
        rvMenuUsual.setHasFixedSize(true);
        manager.setScrollEnabled(false);
        rvMenuUsual.setLayoutManager(manager);
        menuUsualAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.d("position=======>", position + "");
            }
        });
    }

    /**
     * 设置固定菜单
     */
    private void setMenu() {
        menuAdapter = new MenuAdapter(menus, getActivity());
        NoScrollGvManager manager = new NoScrollGvManager(getActivity(), 4);
        rvMenu.setLayoutManager(manager);
        rvMenu.setAdapter(menuAdapter);
        menuAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                goNext(position);
            }
        });
    }

    /**
     * 跳转
     *
     * @param position
     */
    private void goNext(int position) {
        switch (menus.get(position).getId()) {
            case 1:
                intent.setClass(getActivity(), MessageActivity.class);
                startActivity(intent);
                break;
            case 2:
                intent.setClass(getActivity(), ReviewActivity.class);
                startActivity(intent);
                break;
            case 3:
//                intent.setClass(getActivity(), UserCenterActivity.class);
                intent.setClass(getActivity(), SignActivity.class);
                startActivity(intent);
                break;
            case 4:
              Toasts.showShort(getActivity(),getString(R.string.menu_empty));
                break;
            default:
                break;
        }
    }

    /**
     * 设置公告
     */
    private void setNotice() {
        String[] textArrays = new String[]{"this is content No.1", "this is content No.2", "this is content No.3"};
        mtvNotice.setTextArraysAndClickListener(textArrays, new MarqueeTextViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.e("position", position + "");
            }
        });
    }

    /**
     * 设置轮播图
     */
    private void setViewPager() {
        //  设置指示器位置
        // mViewpager.setIndicatorGravity(CircleViewPager.END);
        //  是否显示指示器
        mViewpager.isShowIndicator(true);
        //  设置图片切换时间间隔
        mViewpager.setInterval(5000);
        //  设置指示器圆点半径
        // mViewpager.setIndicatorRadius(6);
        mViewpager.setAutoPlay(true);
        mViewpager.setCurrentItem(1, true);

        //  设置页面点击事件
        mViewpager.setOnPageClickListener(new CircleBanner.OnPageClickListener() {
            @Override
            public void onPageClick(int position) {
                List<DataBean> list = mViewpager.getList();
                String describe = list.get(position).getDescribe();
                Toasts.showShort(getActivity(), "点击了" + list.get(position).getDescribe());
            }
        });
        //  设置数据
        mViewpager.setPages(mDataList, new HolderCreator<DataViewHolder>() {
            @Override
            public DataViewHolder createViewHolder() {
                return new DataViewHolder();
            }
        });

    }

    /**
     * 数据初始化
     */
    private void initData() {
        for (int i = 0; i < picUrls.length; i++) {
            DataBean dataBean = new DataBean(picUrls[i], "图片" + (i + 1));
            mDataList.add(dataBean);
        }
        for (int i = 1; i <= 4; i++) {
            int drawable = getResources().getIdentifier("a" + i, "drawable", getActivity().getPackageName());
            mPicResList.add(drawable);
        }
        initMenu();
        initMenuUsual();
    }

    /**
     * 初始化固定菜单
     */
    private void initMenu() {
        MenuUtil menuUtil = new MenuUtil(getActivity());
        menus = menuUtil.getMenu();
        if (!TextUtils.isEmpty(numData)) {
            NumBean numBean = new Gson().fromJson(numData, NumBean.class);
            menus.get(0).setMsg(numBean.getRemindCout());
            menus.get(1).setMsg(numBean.getMessageCout());
        } else {
            Toasts.showShort(getActivity(), "固定菜单未配置");
        }
    }

    /**
     * 初始化常用菜单
     */
    private void initMenuUsual() {
        if (!TextUtils.isEmpty(initData)) {
            List<MenuBean> menuBeans = new Gson().fromJson(initData, new TypeToken<List<MenuBean>>() {
            }.getType());
            if (menuBeans != null && menuBeans.size() > 0) {
                MenuUtil menuUsualUtil = new MenuUtil(menuBeans, getActivity());
                menuUsuals = menuUsualUtil.getMenu();
                Log.e("menu", new Gson().toJson(menuUsuals));
            } else {
                Toasts.showShort(getActivity(), "常用菜单未配置");
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mViewpager.stopLoop();
        mtvNotice.releaseResources();
    }
}
