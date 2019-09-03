package com.huanxin.oa.form;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
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
import com.huanxin.oa.utils.net.Otype;
import com.huanxin.oa.view.chart.line.LineBean;

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
import butterknife.OnClick;

public class FormWithChartActivity extends AppCompatActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;

    String userid;
    String menuid;
    String field = "";
    String xValue = "";
    String yValue = "";
    String chartType;

    List<FormBean.ReportInfoBean> infoBeans;
    List<FormBean.ReportConditionBean> conditionBeans;
    List<FormBean.ReportColumnsBean> columnsBeans;

    Set<String> fields;
    List<String> titlte;
    List<LineBean> lineORbarData;
    List<LineBean.Line> pieData;

    SharedPreferencesHelper preferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_with_chart);
        ButterKnife.bind(this);
        preferencesHelper = new SharedPreferencesHelper(this, getString(R.string.preferenceCache));
        userid = (String) preferencesHelper.getSharedPreference("userid", "");
        init();


    }

    private void init() {
        infoBeans = new ArrayList<>();
        columnsBeans = new ArrayList<>();
        conditionBeans = new ArrayList<>();
        lineORbarData = new ArrayList<>();
        pieData = new ArrayList<>();

        fields = new HashSet<>();

        Intent intent = getIntent();
        menuid = intent.getStringExtra("menuid");
        Log.e("menuid", menuid);
        ivBack.setVisibility(View.VISIBLE);
        tvTitle.setText(intent.getStringExtra("title"));

        getData();
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
//                        Log.e("tabledata", table);
                        String data = jsonObject.optString("olddata");
                        Gson gson = new Gson();
                        FormBean formBean = gson.fromJson(table, FormBean.class);
                        List<FormBean.ReportInfoBean> info = formBean.getReportInfo();
                        List<FormBean.ReportConditionBean> condition = formBean.getReportCondition();
                        List<FormBean.ReportColumnsBean> column = formBean.getReportColumns();

//                        Log.e("info", new Gson().toJson(info));
//                        Log.e("condition", new Gson().toJson(condition));
//                        Log.e("column", new Gson().toJson(column));
//                        Log.e("info",new Gson().toJson(info));
                        if (info != null && info.size() > 0) {
                            infoBeans.addAll(info);
                            field = infoBeans.get(0).getSSerialField();
                            xValue = infoBeans.get(0).getSXAxisField();
                            yValue = infoBeans.get(0).getSYAxisField();
                            chartType = infoBeans.get(0).getSChartType();
                            if (!TextUtils.isEmpty(chartType))
                                chartType = chartType.split(",")[0];
                        }
                        if (condition != null && condition.size() > 0) {
                            conditionBeans.addAll(condition);
                        }
                        if (column != null && column.size() > 0) {
                            columnsBeans.addAll(column);
                        }
                        if (TextUtils.isEmpty(field)) {
                            loadFail("字段类型未配置");
                        } else if (TextUtils.isEmpty(xValue)) {
                            loadFail("x轴未配置");
                        } else if (TextUtils.isEmpty(yValue)) {
                            loadFail("y轴未配置");
                        } else {
                            initData(data);
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
                e.printStackTrace();
                loadFail("获取数据失败");
            }
        });

    }

    /*数据解析*/
    private void initData(String data) throws JSONException, Exception {
        initChart(data);
//        initForm(data);
        loadFail("");
    }


    /*初始化图数据*/
    private void initChart(String data) throws JSONException, Exception {
        if (isXTypeTrue() == false) {
            loadFail("x轴数据类型错误");
            return;
        }
        if (isYTypeTrue() == false) {
            loadFail("y轴数据类型错误");
            return;
        }
        JSONArray jsonArray = new JSONArray(data);
        getChartFields(jsonArray);
        getChartData(jsonArray);
    }

    /*初始化表数据*/
    private void initForm(String data) throws JSONException, Exception {

    }

    /*判断Y轴数据类型是否正确*/
    private Boolean isYTypeTrue() {
        for (int i = 0; i < columnsBeans.size(); i++) {
            if (yValue.equals(columnsBeans.get(i).getSFieldsName())) {
                String type = columnsBeans.get(i).getSFieldsType();
                if (type.equals("number"))
                    return true;
                else return false;
            }
        }
        return false;
    }

    /*判断X轴数据类型是否正确*/
    private Boolean isXTypeTrue() {
        for (int i = 0; i < columnsBeans.size(); i++) {
            if (xValue.equals(columnsBeans.get(i).getSFieldsName())) {
                String type = columnsBeans.get(i).getSFieldsType();
                if (!type.equals("string") && !type.equals("date") && !type.equals("datetime")) {
                    return false;
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    /*获取图表数据源名字*/
    private void getChartFields(JSONArray jsonArray) throws JSONException, Exception {
        for (int i = 0; i < jsonArray.length(); i++) {
            fields.add(jsonArray.getJSONObject(i).optString(field));
        }
    }

    /*获取数据源*/
    private void getChartData(JSONArray jsonArray) throws JSONException, Exception {
        switch (chartType) {
            case "0":
                getLineORBartData(jsonArray);
                break;
            case "1":
                getLineORBartData(jsonArray);
                break;
            case "2":
                getPieData(jsonArray);
                break;
            default:
                break;
        }
    }

    /*获取线性或柱状数据*/
    private void getLineORBartData(JSONArray jsonArray) throws Exception, JSONException {
        for (String value : fields) {
//            Log.e("value", value);
            List<LineBean.Line> line = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                if (jsonArray.getJSONObject(i).optString(field).equals(value)) {
                    LineBean.Line data = new LineBean.Line();
                    data.setxValue(jsonArray.getJSONObject(i).optString(xValue));
                    String y = jsonArray.getJSONObject(i).opt(yValue).toString();
                    Float yValue = Float.parseFloat(y);
                    data.setyValue(yValue);
                    line.add(data);
                }
            }
            LineBean lineBean = new LineBean();
            lineBean.setName(value);
            lineBean.setList(line);
            lineBean.setUnit(infoBeans.get(0).getSYAxisLabelFormatterSimple());
            lineORbarData.add(lineBean);
        }
        Log.e("lineData", new Gson().toJson(lineORbarData));
    }

    /*获取饼图数据*/
    private void getPieData(JSONArray jsonArray) {

    }

    /*获取初始化数据参数*/
    private List<NetParams> getParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("otype", Otype.GetChartReportInfo));
        params.add(new NetParams("userid", userid));
        params.add(new NetParams("iMenuID", menuid));
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

    @OnClick({R.id.iv_back, R.id.tv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_right:
                break;
        }
    }
}
