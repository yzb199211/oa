package com.huanxin.oa.view.chart.bar;

import android.util.Log;

import com.github.mikephil.charting.data.BarEntry;
import com.google.gson.Gson;
import com.huanxin.oa.view.chart.ChartBean;

import java.util.ArrayList;
import java.util.List;

public class BarUtil {
    public static List<BarEntry> getData(List<ChartBean.Line> list) {
        List<BarEntry> entryList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            entryList.add(new BarEntry(i, list.get(i).getyValue()));
        }
        return entryList;
    }

    public static List<BarEntry> getBarStackData(List<ChartBean> list) {
        Log.e("data", new Gson().toJson(list));
        int counts = list.size();
        int length = list.get(0).getList().size();
        List<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            float[] floats = new float[counts];
            for (int j = 0; j < counts; j++) {
                Log.e("tag", "i=" + i + ";" + "j=" + j + "");
                float yValue = list.get(j).getList().get(i).getyValue();
                floats[j] = yValue;
            }
            entries.add(i, new BarEntry(i, floats));
        }
        Log.e("entries", new Gson().toJson(entries));
        return entries;
    }
}
