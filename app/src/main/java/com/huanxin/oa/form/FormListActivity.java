package com.huanxin.oa.form;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.huanxin.oa.R;
import com.huanxin.oa.dialog.LoadingDialog;
import com.huanxin.oa.form.adapter.FormListAdapter;
import com.huanxin.oa.form.model.FormBean;
import com.huanxin.oa.form.model.FormConditionBean;
import com.huanxin.oa.interfaces.ResponseListener;
import com.huanxin.oa.review.model.ReviewInfo;
import com.huanxin.oa.review.model.ReviewStyle;
import com.huanxin.oa.utils.SharedPreferencesHelper;
import com.huanxin.oa.utils.StringUtil;
import com.huanxin.oa.utils.Toasts;
import com.huanxin.oa.utils.net.NetConfig;
import com.huanxin.oa.utils.net.NetParams;
import com.huanxin.oa.utils.net.NetUtil;
import com.huanxin.oa.view.recycle.MyRecyclerViewDivider;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FormListActivity extends AppCompatActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.refresh_list)
    XRecyclerView refreshList;

    LinearLayoutManager manager;

    int pagerIndex = 1;

    private String menuId;
    private String userId;
    private String filter = "";

    SharedPreferencesHelper preferencesHelper;

    List<FormBean.ReportColumns2> styleList;
    List<FormBean.ReportInfoBean> infoBeans;
    List<FormBean.ReportConditionBean> conditionBeans;
    //    List<FormBean.ReportColumnsBean> columnsBeans;
    private List<FormConditionBean> fixconditions;
    private List<FormBean.ReportConditionBean> conditions;
    private List<ReviewStyle> items;

    FormListAdapter formListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_list);
        ButterKnife.bind(this);
        preferencesHelper = new SharedPreferencesHelper(this, getString(R.string.preferenceCache));
        inti();
    }

    private void inti() {
        styleList = new ArrayList<>();
        infoBeans = new ArrayList<>();
        conditionBeans = new ArrayList<>();
//        columnsBeans = new ArrayList<>();
        fixconditions = new ArrayList<>();
        conditions = new ArrayList<>();
        items = new ArrayList<>();

        manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        refreshList.setLayoutManager(manager);
        refreshList.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        refreshList.setLoadingMoreProgressStyle(ProgressStyle.Pacman);
        refreshList.setArrowImageView(R.mipmap.iconfont_downgrey);
        refreshList.getDefaultRefreshHeaderView()
                .setRefreshTimeVisible(true);

        refreshList.setLoadingMoreEnabled(true);


        userId = (String) preferencesHelper.getSharedPreference("userid", "");
        Intent intent = getIntent();
        tvTitle.setText(TextUtils.isEmpty(intent.getStringExtra("title")) ? "" : intent.getStringExtra("title"));
        menuId = intent.getStringExtra("menuid");
        ivBack.setVisibility(View.VISIBLE);
        getData(false, false, pagerIndex);
    }

    /*获取数据*/
    private void getData(boolean isRefresh, boolean isLoadMore, int page) {
        LoadingDialog.showDialogForLoading(this);
        new NetUtil(getParams(), NetConfig.url + NetConfig.Form_Method, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                try {
                    JSONObject jsonObject = new JSONObject(string);
                    boolean isSuccess = jsonObject.getBoolean("success");
                    if (isSuccess) {
                        String table = jsonObject.optString("info");
                        Gson gson = new Gson();
                        FormBean formBean = gson.fromJson(table, FormBean.class);
                        List<FormBean.ReportInfoBean> reportInfoBeans = formBean.getReportInfo();
                        List<FormBean.ReportConditionBean> reportConditionBeans = formBean.getReportCondition();
                        List<FormBean.ReportColumns2> stylebeans = formBean.getReportColumns2();

                        if (reportInfoBeans.size() > 0) {
                            initFix(reportInfoBeans.get(0));
                        }

                        if (stylebeans.size() > 0) {
                            styleList.addAll(stylebeans);
                        } else {
                            loadFail("未设置App列样式");
                            return;
                        }

                        initData(jsonObject.optJSONArray("data"));
                        if (reportConditionBeans.size() > 0) {
                            conditions.addAll(reportConditionBeans);
                            pagerIndex = page;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tvRight.setVisibility(View.VISIBLE);
                                }
                            });
                        }
                        loadFail("");

                    } else {
                        loadFail(jsonObject.optString("message"));
                    }
                } catch (JSONException e) {
                    loadFail("json解析错误");
                    e.printStackTrace();
                } catch (Exception e) {
                    loadFail("数据解析错误");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(IOException e) {
                e.printStackTrace();
                loadFail("未获取到数据");
            }
        });
    }

    /*格式化数据*/
    private void initData(JSONArray jsonArray) throws Exception {
//        Log.e("size", jsonArray.length() + "");
        if (jsonArray.length() > 0) {
            for (int i = 0; i < jsonArray.length(); i++) {
                ReviewStyle reviewStyle = new ReviewStyle();
                List<ReviewInfo> infos = new ArrayList<>();

                for (int j = 0; j < styleList.size(); j++) {
                    String name = jsonArray.getJSONObject(i).optString(styleList.get(j).getsFieldsName());
                    ReviewInfo info = new ReviewInfo();
                    info.setTitle(styleList.get(j).getsFieldsDisplayName());
                    info.setTitleBold((styleList.get(j).getiNameFontBold() == 1) ? true : false);
                    if (StringUtil.isColor(styleList.get(j).getsNameFontColor()))
                        info.setTitleColor(Color.parseColor(styleList.get(j).getsNameFontColor()));
                    info.setContent(jsonArray.getJSONObject(i).optString(styleList.get(j).getsFieldsName()));
                    info.setSingleLine(true);
                    info.setContentBold((styleList.get(j).getiValueFontBold() == 1) ? true : false);
                    if (StringUtil.isColor(styleList.get(j).getsValueFontColor()))
                        info.setContentColor(Color.parseColor(styleList.get(j).getsValueFontColor()));
                    info.setWidthPercent(StringUtil.isPercent(styleList.get(j).getiProportion()));
                    info.setRow(styleList.get(j).getiSerial());
                    infos.add(info);
                }
                reviewStyle.setList(infos);
                items.add(reviewStyle);
//                Log.e("datas", new Gson().toJson(items));
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    refresh();
                }
            });
        } else {
            loadFail("无数据");
        }
    }

    private void getFormData(boolean isRefresh, boolean isLoadMore, int pager) {
        LoadingDialog.showDialogForLoading(this);
        new NetUtil(getFormParams(pager), NetConfig.url + NetConfig.Form_Method, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                try {
                    JSONObject jsonObject = new JSONObject(string);
                    boolean isSuccess = jsonObject.optBoolean("success");
                    if (isSuccess) {
                        if (jsonObject.optString("data").equals("[]")) {
                            loadFail("无数据");
                        } else {
                            pagerIndex = pager;
                            initData(jsonObject.optJSONArray("data"));

                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (isRefresh) {
                                    refreshList.refreshComplete();
                                }
                                if (isLoadMore) {
                                    refreshList.loadMoreComplete();
                                }
                            }
                        });
                        loadFail("");

                    } else {
                        loadFail(jsonObject.getString("message"));
                    }
                } catch (JSONException e) {
                    loadFail("json解析错误");
                    e.printStackTrace();
                } catch (Exception e) {
                    loadFail("数据解析错误");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(IOException e) {
                e.printStackTrace();
                loadFail("未获取到数据");

            }
        });
    }

    private List<NetParams> getFormParams(int pagerIndex) {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("otype", "getReportData"));
        params.add(new NetParams("userid", userId));
        params.add(new NetParams("iFormID", menuId));
        params.add(new NetParams("pageNo", pagerIndex + ""));
        params.add(new NetParams("filters", filter));
        params.add(new NetParams("sort", ""));
        params.add(new NetParams("order", "asc"));
        return params;
    }

    private void refresh() {
        if (formListAdapter == null) {
            formListAdapter = new FormListAdapter(this, items);
            refreshList.addItemDecoration(new MyRecyclerViewDivider(this, LinearLayoutManager.VERTICAL));
            refreshList.setAdapter(formListAdapter);
            refreshList.setLoadingListener(new XRecyclerView.LoadingListener() {
                @Override
                public void onRefresh() {
//                    List<ReviewStyle> styles = new ArrayList<>();
//                    styles.addAll(items);
                    items.removeAll(items);
                    formListAdapter.notifyDataSetChanged();
                    pagerIndex = 1;
                    getFormData(true, false, pagerIndex);
                }

                @Override
                public void onLoadMore() {
                    Log.e("load", "loading"+pagerIndex);
                    getFormData(false, true, pagerIndex+1);
                }
            });
        } else {
            formListAdapter.notifyDataSetChanged();
        }
    }


    /*初始化固定条件*/
    private void initFix(FormBean.ReportInfoBean fixcondition) throws Exception {
        if (StringUtil.isNotEmpty(fixcondition.getSAppFiltersName1())) {
            fixconditions.add(new FormConditionBean(fixcondition.getSAppFiltersName1(), TextUtils.isEmpty(fixcondition.getSAppFilters1()) ? "" : fixcondition.getSAppFilters1()));
        }
        if (StringUtil.isNotEmpty(fixcondition.getSAppFiltersName2())) {
            fixconditions.add(new FormConditionBean(fixcondition.getSAppFiltersName2(), TextUtils.isEmpty(fixcondition.getSAppFilters2()) ? "" : fixcondition.getSAppFilters2()));
        }
        if (StringUtil.isNotEmpty(fixcondition.getSAppFiltersName3())) {
            fixconditions.add(new FormConditionBean(fixcondition.getSAppFiltersName3(), TextUtils.isEmpty(fixcondition.getSAppFilters3()) ? "" : fixcondition.getSAppFilters3()));
        }
        if (StringUtil.isNotEmpty(fixcondition.getSAppFiltersName4())) {
            fixconditions.add(new FormConditionBean(fixcondition.getSAppFiltersName4(), TextUtils.isEmpty(fixcondition.getSAppFilters4()) ? "" : fixcondition.getSAppFilters4()));
        }
    }

    private List<NetParams> getParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("otype", "GetReportInfo"));
        params.add(new NetParams("iFormID", menuId));
        params.add(new NetParams("userid", userId));
        params.add(new NetParams("pageNo", pagerIndex + ""));
        return params;
    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

    /*请求关闭LoadingDialog和弹出提示*/
    private void loadFail(String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LoadingDialog.cancelDialogForLoading();
                if (StringUtil.isNotEmpty(message))
                    Toasts.showShort(FormListActivity.this, message);
            }
        });
    }
}
