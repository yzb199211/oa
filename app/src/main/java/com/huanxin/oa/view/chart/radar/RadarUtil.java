package com.huanxin.oa.view.chart.radar;

import com.github.mikephil.charting.data.RadarEntry;
import com.huanxin.oa.view.chart.ChartBean;

import java.util.ArrayList;
import java.util.List;

public class RadarUtil {
//    public static List<RadarEntry> getData(List<RadarBean.Radar> list) {
//        List<RadarEntry> entryList = new ArrayList<>();
//        for (int i = 0; i < list.size(); i++) {
//            entryList.add(new RadarEntry(i, list.get(i).getyValue()));
//        }
//        return entryList;
//    }
    public static List<RadarEntry> getData(List<ChartBean.Line> list) {
        List<RadarEntry> entryList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            entryList.add(new RadarEntry(i, list.get(i).getyValue()));
        }
        return entryList;
    }
}

