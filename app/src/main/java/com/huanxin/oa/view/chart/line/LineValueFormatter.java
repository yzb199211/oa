package com.huanxin.oa.view.chart.line;

import com.github.mikephil.charting.formatter.ValueFormatter;
import com.huanxin.oa.view.chart.ChartBean;

import java.util.List;

public class LineValueFormatter extends ValueFormatter {
    List<ChartBean.Line> list;

    public LineValueFormatter(List<ChartBean.Line> list) {
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
