package com.huanxin.oa.form;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.huanxin.oa.R;
import com.huanxin.oa.dialog.LoadingDialog;
import com.huanxin.oa.form.model.FormBean;
import com.huanxin.oa.form.model.FormChartBean;
import com.huanxin.oa.form.model.FormConditionBean;
import com.huanxin.oa.form.utils.FormUtil;
import com.huanxin.oa.interfaces.ResponseListener;
import com.huanxin.oa.main.interfaces.OnItemClickListener;
import com.huanxin.oa.utils.SharedPreferencesHelper;
import com.huanxin.oa.utils.StringUtil;
import com.huanxin.oa.utils.Toasts;
import com.huanxin.oa.utils.net.NetConfig;
import com.huanxin.oa.utils.net.NetParams;
import com.huanxin.oa.utils.net.NetUtil;
import com.huanxin.oa.utils.net.Otype;
import com.huanxin.oa.view.chart.ChartBean;
import com.huanxin.oa.view.chart.bar.BarCharts;
import com.huanxin.oa.view.chart.bar.BarHorCharts;
import com.huanxin.oa.view.chart.bar.BarStackCharts;
import com.huanxin.oa.view.chart.line.LineCharts;
import com.huanxin.oa.view.chart.line.LineCubicCharts;
import com.huanxin.oa.view.chart.pie.PieCharts;
import com.huanxin.oa.view.chart.radar.RadarCharts;
import com.huanxin.oa.view.tab.TabView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    @BindView(R.id.ll_tab)
    LinearLayout llTab;
    @BindView(R.id.ll_child)
    LinearLayout llChild;
    @BindView(R.id.scroll)
    ScrollView scrollView;
    GridLayout glForm;

    String userid;
    String menuid;
    String field = "";
    String xValue = "";
    String xName = "";
    String yValue = "";
    String chartType;
    String filter = "";
    String title = "";
    String fixfilter = "";

    boolean haveChild;
    boolean childIsStore;
    boolean isShowChildChart;
    String childLink;
    String childField = "";
    String childXValue = "";
    String childYValue = "";
    String childXName = "";
    String childChartType;
    String childFilter = "";
    int childChartHeight;

    TabView currentView;
    int currenViewPos = -1;

    List<FormBean.ReportInfoBean> infoBeans;
    List<FormBean.ReportConditionBean> conditionBeans;
    List<FormBean.ReportColumnsBean> columnsBeans;
    List<FormConditionBean> fixconditions;

    List<String> fields;
    List<String> childFields;
    List<String> titlte;
    List<ChartBean> ChartData;
    List<ChartBean> ChildChartData;
    List<ChartBean.Line> pieData;


    SharedPreferencesHelper preferencesHelper;

    private String address;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_with_chart);
        ButterKnife.bind(this);
        preferencesHelper = new SharedPreferencesHelper(this, getString(R.string.preferenceCache));
        init();
    }

    private void init() {
        intiList();
        getPreferenceData();
        getIntentData();
        initView();
        url = address + NetConfig.server + NetConfig.Form_Method;
        Log.e("menuid", menuid);
        getData();
    }


    private void intiList() {
        infoBeans = new ArrayList<>();
        columnsBeans = new ArrayList<>();
        conditionBeans = new ArrayList<>();
        fixconditions = new ArrayList<>();
        ChartData = new ArrayList<>();
        ChildChartData = new ArrayList<>();
        pieData = new ArrayList<>();

        fields = new ArrayList<>();
        childFields = new ArrayList<>();
    }

    private void getPreferenceData() {
        userid = (String) preferencesHelper.getSharedPreference("userid", "");
        address = (String) preferencesHelper.getSharedPreference("address", "");
    }

    private void getIntentData() {
        Intent intent = getIntent();
        menuid = intent.getStringExtra("menuid");
        title = intent.getStringExtra("title");
    }

    private void initView() {
        ivBack.setVisibility(View.VISIBLE);
        tvTitle.setText(title);

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
                        getFixconditions(info);

                        getAxisField(info);

                        getCoditionData(condition);
                        getColumnData(column);
                        getXAxisName();
                        initChartType();
                        if (info.size() > 0) {
                            getChildInfo(info.get(0));
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


    private void getChildInfo(FormBean.ReportInfoBean info) {
        haveChild = info.haveChild();
        if (!haveChild) {
            return;
        }

        isShowChildChart = info.isShowChildChart();
        childChartType = info.getsChildChartType();
        childLink = getChildField(info.getsLinkField());
        Log.e("link", childLink);
        childField = info.getsChildSerialField();
        childIsStore = info.childIsStore();
        childXValue = info.getsChildXAxisField();
        childYValue = info.getsChildYAxisField();
        getChildXAxisName();

    }

    private String getChildField(String field) {
        if (field.contains("=")) {
            return field.split("=")[1];
        }
        return "";
    }

    private void getFixconditions(List<FormBean.ReportInfoBean> info) {
        if (info.size() > 0) {
            FormBean.ReportInfoBean fixcondition = info.get(0);
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
        setFixConditions();
    }

    private void setFixConditions() {
        if (fixconditions.size() > 0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setTab();
                }
            });
        }
    }

    /*设置固定筛选项*/
    private void setTab() {
        llTab.setVisibility(View.VISIBLE);
        for (int i = 0; i < fixconditions.size(); i++) {
            TabView tab = new TabView(this);
            tab.setText(fixconditions.get(i).getName());
            tab.setPosition(i);
            if (i == 0) {
                currenViewPos = i;
                currentView = tab;
            }
            setTabClick(tab);

            llTab.addView(tab);
        }
        currentView.setChecked(true);
    }

    private void setTabClick(TabView tab) {
        tab.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                if (currenViewPos != position) {
                    currenViewPos = position;
                    currentView.setChecked(false);
                    currentView = (TabView) view;
                    currentView.setChecked(true);
                    fixfilter = fixconditions.get(position).getFilters();
                    llContent.removeAllViews();
                    llChild.removeAllViews();
                    getFormData();
                }
            }
        });
    }

    private void getXAxisName() {
        for (FormBean.ReportColumnsBean column : columnsBeans) {
            if (xValue.equals(column.getSFieldsName()) && !column.isChild()) {
                xName = column.getSFieldsdisplayName();
                break;
            }
        }
    }

    private void getChildXAxisName() {
        for (FormBean.ReportColumnsBean column : columnsBeans) {
//            Log.e("xmana",column.getSFieldsName());
            if (childXValue.equals(column.getSFieldsName()) && column.isChild()) {
                childXName = column.getSFieldsdisplayName();
                Log.e("chidldxmana", childXName);
                break;
            }
        }
    }

    private void getColumnData(List<FormBean.ReportColumnsBean> column) {
        if (column != null && column.size() > 0) {
            columnsBeans.addAll(column);
        }
    }

    private void getCoditionData(List<FormBean.ReportConditionBean> condition) {
        if (condition != null && condition.size() > 0) {
            conditionBeans.addAll(condition);
            setTitleRight();
        }
    }

    private void setTitleRight() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvRight.setVisibility(View.VISIBLE);
            }
        });
    }

    private void getAxisField(List<FormBean.ReportInfoBean> info) {
        if (info != null && info.size() > 0) {
            infoBeans.addAll(info);
            field = infoBeans.get(0).getSSerialField();

            xValue = infoBeans.get(0).getSXAxisField();
            yValue = infoBeans.get(0).getSYAxisField();
            chartType = infoBeans.get(0).getSChartType();
        }
    }

    private void initChartType() {
        if (!TextUtils.isEmpty(chartType))
            chartType = chartType.split(",")[0];
    }

    /*数据解析*/
    private void initData(String data) throws JSONException, Exception {
        initChart(data);
//        initForm(data);
        loadFail("");
    }

    LineCharts lineCharts;
    LineCubicCharts lineCubicCharts;
    BarCharts barCharts;
    BarHorCharts barHorCharts;
    BarStackCharts barStackCharts;
    RadarCharts radarCharts;

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
                    break;
                }
                break;

            case "3":
                lineCubicCharts = new LineCubicCharts(this);
                lineCubicCharts.setData(ChartData);
                lineCubicCharts.build();
                llContent.addView(lineCubicCharts);
                break;
            case "4":
                barHorCharts = new BarHorCharts(this);
                barHorCharts.setData(ChartData);
                barHorCharts.build();
                llContent.addView(barHorCharts);
                break;
            case "5":
                radarCharts = new RadarCharts(this);
                radarCharts.setData(ChartData);
                radarCharts.build();
                llContent.addView(radarCharts);
                break;
            case "6":
                barStackCharts = new BarStackCharts(this);
                barStackCharts.setData(ChartData);
                barStackCharts.build();
                llContent.addView(barStackCharts);
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
    private void initChildForm() throws JSONException, Exception {
        GridLayout glchildForm = new GridLayout(this);
        glchildForm.setRowCount(ChildChartData.get(0).getList().size() + 2);
        glchildForm.setColumnCount(childFields.size() + 1);
        setChildFormRowName(glchildForm);
        int length = ChildChartData.get(0).getList().size();
        for (int i = 0; i < ChildChartData.size(); i++) {
            setChildFormColumnName(i + 1, glchildForm);
            List<ChartBean.Line> datas = new ArrayList<>();
            datas.addAll(ChildChartData.get(i).getList());
            setChildFormData(length, datas, i, glchildForm);
        }
        for (int j = 0; j < ChildChartData.size(); j++) {
            setChildtFormTotaData(j + 1, StringUtil.float2Str(ChildChartData.get(j).getTotal()), glchildForm);
        }
        setChildFormRowTotalName(glchildForm);
        llChild.addView(glchildForm);
//        glchildForm.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                scrollView.scrollTo(0, llContent.getHeight());
//            }
//        });

    }

    private void setChildtFormTotaData(int i, String data, GridLayout glchildForm) throws JSONException, Exception {
//        addFormTotalChild(ChartData.get(0).getList().size() + 1, i, data, false, R.color.form_menu_total);
        FormChartBean style = new FormChartBean().setRow(ChildChartData.size() + 1).setCol(i).setText(data).setTypeface(Typeface.DEFAULT_BOLD).setTextColor(R.color.default_text_color).setBackgroundColor(R.color.form_menu_total).setGravity(Gravity.CENTER_HORIZONTAL);
        glchildForm.addView(FormUtil.getColumn(this, style), getChildParam(ChildChartData.get(0).getList().size() + 1, i));
    }

    private void setChildFormRowTotalName(GridLayout glchildForm) throws JSONException, Exception {
//        addFormTotalChild(ChartData.get(0).getList().size() + 1, 0, "总计", false, R.color.form_menu_total);
        FormChartBean style = new FormChartBean().setRow(ChildChartData.size() + 1).setCol(0).setText("总计").setTypeface(Typeface.DEFAULT_BOLD).setTextColor(R.color.default_text_color).setBackgroundColor(R.color.form_menu_total).setGravity(Gravity.CENTER_HORIZONTAL);
        glchildForm.addView(FormUtil.getColumn(this, style), getChildParam(ChildChartData.get(0).getList().size() + 1, 0));
    }

    private void setChildFormColumnName(int i, GridLayout glchildForm) throws JSONException, Exception {
//        addChildFormChild(0, i, ChildChartData.get(i - 1).getName(), true, glchildForm);
        FormChartBean style = new FormChartBean().setRow(0).setCol(i).setText(ChildChartData.get(i - 1).getName()).setTextColor(R.color.white).setBackgroundColor(R.color.blue).setGravity(Gravity.CENTER_HORIZONTAL);
        glchildForm.addView(FormUtil.getColumn(this, style), getChildParam(0, i));
    }

    private void setChildFormRowName(GridLayout glchildForm) throws JSONException, Exception {
//        addChildFormChild(0, 0, childXName, true, glchildForm);
        FormChartBean style = new FormChartBean().setRow(0).setCol(0).setText(childXName).setBackgroundColor(R.color.blue).setGravity(Gravity.CENTER_HORIZONTAL).setTextColor(R.color.white);
        glchildForm.addView(FormUtil.getColumn(this, style), getChildParam(0, 0));
    }

    private void setChildFormData(int length, List<ChartBean.Line> datas, int i, GridLayout glchildForm) throws JSONException, Exception {
        for (int j = 0; j < length; j++) {
            if (datas.size() < length) {
                datas.add(new ChartBean.Line());
            }


            if (i == 0) {
                FormChartBean style = new FormChartBean().setRow(j + 1).setCol(0).setText(datas.get(j).getxValue()).setBackgroundColor(R.color.form_menu_item).setGravity(Gravity.CENTER_HORIZONTAL).setTextColor(R.color.form_menu_title);
                glchildForm.addView(FormUtil.getColumn(this, style), getChildParam(j + 1, 0));
//                addFormTotalChild(j + 1, 0, datas.get(j).getxValue(), false, R.color.form_menu_item);
            }
//            addFormChild(j + 1, i + 1, datas.get(j).getyValue() + "", false);
            FormChartBean style = new FormChartBean().setRow(j + 1).setCol(i + 1).setText(StringUtil.float2Str(datas.get(j).getyValue())).setBackgroundColor(R.color.form_menu_white).setGravity(Gravity.CENTER_HORIZONTAL).setTextColor(R.color.default_content_color);
            glchildForm.addView(FormUtil.getColumn(this, style), getChildParam(j + 1, i + 1));

//            if (i == 0) {
//                addChildFormChild(j + 1, 0, datas.get(j).getxValue(), false, glchildForm);
//            }
//            addChildFormChild(j + 1, i + 1, datas.get(j).getyValue() + "", false, glchildForm);
        }
    }

    private void addChildFormChild(int row, int col, String text, boolean isTitle, GridLayout glchildForm) throws Exception {
        TextView textView = getChildView(text, isTitle, glchildForm);
        glchildForm.addView(textView, getChildParam(row, col));

    }

    private void judgeChild(String s) {

        if (TextUtils.isEmpty(childField)) {
            loadFail("字段类型未配置");
        } else if (TextUtils.isEmpty(childXValue)) {
            loadFail("x轴未配置");
        } else if (TextUtils.isEmpty(childYValue)) {
            loadFail("y轴未配置");
        } else {
            getChildData(s);
        }

    }


    private TextView getChildView(String text, boolean isTitle, GridLayout glForm) throws Exception {
        TextView tvTitle = (TextView) LayoutInflater.from(this).inflate(R.layout.item_form, glForm, false);
        tvTitle.setText(text);
        tvTitle.setGravity(Gravity.CENTER);
        if (isTitle) {
            tvTitle.setBackgroundColor(getColor(R.color.blue));
            tvTitle.setTextColor(getColor(R.color.white));
        }
        return tvTitle;
    }

    private GridLayout.LayoutParams getChildParam(int row, int col) throws Exception {
        GridLayout.Spec rowSpec;
        rowSpec = GridLayout.spec(row, 1.0F);     //设置它的行和列
        GridLayout.Spec columnSpec = GridLayout.spec(col, 1.0F);
        GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);
        setChildMaring(row, params);
        setChildHeight(params);
        return params;
    }

    private void setChildHeight(GridLayout.LayoutParams params) {
        params.height = getResources().getDimensionPixelOffset(R.dimen.dp_30);
    }

    private void setChildMaring(int row, GridLayout.LayoutParams params) {
        params.rightMargin = getResources().getDimensionPixelOffset(R.dimen.dp_1);
        if (row == 0) {
            params.topMargin = 0;
        } else {
            params.topMargin = getResources().getDimensionPixelOffset(R.dimen.dp_1);
        }
        params.leftMargin = getResources().getDimensionPixelOffset(R.dimen.dp_1);
        params.bottomMargin = getResources().getDimensionPixelOffset(R.dimen.dp_1);

    }

    /*判断Y轴数据类型是否正确*/
    private Boolean isYTypeTrue() {
        for (int i = 0; i < columnsBeans.size(); i++) {
            if (yValue.equals(columnsBeans.get(i).getSFieldsName()) && !columnsBeans.get(i).isChild()) {
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
            if (xValue.equals(columnsBeans.get(i).getSFieldsName()) && !columnsBeans.get(i).isChild()) {
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
//        Log.e("fileds",jsonArray.optString(0));
        for (int i = 0; i < jsonArray.length(); i++) {
            if (!fields.contains(jsonArray.getJSONObject(i).optString(field)))
                fields.add(jsonArray.getJSONObject(i).optString(field));
        }
    }

    /*获取数据源*/
    private void getChartData(JSONArray jsonArray) throws JSONException, Exception {
        ChartData.clear();
        Log.e("fields", new Gson().toJson(fields));
        setChartData(jsonArray);
        initChartData();

    }

    private void initChartData() {

        /*数据处理为相同长度*/
        if (ChartData.size() > 0) {
            makeChartDataSameLength();
//            Log.e("TAG", new Gson().toJson(ChartData));
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
    }

    private void makeChartDataSameLength() {
        int length = ChartData.get(0).getList().size();
        for (int i = 0; i < ChartData.size(); i++) {
            if (length > ChartData.get(i).getList().size()) {
                chartDataAddLength(length, i);

            } else {
                chartDataReduceLength(length, i);

            }
        }
    }

    private void chartDataAddLength(int length, int i) {
        for (int j = 0; j < length - ChartData.get(i).getList().size(); j++) {
            ChartData.get(i).getList().add(new ChartBean.Line(ChartData.get(0).getList().get(ChartData.get(i).getList().size()).getxValue(), 0));
        }
    }

    private void chartDataReduceLength(int length, int i) {
        if (length < ChartData.get(i).getList().size()) {
            for (int k = 0; k < ChartData.get(i).getList().size() - length; k++) {
                ChartData.get(i).getList().remove(ChartData.size() - 1);
            }
        }
    }

    private void setChartData(JSONArray jsonArray) throws JSONException, Exception {
        for (String value : fields) {
            List<ChartBean.Line> line = new ArrayList<>();
            setCharChildData(jsonArray, line, value);
            ChartDataAddValue(value, line);

        }
    }

    private void ChartDataAddValue(String value, List<ChartBean.Line> line) {
        ChartBean chartBean = new ChartBean();
        chartBean.setName(value);
        chartBean.setList(line);
        chartBean.setUnit(infoBeans.get(0).getSYAxisLabelFormatterSimple());
        ChartData.add(chartBean);
    }

    private void setCharChildData(JSONArray jsonArray, List<ChartBean.Line> line, String value) throws JSONException, Exception {
        for (int i = 0; i < jsonArray.length(); i++) {
            if (jsonArray.getJSONObject(i).optString(field).equals(value)) {
                ChartBean.Line data = new ChartBean.Line();
                data.setxValue(jsonArray.getJSONObject(i).optString(xValue));
                Object object = jsonArray.getJSONObject(i).opt(yValue);
                String y = (object == null ? "" : object.toString());
                Float yValue = Float.parseFloat(y);
                data.setyValue(yValue);
                line.add(data);
            }
        }
    }


    /*获取初始化数据参数*/
    private List<NetParams> getParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("otype", Otype.GetChartReportInfo));
        params.add(new NetParams("userid", userid));
        params.add(new NetParams("iFormID", menuid));

        return params;
    }

    /*获取关联数据*/
    private void getChildData(String s) {
        LoadingDialog.showDialogForLoading(this);
        new NetUtil(getChildParams(s), url, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                Log.e("Data", string);
                try {
                    JSONObject jsonObject = new JSONObject(string);
                    boolean isSuccess = jsonObject.optBoolean("success");
                    if (isSuccess) {
                        String data = jsonObject.optString("data");
                        if (StringUtil.isNotEmpty(data)) {
                            initChildData(data);
                            loadFail("");
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    loadFail("");
                                    llChild.removeAllViews();
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

    private void initChildData(String data) throws JSONException, Exception {
        if (isChildXTypeTrue() == false) {
            loadFail("x轴数据类型错误");
            return;
        }
        if (isChildYTypeTrue() == false) {
            loadFail("y轴数据类型错误");
            return;
        }
        JSONArray jsonArray = new JSONArray(data);
        getChildChartFields(jsonArray);
        getChildChartData(jsonArray);
    }

    /*获取关联图表数据源名字*/
    private void getChildChartFields(JSONArray jsonArray) throws JSONException, Exception {
        childFields.clear();
//        Log.e("fileds",jsonArray.optString(0));
        for (int i = 0; i < jsonArray.length(); i++) {
            if (!childFields.contains(jsonArray.getJSONObject(i).optString(childField)))
                childFields.add(jsonArray.getJSONObject(i).optString(childField));
        }
    }/*获取数据源*/

    private void getChildChartData(JSONArray jsonArray) throws JSONException, Exception {
        ChildChartData.clear();
        Log.e("fields", new Gson().toJson(childFields));
        setChildChartData(jsonArray);
        initChildChartData();

    }

    private void initChildChartData() {

        /*数据处理为相同长度*/
        if (ChildChartData.size() > 0) {
            makeChildChartDataSameLength();
            Log.e("TAG", new Gson().toJson(ChildChartData));
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (isShowChildChart)
                            setChildView();
                        initChildForm();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /*初始化表*/
    private void initForm() throws JSONException, Exception {
        glForm = new GridLayout(this);
        glForm.setBackgroundColor(getColor(R.color.form_menu_bg));
        glForm.setRowCount(ChartData.get(0).getList().size() + 2);
        glForm.setColumnCount(fields.size() + 1);
        setFormRowName();
        int length = ChartData.get(0).getList().size();
        for (int i = 0; i < ChartData.size(); i++) {
            setFormColumnName(i + 1);
            List<ChartBean.Line> datas = new ArrayList<>();
            datas.addAll(ChartData.get(i).getList());
            setFormData(length, datas, i);
        }
        for (int j = 0; j < ChartData.size(); j++) {
            setFormTotaData(j + 1, StringUtil.float2Str(ChartData.get(j).getTotal()));
        }
        setFormRowTotalName();
        llContent.addView(glForm);
    }

    private void setFormTotaData(int i, String data) throws JSONException, Exception {
//        addFormTotalChild(ChartData.get(0).getList().size() + 1, i, data, false, R.color.form_menu_total);
        FormChartBean style = new FormChartBean().setRow(ChartData.get(0).getList().size() + 1).setCol(i).setText(data).setTypeface(Typeface.DEFAULT_BOLD).setTextColor(R.color.default_text_color).setBackgroundColor(R.color.form_menu_total).setGravity(Gravity.CENTER_HORIZONTAL);
        glForm.addView(FormUtil.getColumn(this, style), getChildParam(ChartData.get(0).getList().size() + 1, i));
    }

    private void setFormRowTotalName() throws JSONException, Exception {
//        addFormTotalChild(ChartData.get(0).getList().size() + 1, 0, "总计", false, R.color.form_menu_total);
        FormChartBean style = new FormChartBean().setRow(ChartData.get(0).getList().size() + 1).setCol(0).setText("总计").setTypeface(Typeface.DEFAULT_BOLD).setTextColor(R.color.default_text_color).setBackgroundColor(R.color.form_menu_total).setGravity(Gravity.CENTER_HORIZONTAL);
        glForm.addView(FormUtil.getColumn(this, style), getChildParam(ChartData.get(0).getList().size() + 1, 0));
    }

    private void setFormColumnName(int i) throws JSONException, Exception {
//        addFormChild(0, i, ChartData.get(i - 1).getName(), true);

        FormChartBean style = new FormChartBean().setRow(0).setCol(i).setText(ChartData.get(i - 1).getName()).setTextColor(R.color.white).setBackgroundColor(R.color.blue).setGravity(Gravity.CENTER_HORIZONTAL);
        glForm.addView(FormUtil.getColumn(this, style), getChildParam(0, i));
    }

    private void setFormRowName() throws JSONException, Exception {
//        addFormChild(0, 0, xName, true);

        FormChartBean style = new FormChartBean().setRow(0).setCol(0).setText(xName).setBackgroundColor(R.color.blue).setGravity(Gravity.CENTER_HORIZONTAL).setTextColor(R.color.white);
        glForm.addView(FormUtil.getColumn(this, style), getChildParam(0, 0));
    }

    private void setFormData(int length, List<ChartBean.Line> datas, int i) throws JSONException, Exception {
        for (int j = 0; j < length; j++) {
            if (datas.size() < length) {
                datas.add(new ChartBean.Line());
            }
            if (i == 0) {
                FormChartBean style = new FormChartBean().setRow(j + 1).setCol(0).setText(datas.get(j).getxValue()).setBackgroundColor(R.color.form_menu_item).setGravity(Gravity.CENTER_HORIZONTAL).setTextColor(R.color.form_menu_title);
                TextView textView = FormUtil.getColumn(this, style);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        llChild.removeAllViews();
                        judgeChild(((TextView) v).getText().toString());
                    }
                });
//                glForm.addView(FormUtil.getColumn(this, style), getChildParam(j + 1, 0));
                glForm.addView(textView, getChildParam(j + 1, 0));
//                addFormTotalChild(j + 1, 0, datas.get(j).getxValue(), false, R.color.form_menu_item);
            }
//            addFormChild(j + 1, i + 1, datas.get(j).getyValue() + "", false);
            FormChartBean style = new FormChartBean().setRow(j + 1).setCol(i + 1).setText(StringUtil.float2Str(datas.get(j).getyValue())).setBackgroundColor(R.color.form_menu_white).setGravity(Gravity.CENTER_HORIZONTAL).setTextColor(R.color.default_content_color);
            glForm.addView(FormUtil.getColumn(this, style), getChildParam(j + 1, i + 1));
        }
    }

    private void addFormChild(int row, int col, String text, boolean isTitle) throws Exception {
        TextView textView = getChildView(text, isTitle, glForm);
        if (col == 0 && row != ChartData.get(0).getList().size() + 1 && isTitle == false && haveChild) {
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    llChild.removeAllViews();
                    judgeChild(((TextView) v).getText().toString());

                }
            });
        }

        glForm.addView(textView, getChildParam(row, col));

    }

    private void addFormTotalChild(int row, int col, String text, boolean isTitle, @ColorRes int color) throws Exception {
        TextView textView = getChildView(text, isTitle, glForm);
        textView.setBackgroundColor(getColor(color));
        glForm.addView(textView, getChildParam(row, col));
    }

    private TextView getChild() {
        TextView textView = new TextView(this);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        return textView;
    }

    private void setChildView() {
        switch (childChartType) {
            case "0":
                LineCharts lineCharts = new LineCharts(this);
                lineCharts.setData(ChildChartData);
                llChild.addView(lineCharts);
                break;
            case "1":
                BarCharts barCharts = new BarCharts(this);
                barCharts.setData(ChildChartData);
                barCharts.build();
                llChild.addView(barCharts);

                break;
            case "2":
                for (int i = 0; i < ChildChartData.size(); i++) {
                    PieCharts pieCharts = new PieCharts(this);
                    pieCharts.setData(ChildChartData.get(0).getList());
                    pieCharts.setCenterText(TextUtils.isEmpty(ChildChartData.get(0).getName()) ? "" : ChildChartData.get(0).getName());
                    pieCharts.build();
                    llChild.addView(pieCharts);
                    break;
                }
                break;
            default:
                break;
        }
    }

    private void makeChildChartDataSameLength() {
        int length = ChildChartData.get(0).getList().size();
        for (int i = 0; i < ChildChartData.size(); i++) {
            if (length > ChildChartData.get(i).getList().size()) {
                childChartDataAddLength(length, i);

            } else {
                childChartDataReduceLength(length, i);
            }
        }
    }

    private void childChartDataAddLength(int length, int i) {
        for (int j = 0; j < length - ChildChartData.get(i).getList().size(); j++) {
            ChildChartData.get(i).getList().add(new ChartBean.Line(ChildChartData.get(0).getList().get(ChildChartData.get(i).getList().size()).getxValue(), 0));
        }
    }

    private void childChartDataReduceLength(int length, int i) {
        if (length < ChildChartData.get(i).getList().size()) {
            for (int k = 0; k < ChildChartData.get(i).getList().size() - length; k++) {
                ChildChartData.get(i).getList().remove(ChildChartData.size() - 1);
            }
        }
    }

    private void setChildChartData(JSONArray jsonArray) throws JSONException, Exception {
        for (String value : childFields) {
            List<ChartBean.Line> line = new ArrayList<>();
            setChildCharChildData(jsonArray, line, value);
            ChildChartDataAddValue(value, line);
        }
    }

    private void setChildCharChildData(JSONArray jsonArray, List<ChartBean.Line> line, String value) throws JSONException, Exception {
        for (int i = 0; i < jsonArray.length(); i++) {
            if (jsonArray.getJSONObject(i).optString(childField).equals(value)) {
                ChartBean.Line data = new ChartBean.Line();
                data.setxValue(jsonArray.getJSONObject(i).optString(childXValue));
                Object object = jsonArray.getJSONObject(i).opt(childYValue);
                String y = (object == null ? "" : object.toString());
                Float yValue = Float.parseFloat(y);
                data.setyValue(yValue);
                line.add(data);
            }
        }
    }

    private void ChildChartDataAddValue(String value, List<ChartBean.Line> line) {
        ChartBean chartBean = new ChartBean();
        chartBean.setName(value);
        chartBean.setList(line);
        chartBean.setUnit(infoBeans.get(0).getChildYlabel());
        ChildChartData.add(chartBean);
    }

    /*判断Y轴数据类型是否正确*/
    private Boolean isChildYTypeTrue() {
        for (int i = 0; i < columnsBeans.size(); i++) {
            if (childYValue.equals(columnsBeans.get(i).getSFieldsName()) && columnsBeans.get(i).isChild()) {
                String type = columnsBeans.get(i).getSFieldsType();
                if (type.equals("number"))
                    return true;
                else return false;
            }
        }
        return false;
    }

    /*判断X轴数据类型是否正确*/
    private Boolean isChildXTypeTrue() {
        for (int i = 0; i < columnsBeans.size(); i++) {
            if (childXValue.equals(columnsBeans.get(i).getSFieldsName()) && columnsBeans.get(i).isChild()) {
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

    /*获取初始化数据参数*/
    private List<NetParams> getChildParams(String s) {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("otype", Otype.GetReportChildData));
        params.add(new NetParams("userid", userid));
        params.add(new NetParams("iFormID", menuid));
        String filters = getChildFilters(s);
        params.add(new NetParams("filters", filters));
        return params;
    }

    private String getChildFilters(String s) {
        String filters = childLink + "=" + s;
        if (StringUtil.isNotEmpty(fixfilter)) {
            if (childIsStore) {
                filters = filters + "$" + fixfilter;
            } else {
                filters = filters + " and " + fixfilter;
            }
        }
        if (StringUtil.isNotEmpty(childFilter)) {
            if (childIsStore) {
                filters = filters + "$" + childFilter;
            } else {
                filters = filters + " and " + childFilter;
            }
        }
        return filters;
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
                if (haveChild) {
                    if (childIsStore == true) {
                        childFilter = data.getStringExtra("isStore");

                    } else {
                        childFilter = data.getStringExtra("noStore");
                    }
                }
                filter = childFilter;
//                Log.e("filter", filter);
                llContent.removeAllViews();
                llChild.removeAllViews();
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
                                    llChild.removeAllViews();
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
        if (StringUtil.isNotEmpty(fixfilter) && StringUtil.isNotEmpty(filter)) {
            params.add(new NetParams("filters", filter + (childIsStore ? "$" : "and") + fixfilter));
        } else {
            params.add(new NetParams("filters", filter + fixfilter));
        }
        params.add(new NetParams("sort", ""));
        params.add(new NetParams("order", "asc"));
        return params;
    }
}
