package com.huanxin.oa.form;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
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
import com.huanxin.oa.view.chart.bar.BarCharts;
import com.huanxin.oa.view.chart.line.LineBean;
import com.huanxin.oa.view.chart.line.LineCharts;
import com.huanxin.oa.view.chart.pie.PieCharts;

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
    private final static String TAG = "FormWithChartActivity";

    private final static int CONDIRION_CODE = 500;

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.ll_content)
    LinearLayout llContent;

    GridLayout glForm;

    String userid;
    String menuid;
    String field = "";
    String xValue = "";
    String yValue = "";
    String chartType;
    String filter = "";

    List<FormBean.ReportInfoBean> infoBeans;
    List<FormBean.ReportConditionBean> conditionBeans;
    List<FormBean.ReportColumnsBean> columnsBeans;

    Set<String> fields;
    List<String> titlte;
    List<LineBean> ChartData;
    List<LineBean.Line> pieData;

    SharedPreferencesHelper preferencesHelper;

    private String address;
    String url;

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

        address = (String) preferencesHelper.getSharedPreference("address", "");
        url = address + NetConfig.server + NetConfig.Form_Method;



        infoBeans = new ArrayList<>();
        columnsBeans = new ArrayList<>();
        conditionBeans = new ArrayList<>();
        ChartData = new ArrayList<>();
        pieData = new ArrayList<>();

        fields = new HashSet<>();

        Intent intent = getIntent();
        menuid = intent.getStringExtra("menuid");
        Log.e("menuid", menuid);
        ivBack.setVisibility(View.VISIBLE);
        tvTitle.setText(intent.getStringExtra("title"));
        tvRight.setVisibility(View.VISIBLE);
        getData();
    }

    /*获取初始化数据*/
    private void getData() {
        LoadingDialog.showDialogForLoading(this);
        new NetUtil(getParams(), url, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                try {
                    JSONObject jsonObject = new JSONObject(string);
                    boolean isSuccess = jsonObject.getBoolean("success");
                    if (isSuccess) {
                        String table = jsonObject.optString("info");
                        Log.e("tabledata", table);
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

    LineCharts lineCharts;
    BarCharts barCharts;

    /*设置图表*/
    private void setView() throws Exception {
        switch (chartType) {
            case "0":
                lineCharts = new LineCharts(this);
                lineCharts.setData(ChartData);
                llContent.addView(lineCharts);
                break;
            case "1":
                barCharts = new BarCharts(this);
                barCharts.setData(ChartData);
                barCharts.build();
                llContent.addView(barCharts);
                break;
            case "2":
                for (int i = 0; i < ChartData.size(); i++) {
                    PieCharts pieCharts = new PieCharts(this);
                    pieCharts.setData(ChartData.get(0).getList());
                    pieCharts.setCenterText(TextUtils.isEmpty(ChartData.get(0).getName()) ? "" : ChartData.get(0).getName());
                    pieCharts.build();
                    llContent.addView(pieCharts);
                }
                break;
            default:
                break;
        }
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

    /*初始化表*/
    private void initForm() throws JSONException, Exception {
        glForm = new GridLayout(this);
        glForm.setRowCount(ChartData.get(0).getList().size() + 1);
        glForm.setColumnCount(fields.size() + 1);
        addFormChild(0, 0, "月份", true);
        for (int i = 0; i < ChartData.size(); i++) {
            addFormChild(0, i + 1, ChartData.get(i).getName(), true);
            for (int j = 0; j < ChartData.get(0).getList().size(); j++) {
                if (i == 0) {
                    addFormChild(j + 1, 0, ChartData.get(0).getList().get(j).getxValue(), false);
                }
                addFormChild(j + 1, i + 1, ChartData.get(0).getList().get(j).getyValue() + "", false);
            }
        }

        llContent.addView(glForm);
    }

    private void addFormChild(int row, int col, String text, boolean isTitle) throws JSONException, Exception {
        TextView tvTitle = (TextView) LayoutInflater.from(this).inflate(R.layout.item_form, glForm, false);
        tvTitle.setText(text);
        tvTitle.setGravity(Gravity.CENTER);
        if (isTitle) {
            tvTitle.setBackgroundColor(getColor(R.color.blue));
            tvTitle.setTextColor(getColor(R.color.white));
        }
        GridLayout.Spec rowSpec;
        rowSpec = GridLayout.spec(row, 1.0F);     //设置它的行和列
        GridLayout.Spec columnSpec = GridLayout.spec(col, 1.0F);
        GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);
        params.rightMargin = getResources().getDimensionPixelOffset(R.dimen.dp_1);
        params.topMargin = getResources().getDimensionPixelOffset(R.dimen.dp_1);
        params.leftMargin = getResources().getDimensionPixelOffset(R.dimen.dp_1);
        params.bottomMargin = getResources().getDimensionPixelOffset(R.dimen.dp_1);
        params.height = getResources().getDimensionPixelOffset(R.dimen.dp_30);
        glForm.addView(tvTitle, params);
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
        fields.clear();
        for (int i = 0; i < jsonArray.length(); i++) {
            fields.add(jsonArray.getJSONObject(i).optString(field));
        }
    }

    /*获取数据源*/
    private void getChartData(JSONArray jsonArray) throws JSONException, Exception {
        ChartData.clear();
        for (String value : fields) {
//            Log.e("value", value);
            List<LineBean.Line> line = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                if (jsonArray.getJSONObject(i).optString(field).equals(value)) {
                    LineBean.Line data = new LineBean.Line();
                    data.setxValue(jsonArray.getJSONObject(i).optString(xValue));
                    Object object = jsonArray.getJSONObject(i).opt(yValue);
                    String y = (object == null ? "" : object.toString());
                    Float yValue = Float.parseFloat(y);
                    data.setyValue(yValue);
                    line.add(data);
                }
            }
            LineBean lineBean = new LineBean();
            lineBean.setName(value);
            lineBean.setList(line);
            lineBean.setUnit(infoBeans.get(0).getSYAxisLabelFormatterSimple());
            ChartData.add(lineBean);
        }
        if (ChartData.size() > 0)
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        setView();
                        initForm();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

    }


    /*获取初始化数据参数*/
    private List<NetParams> getParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("otype", Otype.GetChartReportInfo));
        params.add(new NetParams("userid", userid));
        params.add(new NetParams("iFormID", menuid));
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
                goCondition();
                break;
        }
    }

    /*跳转筛选页*/
    private void goCondition() {
        Intent intent = new Intent();
        intent.setClass(this, FormConditionActivity.class);
        intent.putExtra("data", new Gson().toJson(conditionBeans));
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
                llContent.removeAllViews();
                getFormData();
            }
        }
    }

    /*获取表单数据*/
    private void getFormData() {
        LoadingDialog.showDialogForLoading(this);
        new NetUtil(getFormParams(), url, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                try {
                    Log.e("Data", string);
                    JSONObject jsonObject = new JSONObject(string);
                    boolean isSuccess = jsonObject.optBoolean("success");

                    if (isSuccess) {
                        String data = jsonObject.optString("data");
                        if (StringUtil.isNotEmpty(data)) {
                            initData(data);
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    loadFail("");
                                    llContent.removeAllViews();
                                }
                            });
                        }
                    } else {
                        loadFail(jsonObject.optString("message"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    loadFail("json数据解析错误");
                } catch (Exception e) {
                    e.printStackTrace();
                    loadFail("数据解析错误");
                }

            }

            @Override
            public void onFail(IOException e) {
                e.printStackTrace();
                loadFail("获取数据失败");
            }
        });

    }


    private List<NetParams> getFormParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("otype", "getChartReportData"));
        params.add(new NetParams("userid", userid));
        params.add(new NetParams("iFormID", menuid));
        params.add(new NetParams("filters", filter));
        params.add(new NetParams("sort", ""));
        params.add(new NetParams("order", "asc"));
        return params;
    }
}
