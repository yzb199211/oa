package com.huanxin.oa.form;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.huanxin.oa.R;
import com.huanxin.oa.dialog.LoadingDialog;
import com.huanxin.oa.form.model.FormBean;
import com.huanxin.oa.interfaces.ResponseListener;
import com.huanxin.oa.utils.SharedPreferencesHelper;
import com.huanxin.oa.utils.StringUtil;
import com.huanxin.oa.utils.Toasts;
import com.huanxin.oa.utils.net.NetConfig;
import com.huanxin.oa.utils.net.NetParams;
import com.huanxin.oa.utils.net.NetUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FormWithChartActivity extends AppCompatActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;

    String userid;
    String menuid;

    List<FormBean.ReportInfoBean> infoBeans;
    List<FormBean.ReportConditionBean> conditionBeans;
    List<FormBean.ReportColumnsBean> columnsBeans;

    Set<String> fields;
    SharedPreferencesHelper preferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_with_chart);
        ButterKnife.bind(this);
        preferencesHelper = new SharedPreferencesHelper(this, getString(R.string.preferenceCache));
        userid = (String) preferencesHelper.getSharedPreference("userid", "");
        init();
        getData();

    }

    private void init() {
        infoBeans = new ArrayList<>();
        columnsBeans = new ArrayList<>();
        conditionBeans = new ArrayList<>();
        fields = new HashSet<>();

        Intent intent = getIntent();
        menuid = intent.getStringExtra("menuid");
        ivBack.setVisibility(View.VISIBLE);
        tvTitle.setText(intent.getStringExtra("title"));
    }

    /*获取初始化数据*/
    private void getData() {
        LoadingDialog.showDialogForLoading(this);
        new NetUtil(getParams(), NetConfig.url + NetConfig.Form_Method, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                try {
                    JSONObject jsonObject = new JSONObject(string);
                    boolean isSuccess = jsonObject.getBoolean("success");
                    if (isSuccess) {
                        String table = jsonObject.optString("info");
                        String data = jsonObject.optString("olddata");
                        Gson gson = new Gson();
                        FormBean formBean = gson.fromJson(table, FormBean.class);
                        List<FormBean.ReportInfoBean> info = formBean.getReportInfo();
                        List<FormBean.ReportConditionBean> condition = formBean.getReportCondition();
                        List<FormBean.ReportColumnsBean> column = formBean.getReportColumns();
                        if (info != null && info.size() > 0) {
                            infoBeans.addAll(info);
                        }
                        if (condition != null && condition.size() > 0) {
                            conditionBeans.addAll(condition);
                        }
                        if (column != null && column.size() > 0) {
                            columnsBeans.addAll(column);
                        }
                        String field = infoBeans.get(0).getSSerialField();
                        String xValue = infoBeans.get(0).getSXAxisField();
                        String yValue = infoBeans.get(0).getSYAxisField();
                        if (TextUtils.isEmpty(field)) {
                            loadFail("sSerialField为空");
                            return;
                        }
                        if (TextUtils.isEmpty(xValue)) {
                            loadFail("sXAxisField");
                            return;
                        }
                        if (TextUtils.isEmpty(yValue)) {
                            loadFail("sYAxisField为空");
                            return;
                        }
                        JSONArray jsonArray = new JSONArray(data);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            fields.add(jsonArray.getJSONObject(i).optString(field));
                        }



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

            }
        });

    }

    /*获取初始化数据参数*/
    private List<NetParams> getParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("otype", "GetReportInfo"));
        params.add(new NetParams("userid", userid));
        params.add(new NetParams("menuid", menuid));
        return params;
    }

    /*请求关闭LoadingDialog和弹出提示*/
    private void loadFail(String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LoadingDialog.cancelDialogForLoading();
                if (StringUtil.isNotEmpty(message))
                    Toasts.showShort(FormWithChartActivity.this, message);
            }
        });
    }
}
