package com.huanxin.oa.form;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.format.bg.BaseBackgroundFormat;
import com.bin.david.form.data.format.bg.BaseCellBackgroundFormat;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.data.table.MapTableData;
import com.google.gson.Gson;
import com.huanxin.oa.R;
import com.huanxin.oa.dialog.LoadingDialog;
import com.huanxin.oa.form.model.FormBean;
import com.huanxin.oa.form.model.FormConditionBean;
import com.huanxin.oa.form.utils.JsonHelper;
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

public class FormNewActivity extends AppCompatActivity {
    private final static String TAG = "FormNewActivity";
    private final static int CONDIRION_CODE = 500;
    @BindView(R.id.table)
    SmartTable table;
    @BindView(R.id.ll_tab)
    LinearLayout llTab;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_right)
    TextView tvRight;

    private String menuId;
    private String userId;
    private String filter;

    private String address;
    private String url;

    private List<FormConditionBean> fixconditions;
    private List<FormBean.ReportConditionBean> conditions;

    SharedPreferencesHelper preferencesHelper;
    TabView currentView;
    int currenViewPos = -1;
    int isInterval = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_new);
        ButterKnife.bind(this);
        preferencesHelper = new SharedPreferencesHelper(this, getString(R.string.preferenceCache));
        init();
        getData();
    }

    private void init() {
        userId = (String) preferencesHelper.getSharedPreference("userid", "");
        address = (String) preferencesHelper.getSharedPreference("address", "");
        url = address + NetConfig.server + NetConfig.Form_Method;

        fixconditions = new ArrayList<>();
        conditions = new ArrayList<>();
        Intent intent = getIntent();
        menuId = intent.getStringExtra("menuid");
        tvTitle.setText(TextUtils.isEmpty(intent.getStringExtra("title")) ? "" : intent.getStringExtra("title"));
        ivBack.setVisibility(View.VISIBLE);

    }

    /*获取初始数据*/
    private void getData() {
        LoadingDialog.showDialogForLoading(this);
        new NetUtil(getDataParams(), url, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                try {
                    JSONObject data = new JSONObject(string);
                    boolean isSucess = data.optBoolean("success");
                    if (isSucess) {
                        String formData = data.optString("data");
                        String formInfo = data.getString("info");
                        initData(formData, formInfo);

                        Log.e("formData", formData);
                        Log.e("formData", formInfo);
                        loadFail("");
                    } else {
                        loadFail(data.optString("messages"));
                    }
                } catch (JSONException e) {
                    loadFail("json数据解析错误");
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

    /*获取初始数据参数*/
    private List<NetParams> getDataParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("otype", "GetReportInfo"));
        params.add(new NetParams("iFormID", menuId));
        params.add(new NetParams("userid", userId));
        Log.e("menuid", menuId);
        return params;
    }

    /*初始化数据*/
    private void initData(String formData, String formInfo) throws Exception {
        Gson gson = new Gson();
        FormBean formBean = gson.fromJson(formInfo, FormBean.class);
        List<FormBean.ReportInfoBean> reportInfoBeans = formBean.getReportInfo();
        List<FormBean.ReportConditionBean> reportConditionBeans = formBean.getReportCondition();
//        List<FormBean.ReportColumnsBean> reportColumnsBeans = formBean.getReportColumns();
        if (reportInfoBeans.size() > 0) {
            FormBean.ReportInfoBean fixcondition = reportInfoBeans.get(0);
            isInterval = reportInfoBeans.get(0).getiRowAlternation();
            if (StringUtil.isNotEmpty(fixcondition.getSAppFiltersName1())) {
                fixconditions.add(new FormConditionBean(fixcondition.getSAppFiltersName1(), fixcondition.getSAppFilters1()));
            }
            if (StringUtil.isNotEmpty(fixcondition.getSAppFiltersName2())) {
                fixconditions.add(new FormConditionBean(fixcondition.getSAppFiltersName2(), fixcondition.getSAppFilters2()));
            }
            if (StringUtil.isNotEmpty(fixcondition.getSAppFiltersName3())) {
                fixconditions.add(new FormConditionBean(fixcondition.getSAppFiltersName3(), fixcondition.getSAppFilters3()));
            }
            if (StringUtil.isNotEmpty(fixcondition.getSAppFiltersName4())) {
                fixconditions.add(new FormConditionBean(fixcondition.getSAppFiltersName4(), fixcondition.getSAppFilters4()));
            }
        }
        if (reportConditionBeans.size() > 0) {
            conditions.addAll(reportConditionBeans);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvRight.setVisibility(View.VISIBLE);
                }
            });
        }
        if (StringUtil.isNotEmpty(formData)) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setGsonData(formData);
                    if (fixconditions.size() > 0) {
                        setTab();
                    }
                }
            });
        }
    }

    /*设置固定筛选项*/
    private void setTab() {
        llTab.setVisibility(View.VISIBLE);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) llTab.getLayoutParams();
        params.setMargins(0, 0, 0, 0);
        llTab.setLayoutParams(params);
        for (int i = 0; i < fixconditions.size(); i++) {
            TabView tab = new TabView(this);
            tab.setText(fixconditions.get(i).getName());
            tab.setPosition(i);
            if (i == 0) {
                currenViewPos = i;
                currentView = tab;
            }
            tab.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Log.e("position", "position:" + position);
//                    if (currentView == null) {
//                        currenViewPos = position;
//                        currentView = (TabView) view;
//                        getFormData(position);
//                    } else if (currenViewPos == position) {
//                        currenViewPos = -1;
//                        currentView = null;
//                        getFormData(-1);
//                    } else {
//                        currenViewPos = position;
//                        currentView.setChecked(false);
//                        currentView = (TabView) view;
//                        getFormData(position);
//                    }
                    if (currenViewPos != position) {
                        currenViewPos = position;
                        currentView.setChecked(false);
                        currentView = (TabView) view;
                        currentView.setChecked(true);
                        getFormData(position);
                    }
                }
            });
            llTab.addView(tab);
        }
        currentView.setChecked(true);
    }

    private void getFormData(int pos) {
        LoadingDialog.showDialogForLoading(this);
        new NetUtil(getFormParams(pos), url, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                try {
                    JSONObject jsonObject = new JSONObject(string);
                    boolean isSuccess = jsonObject.optBoolean("success");
                    if (isSuccess) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setGsonData(jsonObject.optString("data"));
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

    private List<NetParams> getFormParams(int pos) {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("otype", "getReportData"));
        params.add(new NetParams("userid", userId));
        params.add(new NetParams("iFormID", menuId));
        if (pos == -1) {
            params.add(new NetParams("filters", ""));
        } else if (pos == -2) {
            params.add(new NetParams("filters", filter));
        } else {
            params.add(new NetParams("filters", fixconditions.get(pos).getFilters()));
        }

        params.add(new NetParams("sort", ""));
        params.add(new NetParams("order", "asc"));
        return params;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (resultCode == CONDIRION_CODE) {
                filter = data.getStringExtra("data");
//                Log.e("filter", filter);
                getFormData(-2);
            }
        }
    }

    private void goCondition() {
        Intent intent = new Intent();
        intent.setClass(this, FormConditionActivity.class);
        intent.putExtra("data", new Gson().toJson(conditions));
        intent.putExtra("code", CONDIRION_CODE);
        startActivityForResult(intent, CONDIRION_CODE);
    }

    //传入json直接形成表单
    private void setGsonData(String data) {
        table.getConfig().setContentCellBackgroundFormat(new BaseCellBackgroundFormat<CellInfo>() {
            @Override
            public int getBackGroundColor(CellInfo cellInfo) {
                if (isInterval == 1) {
                    if (cellInfo.row % 2 == 1) {
                        return ContextCompat.getColor(FormNewActivity.this, R.color.blue1);

                    }
                    return ContextCompat.getColor(FormNewActivity.this, R.color.white);
//                return TableConfig.INVALID_COLOR;
                } else {
                    return ContextCompat.getColor(FormNewActivity.this, R.color.white);
                }
            }

        });
        table.getConfig().setShowTableTitle(false).setShowXSequence(false).setShowYSequence(false);
        table.getConfig().setColumnTitleBackground(new BaseBackgroundFormat(getResources().getColor(R.color.blue)));
        table.getConfig().setColumnTitleStyle(new FontStyle(this, 15, getResources().getColor(R.color.white)).setAlign(Paint.Align.CENTER));
//        table.getConfig().setColumnTitleBackground(new BaseBackgroundFormat(getResources().getColor(R.color.blue)));
        table.getConfig().setVerticalPadding(30);
//        table.getConfig().setHorizontalPadding(30);
        table.getConfig().setColumnTitleVerticalPadding(30);
//        table.getConfig().setColumnTitleHorizontalPadding(30);

        MapTableData tableData = MapTableData.create("", JsonHelper.jsonToMapList(data));
        table.setTableData(tableData);
    }

    /*请求关闭LoadingDialog和弹出提示*/
    private void loadFail(String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LoadingDialog.cancelDialogForLoading();
                if (StringUtil.isNotEmpty(message))
                    Toasts.showShort(FormNewActivity.this, message);
            }
        });
    }
}
