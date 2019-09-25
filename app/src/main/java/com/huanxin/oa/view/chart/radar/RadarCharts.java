package com.huanxin.oa.view.chart.radar;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.huanxin.oa.R;
import com.huanxin.oa.utils.StringUtil;
import com.huanxin.oa.view.chart.ChartBean;
import com.huanxin.oa.view.chart.marker.MarkerRadar;

import java.util.ArrayList;
import java.util.List;

public class RadarCharts extends FrameLayout {
    Context context;
    XAxis xAxis;
    YAxis yAxis;
    RadarChart chart;
    boolean isFill = false;
    List<ChartBean> data;


    public RadarCharts(@NonNull Context context) {
        this(context, null);
    }

    public RadarCharts(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public RadarCharts isFill(boolean isFill) {
        this.isFill = isFill;
        return this;
    }

    /*设置数据源*/
    public RadarCharts setData(List<ChartBean> data) {
        this.data = data;
        return this;
    }

    public void build() {
        try {
            initData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initData() {
        setXaxis();
        ArrayList<IRadarDataSet> sets = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            ArrayList<RadarEntry> entries = new ArrayList<>();
            for (int j = 0; j < data.get(i).getList().size(); j++) {
                entries.add(new RadarEntry(data.get(i).getList().get(j).getyValue()));
            }

            RadarDataSet set = new RadarDataSet(entries, data.get(i).getName());
            set.setDrawFilled(isFill);
            set.setFillAlpha(100);
            set.setLineWidth(2f);
            set.setDrawHighlightCircleEnabled(true);
            set.setDrawHighlightIndicators(false);
            set.setDrawValues(false);
            int color = StringUtil.randomColor();
            set.setColor(color);
            set.setFillColor(color);
            sets.add(set);
        }
        RadarData radarData = new RadarData(sets);
        chart.setData(radarData);
    }

    private void setXaxis() {
        xAxis.setValueFormatter(new RadarValueFormatter(data.get(0).getList()));
    }

    private void init() {
        LayoutInflater.from(context).inflate(R.layout.chart_radar, this, true);
        chart = findViewById(R.id.radar_chart);
        initChart();
    }

    private void initChart() {
//        chart.setBackgroundColor(Color.rgb(60, 65, 82));
        chart.getDescription().setEnabled(false);
        Legend legend = chart.getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);

        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        chart.setWebLineWidth(1f);
        chart.setWebColor(Color.LTGRAY);
        chart.setWebLineWidthInner(1f);
        chart.setWebColorInner(Color.LTGRAY);
        chart.setWebAlpha(100);

//        MarkerView mv = new RadarMarkerView(context, R.layout.radar_markerview);
        MarkerView mv = new MarkerRadar(context);
        mv.setChartView(chart); // For bounds control
        chart.setMarker(mv); // Set the marker to the chart
//        chart.animateXY(1400, 1400, Easing.EaseInOutQuad);

        xAxis = chart.getXAxis();
        yAxis = chart.getYAxis();

        initXaxis();
        initYaxis();
    }

    private void initXaxis() {
        XAxis xAxis = chart.getXAxis();
        xAxis.setTextSize(24);
        xAxis.setYOffset(0f);
        xAxis.setXOffset(0f);
        xAxis.setTextColor(Color.BLACK);

    }

    private void initYaxis() {
        yAxis.setLabelCount(5, false);
        yAxis.setTextSize(20f);
        yAxis.setAxisMinimum(0f);
//        yAxis.setAxisMaximum(80f);
        yAxis.setDrawLabels(false);
    }
}
