package com.huanxin.oa.view.chart;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.huanxin.oa.R;

import java.math.BigDecimal;

public class DetailsMarkerView extends MarkerView {

    private TextView tvContent;
    //    private TextView mTvChart1;
    private String description;
    private String unit;

    /**
     * 在构造方法里面传入自己的布局以及实例化控件
     *
     * @param context 上下文
     * @param
     */
    public DetailsMarkerView(Context context, int layoutResource, String description, String unit) {
        super(context, layoutResource);
        tvContent = findViewById(R.id.tv_content);
        this.description = description;
        this.unit = unit;
    }

    //每次重绘，会调用此方法刷新数据
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        super.refreshContent(e, highlight);
        try {
            //收入
            if (e.getY() == 0) {
                tvContent.setText("暂无数据");
            } else {
                tvContent.setText(concat(e.getY(), description));
            }
            tvContent.setText(String.valueOf((int) e.getX() + 1));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        super.refreshContent(e, highlight);
    }

    //布局的偏移量。就是布局显示在圆点的那个位置
    // -(width / 2) 布局水平居中
    //-(height) 布局显示在圆点上方
    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }

    public String concat(float money, String values) {
        return values + new BigDecimal(money).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + unit;
    }

}
