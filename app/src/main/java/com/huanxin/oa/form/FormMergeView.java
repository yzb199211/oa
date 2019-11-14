package com.huanxin.oa.form;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.huanxin.oa.R;
import com.huanxin.oa.form.merge.FormColumnView;
import com.huanxin.oa.form.model.FormModel;
import com.huanxin.oa.utils.PxUtil;

import java.util.List;

public class FormMergeView extends FrameLayout {
    Context context;
    List<List<FormModel>> columns;

    int itemHeight;
    int itemWidth;

    int height;
    int width;

    public void setColumns(List<List<FormModel>> columns) {
        this.columns = columns;
        initView();
    }

    public FormMergeView(@NonNull Context context) {
        this(context, null);
    }

    public FormMergeView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        initData();
        setBackgroundColor(context.getColor(R.color.white));
    }

    private void initData() {
        itemHeight = context.getResources().getDimensionPixelOffset(R.dimen.dp_40);
        itemWidth = PxUtil.getWidth(context) / 5;
    }

    private void initView() {
        for (int i = 0; i < columns.size(); i++) {
            List<FormModel> column = columns.get(i);
            width = itemWidth * i;
            for (int j = 0; j < column.size(); j++) {
                FormColumnView view = new FormColumnView(context);
                view.setMarginLeft(width);
                view.setMarginTop(height);
                view.setColumn(column.get(j));
                addView(view);
                height = height + itemHeight * column.get(j).getSpanHeight();
            }
            height = 0;
        }
    }
}
