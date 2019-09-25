package com.huanxin.oa.view.chart.pie;

import com.github.mikephil.charting.data.PieEntry;
import com.huanxin.oa.view.chart.ChartBean;

import java.util.ArrayList;
import java.util.List;

public class PieUtil {
    public static   ArrayList<PieEntry> getData(List<ChartBean.Line> datas) {
        ArrayList<PieEntry>  list = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            list.add(new PieEntry(datas.get(i).getyValue(), datas.get(i).getxValue()));
        }
        return list;
    }
}
