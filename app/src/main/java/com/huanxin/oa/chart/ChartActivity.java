package com.huanxin.oa.chart;

import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.data.RadarEntry;
import com.huanxin.oa.R;
import com.huanxin.oa.view.chart.bar.BarCharts;
import com.huanxin.oa.view.chart.line.LineBean;
import com.huanxin.oa.view.chart.line.LineCharts;
import com.huanxin.oa.view.chart.pie.PieCharts;
import com.huanxin.oa.view.chart.radar.RadarBean;
import com.huanxin.oa.view.chart.radar.RadarCharts;
import com.huanxin.oa.view.chart.table.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChartActivity extends AppCompatActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.gl_data)
    GridLayout glData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        ButterKnife.bind(this);
        init();
    }

    private void init() {

        tvTitle.setText("图表");
        rlTop.setBackgroundColor(getColor(R.color.white));
        tvTitle.setTextColor(getColor(R.color.black));
        tvRight.setVisibility(View.VISIBLE);
        List<LineBean> lineBeans = new ArrayList<>();
        for (int size = 0; size < 2; size++) {
            List<LineBean.Line> lines = new ArrayList<>();
            for (int i = 0; i < 6; i++) {
                LineBean.Line line = new LineBean.Line();
                line.setxValue(i + 1 + "月");
                line.setyValue((float) new Random().nextInt(100));

                lines.add(line);
            }
            LineBean lineBean = new LineBean();
            lineBean.setList(lines);
            lineBean.setName("yyy" + size + 1);
            lineBean.setDescription("卖猪：");
            lineBean.setUnit("只");
            lineBeans.add(lineBean);
        }
        List<RadarBean> radarBeans = new ArrayList<>();
        for (int size = 0; size < 2; size++) {
            List<RadarBean.Radar> radars = new ArrayList<>();
            for (int i = 0; i < 6; i++) {
                RadarBean.Radar radar = new RadarBean.Radar();
                radar.setxValue(i + 1 + "月");
                radar.setyValue((float) new Random().nextInt(100));

                radars.add(radar);
            }
            RadarBean radarBean = new RadarBean();
            radarBean.setList(radars);
            radarBean.setLabel("yyy" + size + 1);
            radarBeans.add(radarBean);
        }
        LineCharts charts = new LineCharts(this);
        charts.setData(lineBeans);

        llContent.addView(charts);

        BarCharts barCharts = new BarCharts(this);
        barCharts.setData(lineBeans);
        barCharts.build();
        llContent.addView(barCharts);

        PieCharts pieCharts = new PieCharts(this);
        pieCharts.setData(lineBeans.get(0).getList());
        pieCharts.setCenterText("");
        pieCharts.build();
        llContent.addView(pieCharts);
        RadarCharts radarCharts = new RadarCharts(this);
        radarCharts.setData(radarBeans);
        llContent.addView(radarCharts);
        addTable();
//        setTable();
    }

    private ArrayList<RadarEntry> getRadarData() {

        float mul = 80;
        float min = 20;
        int cnt = 5;
        ArrayList<RadarEntry> entries = new ArrayList<>();
        for (int i = 0; i < cnt; i++) {
            float val1 = (float) (Math.random() * mul) + min;
            entries.add(new RadarEntry(val1));
        }
        return entries;
    }

    private void addTable() {
        Table table = new Table(this);
        table.setData(getTableData(), "表一");
        llContent.addView(table);
    }


    private List<List<String>> getTableData() {
        List<List<String>> data = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            List<String> column = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                if (i == 0) {
                    column.add("第" + j + "列");
                } else {
                    if (j == 0) {
                        column.add("第" + i + "行");
                    } else {
                        column.add(new Random().nextInt(100) + "");
                    }
                }
            }
            data.add(column);
        }
        return data;
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
