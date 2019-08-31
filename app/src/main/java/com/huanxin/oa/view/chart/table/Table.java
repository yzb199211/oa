package com.huanxin.oa.view.chart.table;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.huanxin.oa.R;

import java.util.List;

public class Table extends FrameLayout {
    Context context;
    List<List<String>> data;
    String title;
    TextView tvTitle;
    GridLayout glData;
    int maxRow;
    int maxColumn;

    public Table(@NonNull Context context) {
        this(context, null);
    }

    /*设置数据源*/
    public Table setData(List<List<String>> data, String title) {
        this.data = data;
        this.title = title;
        initData();
        return this;
    }

    private void initData() {
        tvTitle.setText(TextUtils.isEmpty(title) ? "" : title);
        if (data != null && data.size() > 0) {
            maxRow = data.size();
            if (data.get(0) != null && data.get(0).size() > 0) {
                maxColumn = data.get(0).size();
            }
        }
        setTable();
        invalidate();
    }

    private void setTable() {
        glData.setRowCount(maxRow);
        glData.setColumnCount(maxColumn);
        for (int i = 0; i < maxRow; i++) {
            for (int j = 0; j < maxColumn; j++) {
                TextView textView = new TextView(context);
                textView.setTextColor(context.getColor(R.color.default_content_color));
                textView.setBackgroundColor(context.getColor(R.color.blue1));
                if (i == 0 || j == 0) {
                    textView.setBackgroundColor(context.getColor(R.color.blue));
                    textView.setTextColor(context.getColor(R.color.white));
                }
                textView.setText(data.get(i).get(j));
                textView.setGravity(Gravity.CENTER);
                textView.setSingleLine();
                textView.setPadding(40, 20, 40, 20);
                GridLayout.Spec rowSpec = GridLayout.spec(i);     //设置它的行和列
                GridLayout.Spec columnSpec;
                if (j == 0)
                    columnSpec = GridLayout.spec(j);
                else
                    columnSpec = GridLayout.spec(j, 1.0f);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);
                if (j == 0)
                    params.setGravity(Gravity.LEFT);
                else
                    params.setGravity(Gravity.FILL);
                params.leftMargin = 1;
                params.bottomMargin = 1;
                glData.addView(textView, params);
            }
        }
    }

    public Table(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.grid_table, this, true);
        init();
    }

    private void init() {
        tvTitle = findViewById(R.id.tv_title);
        glData = findViewById(R.id.gl_data);
    }

}
