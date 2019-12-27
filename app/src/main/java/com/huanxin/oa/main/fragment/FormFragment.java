package com.huanxin.oa.main.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.huanxin.oa.R;
import com.huanxin.oa.dialog.LoadingDialog;
import com.huanxin.oa.form.FormListActivity;
import com.huanxin.oa.form.FormWithChartActivity;
import com.huanxin.oa.form.merge.FormMergeActivity;
import com.huanxin.oa.form.FormNewActivity;
import com.huanxin.oa.form.FormRefreshActivity;
import com.huanxin.oa.interfaces.ResponseListener;
import com.huanxin.oa.main.MainActivity;
import com.huanxin.oa.main.adapter.FormAdapter;
import com.huanxin.oa.main.interfaces.OnItemClickListener;
import com.huanxin.oa.model.main.FormChildMenusBean;
import com.huanxin.oa.model.main.FormMenusBean;
import com.huanxin.oa.model.main.FormModel;
import com.huanxin.oa.utils.CodeUtil;
import com.huanxin.oa.utils.SharedPreferencesHelper;
import com.huanxin.oa.utils.Toasts;
import com.huanxin.oa.utils.net.NetConfig;
import com.huanxin.oa.utils.net.NetParams;
import com.huanxin.oa.utils.net.NetUtil;
import com.huanxin.oa.utils.net.Otype;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FormFragment extends Fragment {

    private final static String TAG = "FormFragment";

    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_form)
    RecyclerView rvForm;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;

    FormAdapter formAdapter;

    List<FormMenusBean> menusBeans;
    //菜单列表，一级列表title为true，一维数组
    List<FormChildMenusBean> menus;

    private String userid;
    private String address;
    private String url;

    SharedPreferencesHelper preferencesHelper;

    public FormFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        userid = ((MainActivity) context).getUserid();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferencesHelper = new SharedPreferencesHelper(getActivity(), getString(R.string.preferenceCache));
        address = (String) preferencesHelper.getSharedPreference("address", "");
        url = address + NetConfig.server + NetConfig.Form_Method;
        menusBeans = new ArrayList<>();
        menus = new ArrayList<>();
        getData();
    }

    /*获取数据*/
    private void getData() {
        LoadingDialog.showDialogForLoading(getActivity());
        new NetUtil(getParams(), url, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                try {
                    initData(string);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LoadingDialog.cancelDialogForLoading();
                        Toasts.showLong(getActivity(), e.getMessage());
                    }
                });

            }
        });
    }

    /**
     * 数据处理
     *
     * @param string
     * @throws Exception
     */
    private void initData(String string) throws Exception {
        FormModel formModel = new Gson().fromJson(string, FormModel.class);
        if (formModel.isSuccess()) {
            List<FormMenusBean> list = formModel.getMenus();
            if (list != null && list.size() > 0) {
                menusBeans.addAll(list);
                initMenus(menusBeans);
            }
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    LoadingDialog.cancelDialogForLoading();
                    try {
                        initList();
                    } catch (Exception e) {

                    }

                }
            });
        } else {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e(TAG, formModel.getMessage());
                    LoadingDialog.cancelDialogForLoading();
                    Toasts.showLong(getActivity(), formModel.getMessage());
                }
            });
        }
    }

    //二维数组解析为一维数据
    private void initMenus(List<FormMenusBean> menusBeans) {
        for (int i = 0; i < menusBeans.size(); i++) {
            FormChildMenusBean menu = new FormChildMenusBean();
            menu.setTitle(true);
            menu.setSMenuName(menusBeans.get(i).getSMenuName());
            menus.add(menu);
            menus.addAll(menusBeans.get(i).getChildMenus());
        }
    }

    private List<NetParams> getParams() {
        List<NetParams> list = new ArrayList<>();
        list.add(new NetParams("userid", userid));
        list.add(new NetParams("otype", Otype.FormMenu));
        return list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_form, container, false);
        ButterKnife.bind(this, view);
        initView();

        return view;
    }

    /**
     * 初始化控件
     */
    private void initView() {
        tvTitle.setText(getString(R.string.title_form));
        tvTitle.setTextColor(getActivity().getColor(R.color.default_text_color));
        rlTop.setBackgroundColor(getActivity().getColor(R.color.white));
        rvForm.setLayoutManager(getLayoutManager());
        if (menus.size() > 0) {
            rvForm.setAdapter(formAdapter);//如果没有切换fragment时会出现 No adapter attached; skipping layout
        }
    }

    private void initList() throws Exception {
        if (menus == null || menus.size() == 0) {
            tvEmpty.setVisibility(View.VISIBLE);
            rvForm.setVisibility(View.GONE);
        } else {
            tvEmpty.setVisibility(View.GONE);
            rvForm.setVisibility(View.VISIBLE);
            setList();
        }

    }

    /**
     * 加载数据
     */
    private void setList() {
        if (formAdapter == null) {
            Log.e("menus", new Gson().toJson(menus));
            formAdapter = new FormAdapter(menus, getActivity());
            formAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    if (menus.get(position).isTitle() == false) {
                        int isChart = menus.get(position).getIShowChart();
                        int isUnion = menus.get(position).getIIsUnion();
                        int menuId = menus.get(position).getIFormID();
                        int isPage = menus.get(position).getiPageGetData();
                        String isAppStyle = menus.get(position).getSAppStyle();

                        if (isUnion == 0 && isChart == 0) {
                            Intent intent = new Intent();
                            if (isAppStyle.equals("列表")) {
                                intent.setClass(getActivity(), FormListActivity.class);
                            } else if (isAppStyle.equals("表格") && isPage == 0) {
                                intent.setClass(getActivity(), FormMergeActivity.class);
                            } else if (isPage == 0) {
                                intent.setClass(getActivity(), FormNewActivity.class);
                            } else
                                intent.setClass(getActivity(), FormRefreshActivity.class);
                            intent.putExtra("menuid", menuId + "");
                            intent.putExtra("title", menus.get(position).getSMenuName());
                            startActivity(intent);
                        } else if (isUnion == 0 && isChart == 1) {
                            Intent intent = new Intent();
//                            if (address.equals(NetConfig.addressLocal))
//                            intent.setClass(getActivity(), FormMergeActivity.class);
//                            else
                            intent.setClass(getActivity(), FormWithChartActivity.class);
                            intent.putExtra("menuid", menuId + "");
                            intent.putExtra("title", menus.get(position).getSMenuName());
                            startActivity(intent);
                        }
                    }
                }
            });

            rvForm.setAdapter(formAdapter);

        } else
            formAdapter.notifyDataSetChanged();
    }

    /**
     * 设置LayoutManager
     *
     * @return
     */
    private GridLayoutManager getLayoutManager() {
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 4);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (formAdapter.getItemViewType(position) == CodeUtil.MENUS_TITLE)
                    return 4;
                else return 1;
            }
        });
        return manager;
    }

    @OnClick(R.id.tv_empty)
    public void onViewClicked() {
        getData();
    }
}
