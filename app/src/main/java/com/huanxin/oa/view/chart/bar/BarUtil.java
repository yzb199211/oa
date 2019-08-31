package com.huanxin.oa.view.chart.bar;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.huanxin.oa.view.chart.line.LineBean;

import java.util.ArrayList;
import java.util.List;

public class BarUtil {
    public static List<BarEntry> getData(List<LineBean.Line> list) {
        List<BarEntry> entryList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            entryList.add(new BarEntry(i,list.get(i).getyValue()));
        }
        return entryList;
    }
}
