package com.huanxin.oa.view.chart.radar;

import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.List;

public class RadarValueFormatter extends ValueFormatter {
    List<RadarBean.Radar> list;

    public RadarValueFormatter(List<RadarBean.Radar> list) {
        this.list = list;
    }

    @Override
    public String getFormattedValue(float value) {
        int i = (int) value;
        if (i == -1) {
            return "";
        } else if (i < list.size())
            return list.get(i).getxValue();
        else
            return "";
    }
}
