package com.huanxin.oa.form;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.huanxin.oa.R;
import com.huanxin.oa.form.model.FormChartBean;
import com.huanxin.oa.form.utils.FormUtil;
import com.huanxin.oa.main.interfaces.OnItemClickListener;
import com.huanxin.oa.utils.StringUtil;
import com.huanxin.oa.utils.Toasts;
import com.huanxin.oa.view.chart.ChartBean;
import com.huanxin.oa.view.chart.bar.BarCharts;
import com.huanxin.oa.view.chart.bar.BarHorCharts;
import com.huanxin.oa.view.chart.bar.BarStackCharts;
import com.huanxin.oa.view.chart.line.LineCharts;
import com.huanxin.oa.view.chart.line.LineCubicCharts;
import com.huanxin.oa.view.chart.pie.PieCharts;
import com.huanxin.oa.view.chart.radar.RadarCharts;


import java.util.ArrayList;
import java.util.List;

public class FormAndCharts extends LinearLayout {
    private Context context;

    private LineCharts lineCharts;
    private BarCharts barCharts;
    private PieCharts pieCharts;
    private LineCubicCharts lineCubicCharts;
    private BarHorCharts barHorCharts;
    private RadarCharts radarCharts;
    private BarStackCharts barStackCharts;

    private String FormXName;
    private GridLayout glForm;
    private boolean haveChart;
    private String chartType;

    private List<ChartBean> ChartData;
    private List<String> titles;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public FormAndCharts(Context context) {
        this(context, null);
    }

    public FormAndCharts(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        initList();
        initForm();
    }

    private void initList() {
        ChartData = new ArrayList<>();
        titles = new ArrayList<>();
    }

    private void initForm() {
        glForm = new GridLayout(context);
        glForm.setBackgroundColor(context.getColor(R.color.form_menu_bg));
    }

    /*设置图表*/
    private void setChart(String chartType) throws Exception {
        switch (chartType) {
            case "0":
                setLineChart();
                break;
            case "1":
                setBarChart();
                break;
            case "2":
                if ((ChartData.size() > 0)) {
                    setPieChart();
                }
                break;
            case "3":
                setLineCubicChart();
                break;
            case "4":
                setBarHorChart();
                break;
            case "5":
                setRadaChart();
                break;
            case "6":
                setBarStackChart();
            default:
                break;
        }
    }


    private void setLineChart() {
        if (lineCharts == null) {
            lineCharts = new LineCharts(context);
            lineCharts.setData(ChartData);
            addView(lineCharts);
        } else {
            lineCharts.setData(ChartData);
        }
    }

    private void setBarChart() {
        if (barCharts == null) {
            barCharts = new BarCharts(context);
            barCharts.setData(ChartData);
            barCharts.build();
        } else {
            barCharts.setData(ChartData);
        }
        addView(barCharts);
    }

    private void setPieChart() {
        if (ChartData.size() > 0 && pieCharts == null) {
            pieCharts = new PieCharts(context);
            pieCharts.setData(ChartData.get(0).getList());
            pieCharts.setCenterText(TextUtils.isEmpty(ChartData.get(0).getName()) ? "" : ChartData.get(0).getName());
            pieCharts.build();
            addView(pieCharts);
        } else {
            pieCharts.setData(ChartData.get(0).getList());
        }
    }

    private void setLineCubicChart() {
        if (lineCubicCharts == null) {
            lineCubicCharts = new LineCubicCharts(context);
            lineCubicCharts.setData(ChartData);
            lineCubicCharts.build();
            addView(lineCubicCharts);
        } else {
            lineCubicCharts.setData(ChartData);
        }
    }

    private void setBarHorChart() {
        if (barHorCharts == null) {
            barHorCharts = new BarHorCharts(context);
            barHorCharts.setData(ChartData);
            barHorCharts.build();
            addView(barHorCharts);
        } else {
            barHorCharts.setData(ChartData);
        }
    }

    private void setRadaChart() {
        if (radarCharts == null) {
            radarCharts = new RadarCharts(context);
            radarCharts.setData(ChartData);
            radarCharts.build();
            addView(radarCharts);
        } else {
            radarCharts.setData(ChartData);
        }
    }

    private void setBarStackChart() {
        if (barStackCharts == null) {
            barStackCharts = new BarStackCharts(context);
            barStackCharts.setData(ChartData);
            barStackCharts.build();
            addView(barStackCharts);
        } else {
            barStackCharts.setData(ChartData);
        }
    }

    private void setFormData() throws Exception {
        glForm.removeAllViews();
        glForm.setRowCount(ChartData.get(0).getList().size() + 2);
        glForm.setColumnCount(titles.size() + 1);
        setFormRowName();
        int length = ChartData.get(0).getList().size();
        for (int i = 0; i < ChartData.size(); i++) {
            setFormColumnName(i + 1);
            List<ChartBean.Line> datas = new ArrayList<>();
            datas.addAll(ChartData.get(i).getList());
            setDetailData(length, datas, i);
        }
        for (int j = 0; j < ChartData.size(); j++) {
            setFormTotalData(j + 1, StringUtil.float2Str(ChartData.get(j).getTotal()));
        }
        setFormRowTotalName();
        addView(glForm);
    }

    private void setFormTotalData(int i, String data) throws Exception {
        FormChartBean style = new FormChartBean().setRow(ChartData.get(0).getList().size() + 1).setCol(i).setText(data).setTypeface(Typeface.DEFAULT_BOLD).setTextColor(R.color.default_text_color).setBackgroundColor(R.color.form_menu_total).setGravity(Gravity.CENTER_HORIZONTAL);
        glForm.addView(FormUtil.getColumn(context, style), getFormParam(ChartData.get(0).getList().size() + 1, i));
    }

    private void setFormRowTotalName() throws Exception {
        FormChartBean style = new FormChartBean().setRow(ChartData.get(0).getList().size() + 1).setCol(0).setText("总计").setTypeface(Typeface.DEFAULT_BOLD).setTextColor(R.color.default_text_color).setBackgroundColor(R.color.form_menu_total).setGravity(Gravity.CENTER_HORIZONTAL);
        glForm.addView(FormUtil.getColumn(context, style), getFormParam(ChartData.get(0).getList().size() + 1, 0));
    }

    private void setFormColumnName(int i) throws Exception {

        FormChartBean style = new FormChartBean().setRow(0).setCol(i).setText(ChartData.get(i - 1).getName()).setTextColor(R.color.white).setBackgroundColor(R.color.blue).setGravity(Gravity.CENTER_HORIZONTAL);
        glForm.addView(FormUtil.getColumn(context, style), getFormParam(0, i));
    }

    private void setFormRowName() throws Exception {
        FormChartBean style = new FormChartBean().setRow(0).setCol(0).setText(FormXName).setBackgroundColor(R.color.blue).setGravity(Gravity.CENTER_HORIZONTAL).setTextColor(R.color.white);
        glForm.addView(FormUtil.getColumn(context, style), getFormParam(0, 0));
    }

    private void setDetailData(int length, List<ChartBean.Line> datas, int i) throws Exception {
        for (int j = 0; j < length; j++) {
            if (datas.size() < length) {
                datas.add(new ChartBean.Line());
            }
            if (i == 0) {
                FormChartBean style = new FormChartBean().setRow(j + 1).setCol(0).setText(datas.get(j).getxValue()).setBackgroundColor(R.color.form_menu_item).setGravity(Gravity.CENTER_HORIZONTAL).setTextColor(R.color.form_menu_title);
                TextView textView = FormUtil.getColumn(context, style);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(v, 0);
                        }
                    }
                });
                glForm.addView(textView, getFormParam(j + 1, 0));
            }
            FormChartBean style = new FormChartBean().setRow(j + 1).setCol(i + 1).setText(StringUtil.float2Str(datas.get(j).getyValue())).setBackgroundColor(R.color.form_menu_white).setGravity(Gravity.CENTER_HORIZONTAL).setTextColor(R.color.default_content_color);
            glForm.addView(FormUtil.getColumn(context, style), getFormParam(j + 1, i + 1));
        }
    }

    private GridLayout.LayoutParams getFormParam(int row, int col) throws Exception {
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

    public FormAndCharts setData(@NonNull List<ChartBean> chartData) {
        ChartData.clear();
        ChartData = chartData;
        return this;
    }

    public FormAndCharts setTitles(@NonNull List<String> titles) {
        this.titles.clear();
        this.titles.addAll(titles);
        return this;
    }

    public FormAndCharts setFormXName(String formXName) {
        FormXName = formXName;
        return this;
    }

    public FormAndCharts setHaveChart(boolean haveChart) {
        this.haveChart = haveChart;
        return this;
    }

    public FormAndCharts setChartType(String chartType) {
        this.chartType = chartType;
        return this;
    }

    public void Build() {
        if (titles.size() == 0) {
            Toasts.showLong(context, context.getString(R.string.form_empty_title));
            return;
        }
        if (ChartData.size() == 0) {
            Toasts.showLong(context, context.getString(R.string.form_empty_detail));
            return;
        }
        if (haveChart) {
            try {
                setChart(chartType);
            } catch (Exception e) {
                e.printStackTrace();
                Toasts.showLong(context, context.getString(R.string.form_chart_error));
                try {
                    setFormData();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Toasts.showLong(context, context.getString(R.string.form_chart_error));
                }
            }
        }
        try {
            setFormData();
        } catch (Exception ex) {
            ex.printStackTrace();
            Toasts.showLong(context, context.getString(R.string.form_chart_error));
        }
    }
}

