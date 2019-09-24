package com.huanxin.oa.view.chart.bar;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.huanxin.oa.R;
import com.huanxin.oa.utils.StringUtil;
import com.huanxin.oa.view.chart.line.LineBean;
import com.huanxin.oa.view.chart.line.LineValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class BarStackCharts extends FrameLayout {
    Context context;
    XAxis xAxis;
    YAxis yAxis;
    BarChart chart;
    List<LineBean> data;
    int yCounts = 5;
    float groupSpace = 0.1f;
    float barWidth = 0.45f;
    int start = 0;
    float barSpace = 0;
    int groupCount;
    int dataSize;

    public BarStackCharts(@NonNull Context context) {
        this(context, null);
    }

    public void setData(List<LineBean> data) {
        this.data = data;
    }

    public void setyCounts(int yCounts) {
        this.yCounts = yCounts;
    }

    public void build() {
        try {
            initData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BarStackCharts(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.chart_bar, this, true);
        init();
    }

    private void init() {
        chart = findViewById(R.id.bar_chart);
        xAxis = chart.getXAxis();//获取x轴
        yAxis = chart.getAxisLeft();//获取y轴
        chart.getAxisRight().setEnabled(false);//不显示右边的y轴
        initXaxis();
        initYaxis();
        initChart();
    }

    //设置x轴样式
    private void initXaxis() {

        //X轴标签的倾斜角度
        xAxis.setLabelRotationAngle(0f);
        //设置X轴标签出现位置
        //TOP、BOTTOM、BOTH_SIDED、TOP_INSIDE、BOTTOM_INSIDE
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //设置标签文本格式
        xAxis.setTextSize(10f);
        //设置标签文本颜色
//        xAxis.setTextColor(Color.RED);
        //是否绘制轴线
        xAxis.setDrawAxisLine(true);
        //是否绘制网格线
        xAxis.setDrawGridLines(false);
        //自定义数值格式
//        xAxis.setValueFormatter(new MyCustomFormatter());
        //设置轴标签的颜色
//        xAxis.setTextColor(Color.BLUE);
        //设置轴标签的大小
//        xAxis.setTextSize(24f);
        //设置竖线大小
//        xAxis.setGridLineWidth(10f);
        //设置竖线颜色
//        xAxis.setGridColor(Color.RED);
        //设置x轴线颜色
//        xAxis.setAxisLineColor(Color.GREEN);
        //设置x轴线宽度
//        xAxis.setAxisLineWidth(5f);

    }

    /*设置y轴样式*/
    private void initYaxis() {
        yAxis.setLabelCount(yCounts, true);
        yAxis.setDrawGridLines(false);
        yAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
    }

    /*初始化表格*/
    private void initChart() {
        Description description = new Description();
        description.setEnabled(false);
        chart.setDescription(description);
    }

    private void initData() throws Exception {

        //设置x轴显示标签数量  还有一个重载方法第二个参数为布尔值强制设置数量 如果启用会导致绘制点出现偏差
        xAxis.setLabelCount(data.get(0).getList().size(), false);
        //格式化x轴标签显示字符
        xAxis.setValueFormatter(new LineValueFormatter(data.get(0).getList()));
        xAxis.setCenterAxisLabels(false);
        if (chart.getData() == null) {
            //保存BarDataSet集合
            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            BarDataSet set;
            List<Integer> colors = new ArrayList<>();
            List<String> labels = new ArrayList<>();
            for (int i = 0; i < data.size(); i++) {
                colors.add(StringUtil.randomColor());
                labels.add(data.get(i).getName());
            }

            set = new BarDataSet(BarUtil.getBarStackData(data), "1");
            set.setColors(colors);
            set.setStackLabels(labels.toArray(new String[labels.size()]));

            dataSets.add(set);
//            }

            BarData data1 = new BarData(dataSets);
            groupCount = data.get(0).getList().size();
            dataSize = data.size();
            barWidth = (1 - groupSpace) / dataSize;
            chart.setData(data1);
            // specify the width each bar should have
//            chart.getBarData().setBarWidth(barWidth);
            // restrict the x-axis range
//            chart.getXAxis().setAxisMinimum(start);
            // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters
//            chart.getXAxis().setAxisMaximum(start + chart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
//            chart.groupBars(start, groupSpace, barSpace);
//            for (IDataSet set : chart.getData().getDataSets())
//                set.setDrawValues(!set.isDrawValuesEnabled());
            //绘制图表
            chart.invalidate();
        }
    }

    public BarData getBarData() {
        return chart.getBarData();
    }

    public BarChart getChart() {
        return chart;
    }
}

