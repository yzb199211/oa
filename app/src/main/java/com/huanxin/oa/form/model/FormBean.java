package com.huanxin.oa.form.model;

import java.util.List;

public class FormBean {
    private List<ReportConditionBean> ReportCondition;
    private List<ReportInfoBean> ReportInfo;
    private List<ReportColumnsBean> ReportColumns;

    public List<ReportConditionBean> getReportCondition() {
        return ReportCondition;
    }

    public void setReportCondition(List<ReportConditionBean> ReportCondition) {
        this.ReportCondition = ReportCondition;
    }

    public List<ReportInfoBean> getReportInfo() {
        return ReportInfo;
    }

    public void setReportInfo(List<ReportInfoBean> ReportInfo) {
        this.ReportInfo = ReportInfo;
    }

    public List<ReportColumnsBean> getReportColumns() {
        return ReportColumns;
    }

    public void setReportColumns(List<ReportColumnsBean> ReportColumns) {
        this.ReportColumns = ReportColumns;
    }

    public static class ReportConditionBean {
        /**
         * iSerial : 1
         * sFieldName : sDate
         * sCaption : 年份
         * sFieldType :
         * sLookUpName :
         * sLookUpFilters :
         * sOpTion : like
         * sValue :
         */

        private int iSerial;
        private String sFieldName;
        private String sCaption;
        private String sFieldType;
        private String sLookUpName;
        private String sLookUpFilters;
        private String sOpTion;
        private String sValue;

        public int getISerial() {
            return iSerial;
        }

        public void setISerial(int iSerial) {
            this.iSerial = iSerial;
        }

        public String getSFieldName() {
            return sFieldName;
        }

        public void setSFieldName(String sFieldName) {
            this.sFieldName = sFieldName;
        }

        public String getSCaption() {
            return sCaption;
        }

        public void setSCaption(String sCaption) {
            this.sCaption = sCaption;
        }

        public String getSFieldType() {
            return sFieldType;
        }

        public void setSFieldType(String sFieldType) {
            this.sFieldType = sFieldType;
        }

        public String getSLookUpName() {
            return sLookUpName;
        }

        public void setSLookUpName(String sLookUpName) {
            this.sLookUpName = sLookUpName;
        }

        public String getSLookUpFilters() {
            return sLookUpFilters;
        }

        public void setSLookUpFilters(String sLookUpFilters) {
            this.sLookUpFilters = sLookUpFilters;
        }

        public String getSOpTion() {
            return sOpTion;
        }

        public void setSOpTion(String sOpTion) {
            this.sOpTion = sOpTion;
        }

        public String getSValue() {
            return sValue;
        }

        public void setSValue(String sValue) {
            this.sValue = sValue;
        }
    }

    public static class ReportInfoBean {
        /**
         * sBillType : 销售员年销量
         * pPageCount : 30
         * sAppStyle : 普通表格
         * iShowChart : 1
         * sAppFilters1 :
         * sAppFilters2 :
         * sAppFilters3 :
         * sAppFilters4 :
         * sAppFiltersName1 :
         * sAppFiltersName2 :
         * sAppFiltersName3 :
         * sAppFiltersName4 :
         * iNewChart : 1
         * sChartType : 2
         * sSerialField : sSaleName
         * sXAxisField : sDate
         * sYAxisField : fTotal
         * sItemColor :
         * sLineWidth : 0
         * sLineItemSymbol : circle
         * sLineItemSymbolSize :
         * sLineItemBorderWidth : 0
         * sLineItemBorderColor :
         * sLineItemBorderType : solid
         * iLineSmooth : 0
         * iLineArea : 0
         * sAreaOptions :
         * iShowValue : 0
         * sShowPosition : top
         * sItemLabelFormatterSimple : {b}:{c}({d}%)
         * sItemLabelFormatterFn :
         * sItemToolTipFormatterSimple : {b}:{c}({d}%)
         * sItemToolTipFormatterFn :
         * iBarStack : 0
         * iHideX : 0
         * iHideY : 0
         * sXAxisLabelFormatterSimple :
         * sXAxisLabelFormatterFn :
         * sYAxisLabelFormatterSimple :
         * isnull :
         * iHideXGrid : 0
         * iHideYGrid : 0
         * sPieCenter :
         * sPieRadius : 0,25%
         * sPieRoseType : radius
         */

        private String sBillType;
        private int pPageCount;
        private String sAppStyle;
        private int iShowChart;
        private String sAppFilters1;
        private String sAppFilters2;
        private String sAppFilters3;
        private String sAppFilters4;
        private String sAppFiltersName1;
        private String sAppFiltersName2;
        private String sAppFiltersName3;
        private String sAppFiltersName4;
        private int iNewChart;
        private String sChartType;
        private String sSerialField;
        private String sXAxisField;
        private String sYAxisField;
        private String sItemColor;
        private int sLineWidth;
        private String sLineItemSymbol;
        private String sLineItemSymbolSize;
        private int sLineItemBorderWidth;
        private String sLineItemBorderColor;
        private String sLineItemBorderType;
        private int iLineSmooth;
        private int iLineArea;
        private String sAreaOptions;
        private int iShowValue;
        private String sShowPosition;
        private String sItemLabelFormatterSimple;
        private String sItemLabelFormatterFn;
        private String sItemToolTipFormatterSimple;
        private String sItemToolTipFormatterFn;
        private int iBarStack;
        private int iHideX;
        private int iHideY;
        private String sXAxisLabelFormatterSimple;
        private String sXAxisLabelFormatterFn;
        private String sYAxisLabelFormatterSimple;
        private String isnull;
        private int iHideXGrid;
        private int iHideYGrid;
        private String sPieCenter;
        private String sPieRadius;
        private String sPieRoseType;

        public String getSBillType() {
            return sBillType;
        }

        public void setSBillType(String sBillType) {
            this.sBillType = sBillType;
        }

        public int getPPageCount() {
            return pPageCount;
        }

        public void setPPageCount(int pPageCount) {
            this.pPageCount = pPageCount;
        }

        public String getSAppStyle() {
            return sAppStyle;
        }

        public void setSAppStyle(String sAppStyle) {
            this.sAppStyle = sAppStyle;
        }

        public int getIShowChart() {
            return iShowChart;
        }

        public void setIShowChart(int iShowChart) {
            this.iShowChart = iShowChart;
        }

        public String getSAppFilters1() {
            return sAppFilters1;
        }

        public void setSAppFilters1(String sAppFilters1) {
            this.sAppFilters1 = sAppFilters1;
        }

        public String getSAppFilters2() {
            return sAppFilters2;
        }

        public void setSAppFilters2(String sAppFilters2) {
            this.sAppFilters2 = sAppFilters2;
        }

        public String getSAppFilters3() {
            return sAppFilters3;
        }

        public void setSAppFilters3(String sAppFilters3) {
            this.sAppFilters3 = sAppFilters3;
        }

        public String getSAppFilters4() {
            return sAppFilters4;
        }

        public void setSAppFilters4(String sAppFilters4) {
            this.sAppFilters4 = sAppFilters4;
        }

        public String getSAppFiltersName1() {
            return sAppFiltersName1;
        }

        public void setSAppFiltersName1(String sAppFiltersName1) {
            this.sAppFiltersName1 = sAppFiltersName1;
        }

        public String getSAppFiltersName2() {
            return sAppFiltersName2;
        }

        public void setSAppFiltersName2(String sAppFiltersName2) {
            this.sAppFiltersName2 = sAppFiltersName2;
        }

        public String getSAppFiltersName3() {
            return sAppFiltersName3;
        }

        public void setSAppFiltersName3(String sAppFiltersName3) {
            this.sAppFiltersName3 = sAppFiltersName3;
        }

        public String getSAppFiltersName4() {
            return sAppFiltersName4;
        }

        public void setSAppFiltersName4(String sAppFiltersName4) {
            this.sAppFiltersName4 = sAppFiltersName4;
        }

        public int getINewChart() {
            return iNewChart;
        }

        public void setINewChart(int iNewChart) {
            this.iNewChart = iNewChart;
        }

        public String getSChartType() {
            return sChartType;
        }

        public void setSChartType(String sChartType) {
            this.sChartType = sChartType;
        }

        public String getSSerialField() {
            return sSerialField;
        }

        public void setSSerialField(String sSerialField) {
            this.sSerialField = sSerialField;
        }

        public String getSXAxisField() {
            return sXAxisField;
        }

        public void setSXAxisField(String sXAxisField) {
            this.sXAxisField = sXAxisField;
        }

        public String getSYAxisField() {
            return sYAxisField;
        }

        public void setSYAxisField(String sYAxisField) {
            this.sYAxisField = sYAxisField;
        }

        public String getSItemColor() {
            return sItemColor;
        }

        public void setSItemColor(String sItemColor) {
            this.sItemColor = sItemColor;
        }

        public int getSLineWidth() {
            return sLineWidth;
        }

        public void setSLineWidth(int sLineWidth) {
            this.sLineWidth = sLineWidth;
        }

        public String getSLineItemSymbol() {
            return sLineItemSymbol;
        }

        public void setSLineItemSymbol(String sLineItemSymbol) {
            this.sLineItemSymbol = sLineItemSymbol;
        }

        public String getSLineItemSymbolSize() {
            return sLineItemSymbolSize;
        }

        public void setSLineItemSymbolSize(String sLineItemSymbolSize) {
            this.sLineItemSymbolSize = sLineItemSymbolSize;
        }

        public int getSLineItemBorderWidth() {
            return sLineItemBorderWidth;
        }

        public void setSLineItemBorderWidth(int sLineItemBorderWidth) {
            this.sLineItemBorderWidth = sLineItemBorderWidth;
        }

        public String getSLineItemBorderColor() {
            return sLineItemBorderColor;
        }

        public void setSLineItemBorderColor(String sLineItemBorderColor) {
            this.sLineItemBorderColor = sLineItemBorderColor;
        }

        public String getSLineItemBorderType() {
            return sLineItemBorderType;
        }

        public void setSLineItemBorderType(String sLineItemBorderType) {
            this.sLineItemBorderType = sLineItemBorderType;
        }

        public int getILineSmooth() {
            return iLineSmooth;
        }

        public void setILineSmooth(int iLineSmooth) {
            this.iLineSmooth = iLineSmooth;
        }

        public int getILineArea() {
            return iLineArea;
        }

        public void setILineArea(int iLineArea) {
            this.iLineArea = iLineArea;
        }

        public String getSAreaOptions() {
            return sAreaOptions;
        }

        public void setSAreaOptions(String sAreaOptions) {
            this.sAreaOptions = sAreaOptions;
        }

        public int getIShowValue() {
            return iShowValue;
        }

        public void setIShowValue(int iShowValue) {
            this.iShowValue = iShowValue;
        }

        public String getSShowPosition() {
            return sShowPosition;
        }

        public void setSShowPosition(String sShowPosition) {
            this.sShowPosition = sShowPosition;
        }

        public String getSItemLabelFormatterSimple() {
            return sItemLabelFormatterSimple;
        }

        public void setSItemLabelFormatterSimple(String sItemLabelFormatterSimple) {
            this.sItemLabelFormatterSimple = sItemLabelFormatterSimple;
        }

        public String getSItemLabelFormatterFn() {
            return sItemLabelFormatterFn;
        }

        public void setSItemLabelFormatterFn(String sItemLabelFormatterFn) {
            this.sItemLabelFormatterFn = sItemLabelFormatterFn;
        }

        public String getSItemToolTipFormatterSimple() {
            return sItemToolTipFormatterSimple;
        }

        public void setSItemToolTipFormatterSimple(String sItemToolTipFormatterSimple) {
            this.sItemToolTipFormatterSimple = sItemToolTipFormatterSimple;
        }

        public String getSItemToolTipFormatterFn() {
            return sItemToolTipFormatterFn;
        }

        public void setSItemToolTipFormatterFn(String sItemToolTipFormatterFn) {
            this.sItemToolTipFormatterFn = sItemToolTipFormatterFn;
        }

        public int getIBarStack() {
            return iBarStack;
        }

        public void setIBarStack(int iBarStack) {
            this.iBarStack = iBarStack;
        }

        public int getIHideX() {
            return iHideX;
        }

        public void setIHideX(int iHideX) {
            this.iHideX = iHideX;
        }

        public int getIHideY() {
            return iHideY;
        }

        public void setIHideY(int iHideY) {
            this.iHideY = iHideY;
        }

        public String getSXAxisLabelFormatterSimple() {
            return sXAxisLabelFormatterSimple;
        }

        public void setSXAxisLabelFormatterSimple(String sXAxisLabelFormatterSimple) {
            this.sXAxisLabelFormatterSimple = sXAxisLabelFormatterSimple;
        }

        public String getSXAxisLabelFormatterFn() {
            return sXAxisLabelFormatterFn;
        }

        public void setSXAxisLabelFormatterFn(String sXAxisLabelFormatterFn) {
            this.sXAxisLabelFormatterFn = sXAxisLabelFormatterFn;
        }

        public String getSYAxisLabelFormatterSimple() {
            return sYAxisLabelFormatterSimple;
        }

        public void setSYAxisLabelFormatterSimple(String sYAxisLabelFormatterSimple) {
            this.sYAxisLabelFormatterSimple = sYAxisLabelFormatterSimple;
        }

        public String getIsnull() {
            return isnull;
        }

        public void setIsnull(String isnull) {
            this.isnull = isnull;
        }

        public int getIHideXGrid() {
            return iHideXGrid;
        }

        public void setIHideXGrid(int iHideXGrid) {
            this.iHideXGrid = iHideXGrid;
        }

        public int getIHideYGrid() {
            return iHideYGrid;
        }

        public void setIHideYGrid(int iHideYGrid) {
            this.iHideYGrid = iHideYGrid;
        }

        public String getSPieCenter() {
            return sPieCenter;
        }

        public void setSPieCenter(String sPieCenter) {
            this.sPieCenter = sPieCenter;
        }

        public String getSPieRadius() {
            return sPieRadius;
        }

        public void setSPieRadius(String sPieRadius) {
            this.sPieRadius = sPieRadius;
        }

        public String getSPieRoseType() {
            return sPieRoseType;
        }

        public void setSPieRoseType(String sPieRoseType) {
            this.sPieRoseType = sPieRoseType;
        }
    }

    public static class ReportColumnsBean {
        /**
         * iShowOrder : 1
         * sFieldsName : sSaleName
         * sFieldsdisplayName : 业务员
         * sFieldsType : string
         * iWidth : 1
         * iMerge : 0
         * sAlign : left
         * iSort : 0
         */

        private int iShowOrder;
        private String sFieldsName;
        private String sFieldsdisplayName;
        private String sFieldsType;
        private int iWidth;
        private int iMerge;
        private String sAlign;
        private int iSort;

        public int getIShowOrder() {
            return iShowOrder;
        }

        public void setIShowOrder(int iShowOrder) {
            this.iShowOrder = iShowOrder;
        }

        public String getSFieldsName() {
            return sFieldsName;
        }

        public void setSFieldsName(String sFieldsName) {
            this.sFieldsName = sFieldsName;
        }

        public String getSFieldsdisplayName() {
            return sFieldsdisplayName;
        }

        public void setSFieldsdisplayName(String sFieldsdisplayName) {
            this.sFieldsdisplayName = sFieldsdisplayName;
        }

        public String getSFieldsType() {
            return sFieldsType;
        }

        public void setSFieldsType(String sFieldsType) {
            this.sFieldsType = sFieldsType;
        }

        public int getIWidth() {
            return iWidth;
        }

        public void setIWidth(int iWidth) {
            this.iWidth = iWidth;
        }

        public int getIMerge() {
            return iMerge;
        }

        public void setIMerge(int iMerge) {
            this.iMerge = iMerge;
        }

        public String getSAlign() {
            return sAlign;
        }

        public void setSAlign(String sAlign) {
            this.sAlign = sAlign;
        }

        public int getISort() {
            return iSort;
        }

        public void setISort(int iSort) {
            this.iSort = iSort;
        }
    }
}
