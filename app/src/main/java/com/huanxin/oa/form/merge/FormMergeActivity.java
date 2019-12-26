package com.huanxin.oa.form.merge;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.huanxin.oa.R;
import com.huanxin.oa.dialog.LoadingDialog;
import com.huanxin.oa.form.FormConditionActivity;
import com.huanxin.oa.form.FormNewActivity;
import com.huanxin.oa.form.model.FormBean;
import com.huanxin.oa.form.model.FormConditionBean;
import com.huanxin.oa.interfaces.ResponseListener;
import com.huanxin.oa.main.interfaces.OnItemClickListener;
import com.huanxin.oa.utils.SharedPreferencesHelper;
import com.huanxin.oa.utils.StringUtil;
import com.huanxin.oa.utils.Toasts;
import com.huanxin.oa.utils.net.NetConfig;
import com.huanxin.oa.utils.net.NetParams;
import com.huanxin.oa.utils.net.NetUtil;
import com.huanxin.oa.view.tab.TabView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FormMergeActivity extends AppCompatActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.ll_tab)
    LinearLayout llTab;

    SharedPreferencesHelper preferencesHelper;

    String address;
    String url;
    String userid;
    String formid;
    String filter;
    private String fixfilter = "";


    boolean isStore;

    private List<FormConditionBean> fixconditions;
    private List<FormBean.ReportConditionBean> conditions;
    private List<FormBean.ReportColumnsBean> columnsBeans;
    FormMerge formMerge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_merge);
        ButterKnife.bind(this);
        preferencesHelper = new SharedPreferencesHelper(this, getString(R.string.preferenceCache));
        init();
    }

    private void init() {
        initList();
        getDefaultData();
        initView();
        getData();
    }

    private void initView() {
        tvTitle.setText(TextUtils.isEmpty(getIntent().getStringExtra("title")) ? "" : getIntent().getStringExtra("title"));
        ivBack.setVisibility(View.VISIBLE);
    }

    private void initList() {
        fixconditions = new ArrayList<>();
        conditions = new ArrayList<>();
        columnsBeans = new ArrayList<>();
    }


    private void getDefaultData() {
        userid = (String) preferencesHelper.getSharedPreference("userid", "");
        address = (String) preferencesHelper.getSharedPreference("address", "");
        url = address + NetConfig.server + NetConfig.Form_Method;
        formid = getIntent().getStringExtra("menuid");
    }

    private List<NetParams> getParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("otype", "GetChartReportInfo"));
        params.add(new NetParams("iFormID", formid));
        params.add(new NetParams("userid", userid));
        return params;
    }

    private void getData() {
        LoadingDialog.showDialogForLoading(this);
        new NetUtil(getParams(), url, new ResponseListener() {
            @Override
            public void onSuccess(String string) {

                try {
                    JSONObject jsonObject = new JSONObject(string);
                    if (jsonObject.optBoolean("success")) {
                        initData(jsonObject.optString("info"), jsonObject.optString("olddata"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    FinishLoading(getString(R.string.error_json));
                } catch (Exception e) {
                    e.printStackTrace();
                    FinishLoading(getString(R.string.error_data));
                }

            }

            @Override
            public void onFail(IOException e) {
                e.printStackTrace();
                FinishLoading(e.getMessage());
            }
        });

    }

    private void initData(String s, String data) throws Exception {
        FormBean formBean = new Gson().fromJson(s, FormBean.class);
        List<FormBean.ReportColumnsBean> columns = formBean.getReportColumns();
        List<FormBean.ReportConditionBean> reportConditionBeans = formBean.getReportCondition();
        List<FormBean.ReportInfoBean> reportInfoBeans = formBean.getReportInfo();
        columnsBeans.addAll(columns);
        Log.e("cloums", new Gson().toJson(columns));
        initFix(reportInfoBeans.get(0));
        initCondition(reportConditionBeans);
        setView(columns, data);

    }

    private void setView(List<FormBean.ReportColumnsBean> columns, String data) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (fixconditions.size() > 0) {
                    setFix();
                }
                if (conditions.size() > 0) {
                    tvRight.setVisibility(View.VISIBLE);
                }
                if (columns != null && columns.size() > 0 && StringUtil.isNotEmpty(data)) {
                    initForm(columns, data);
                }
                FinishLoading(null);
            }
        });
    }

    private void initForm(List<FormBean.ReportColumnsBean> columns, String data) {
        try {

            formMerge = new FormMerge(this);
            formMerge.setColumnsTitle(columns);
            formMerge.setData(data);
            formMerge.build();
            llContent.addView(formMerge);
        } catch (Exception e) {
            e.printStackTrace();
            FinishLoading(getString(R.string.error_data));
        }

    }

    private void setFix() {
        llTab.setVisibility(View.VISIBLE);
        for (int i = 0; i < fixconditions.size(); i++) {
            TabView tabView = getTabView(i);
            llTab.addView(tabView);
        }
    }

    TabView currentView;
    int currenViewPos = -1;


    private TabView getTabView(int pos) {
        TabView tab = new TabView(this);
        tab.setText(fixconditions.get(pos).getName());
        tab.setPosition(pos);
        tab.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (currenViewPos != position) {
                    currenViewPos = position;
                    currentView.setChecked(false);
                    currentView = (TabView) view;
                    currentView.setChecked(true);
                    fixfilter = fixconditions.get(position).getFilters();
                    getFormData();
                }
            }
        });
        return tab;
    }

    private void getFormData() {
        if (formMerge != null) {
            llContent.removeView(formMerge);
            formMerge = null;
        }
        LoadingDialog.showDialogForLoading(this);
        new NetUtil(getFormParams(), url, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                try {
                    JSONObject jsonObject = new JSONObject(string);
                    boolean isSuccess = jsonObject.optBoolean("success");
                    if (isSuccess) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                initForm(columnsBeans, jsonObject.optString("data"));
//                                setGsonData(jsonObject.optString("data"));
                                loadFail("");
                            }
                        });

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

    private List<NetParams> getFormParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("otype", "getReportData"));
        params.add(new NetParams("userid", userid));
        params.add(new NetParams("iFormID", formid));

        if (StringUtil.isNotEmpty(fixfilter) && StringUtil.isNotEmpty(filter)) {
            params.add(new NetParams("filters", filter + " and " + fixfilter));
        } else {
            params.add(new NetParams("filters", filter + fixfilter));
        }
        params.add(new NetParams("sort", ""));
        params.add(new NetParams("order", "asc"));
        return params;
    }

    private void initCondition(List<FormBean.ReportConditionBean> reportConditionBeans) {
        if (reportConditionBeans != null && reportConditionBeans.size() > 0) {
            conditions.addAll(reportConditionBeans);
        }
    }

    /*初始化固定条件*/
    private void initFix(FormBean.ReportInfoBean fixcondition) throws Exception {
        isStore = fixcondition.getiStore() == 0 ? false : true;
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

    @OnClick({R.id.iv_back, R.id.tv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_right:
                goCondition();
                break;
        }
    }

    private final static int CONDIRION_CODE = 500;

    private void goCondition() {
        Intent intent = new Intent();
        intent.setClass(this, FormConditionActivity.class);
        intent.putExtra("data", new Gson().toJson(conditions));
        intent.putExtra("code", CONDIRION_CODE);
        startActivityForResult(intent, CONDIRION_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (resultCode == CONDIRION_CODE) {
                filter = data.getStringExtra("data");
                Log.e("filter", filter);
                getFormData();
            }
        }
    }

    private void FinishLoading(String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LoadingDialog.cancelDialogForLoading();
                if (StringUtil.isNotEmpty(message))
                    Toasts.showShort(FormMergeActivity.this, message);
            }
        });
    }

    /*请求关闭LoadingDialog和弹出提示*/
    private void loadFail(String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LoadingDialog.cancelDialogForLoading();
                if (StringUtil.isNotEmpty(message))
                    Toasts.showShort(FormMergeActivity.this, message);
            }
        });
    }
}
