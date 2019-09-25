package com.huanxin.oa.view.chart;

import java.util.List;

public class ChartBean {
//    String string = "{\"name\":\"例如：业务员\",\"unit\":\"单位\",\"description\":\"支出：\",\"list\":[{\"xValue\":\"一月\",\"yValue\":1.1}}";
    String name;//数据源名称
    String unit;//单位
    String description;//弹出描述
    List<Line> list;//数据源

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Line> getList() {
        return list;
    }

    public void setList(List<Line> list) {
        this.list = list;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static class Line {
        String xValue="";
        float yValue=0;

        public Line() {
        }

        public Line(String xValue, float yValue) {
            this.xValue = xValue;
            this.yValue = yValue;
        }

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
