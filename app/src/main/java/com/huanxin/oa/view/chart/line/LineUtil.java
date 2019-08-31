package com.huanxin.oa.view.chart.line;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;

public class LineUtil {
    public static List<Entry> getData(List<LineBean.Line> list) {
        List<Entry> entryList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            entryList.add(new Entry(i,list.get(i).getyValue()));
        }
        return entryList;
    }
}
