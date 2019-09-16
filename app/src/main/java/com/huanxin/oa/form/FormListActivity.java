package com.huanxin.oa.form;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.huanxin.oa.R;
import com.huanxin.oa.dialog.LoadingDialog;
import com.huanxin.oa.form.model.FormBean;
import com.huanxin.oa.form.model.FormConditionBean;
import com.huanxin.oa.interfaces.ResponseListener;
import com.huanxin.oa.utils.SharedPreferencesHelper;
import com.huanxin.oa.utils.StringUtil;
import com.huanxin.oa.utils.Toasts;
import com.huanxin.oa.utils.net.NetConfig;
import com.huanxin.oa.utils.net.NetParams;
import com.huanxin.oa.utils.net.NetUtil;
import com.huanxin.oa.view.recycle.RefreshRecycleView;

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
    RefreshRecycleView refreshList;

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

        manager = new LinearLayoutManager(this);
        refreshList.setLayoutManager(manager);

        userId = (String) preferencesHelper.getSharedPreference("userid", "");
        Intent intent = getIntent();
        tvTitle.setText(TextUtils.isEmpty(intent.getStringExtra("title")) ? "" : intent.getStringExtra("titlte"));
        menuId = intent.getStringExtra("menuid");
        ivBack.setVisibility(View.VISIBLE);
        getData();
    }

    /*获取数据*/
    private void getData() {
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
        Log.e("size", jsonArray.length() + "");
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
        params.add(new NetParams("iMenuID", menuId));
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
