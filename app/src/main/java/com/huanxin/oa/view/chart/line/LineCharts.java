package com.huanxin.oa.view.chart.line;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.huanxin.oa.R;
import com.huanxin.oa.utils.StringUtil;
import com.huanxin.oa.view.chart.ChartBean;
import com.huanxin.oa.view.chart.marker.MarkerRadar;

import java.util.ArrayList;
import java.util.List;


public class LineCharts extends FrameLayout {
    List<ChartBean> data;
    Context context;
    LineChart chart;
    XAxis xAxis;
    YAxis yAxis;
    //y轴显示标签数
    int yCount = 5;


    /*设置数据源*/
    public LineCharts setData(List<ChartBean> data) {
        this.data = data;
        initData();
        return this;
    }

    /*设置y轴标签数*/
    public LineCharts setYCount(int yCount) {
        this.yCount = yCount;
        return this;
    }

    public Legend getLegend() {
        return chart.getLegend();

    }

    public LineCharts(@NonNull Context context) {
        this(context, null);
    }

    public LineCharts(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.chart_line, this, true);
        init();
    }

    private void init() {
        chart = findViewById(R.id.line_chart);
        chart.getAxisRight().setEnabled(false);//不显示右边的Y轴
        xAxis = chart.getXAxis();
        yAxis = chart.getAxisLeft();
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
        yAxis.setLabelCount(yCount, true);
        yAxis.setDrawGridLines(false);
        yAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
    }

    /*初始化表格*/
    private void initChart() {
        Description description = new Description();
        description.setEnabled(false);
        chart.setDescription(description);
        MarkerView mv = new MarkerRadar(context);
        mv.setChartView(chart); // For bounds control
        chart.setMarker(mv);
        chart.setPinchZoom(false);
        chart.setDoubleTapToZoomEnabled(false);
    }

    private void initData() {
        //设置x轴显示标签数量  还有一个重载方法第二个参数为布尔值强制设置数量 如果启用会导致绘制点出现偏差
        xAxis.setLabelCount(data.get(0).getList().size() - 1, false);
        //格式化x轴标签显示字符
        xAxis.setValueFormatter(new LineValueFormatter(data.get(0).getList()));
        if (chart.getData() == null) {
            //保存LineDataSet集合
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            for (int i = 0; i < data.size(); i++) {
                LineDataSet set;
                set = new LineDataSet(LineUtil.getData(data.get(i).getList()), data.get(i).getName());
                set.setColor(StringUtil.randomColor());
                dataSets.add(set);
            }
            LineData data1 = new LineData(dataSets);
            chart.setData(data1);
            //绘制图表
            chart.invalidate();
        }
    }
}
