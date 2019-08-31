package com.huanxin.oa.view.chart.radar;

import java.util.List;

public class RadarBean {
    List<Radar> list;//数据源
    String label;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<Radar> getList() {
        return list;
    }

    public void setList(List<Radar> list) {
        this.list = list;
    }

    public static class Radar {
        String xValue;
        float yValue;

        public String getxValue() {
            return xValue;
        }

        public void setxValue(String xValue) {
            this.xValue = xValue;
        }

        public float getyValue() {
            return yValue;
        }

        public void setyValue(float yValue) {
            this.yValue = yValue;
        }
    }
}
