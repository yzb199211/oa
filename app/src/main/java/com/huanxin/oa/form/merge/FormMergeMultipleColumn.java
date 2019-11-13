package com.huanxin.oa.form.merge;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.huanxin.oa.R;
import com.huanxin.oa.form.model.FormModel;
import com.huanxin.oa.utils.PxUtil;

import java.util.List;

public class FormMergeMultipleColumn extends LinearLayout {
    Context context;
    List<FormModel> columns;

    public FormMergeMultipleColumn(Context context) {
        this(context, null);
    }

    public FormMergeMultipleColumn(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        initView();
    }

    private void initView() {
        ViewGroup.LayoutParams params = new LayoutParams(3 * PxUtil.getWidth(context) / 2, context.getResources().getDimensionPixelOffset(R.dimen.dp_40));
        setLayoutParams(params);
        setOrientation(HORIZONTAL);
        setBackgroundColor(context.getColor(R.color.white));
    }

    public void setColumns(List<FormModel> columns) {
        this.columns = columns;
        addColumn();
    }

    private void addColumn() {
        for (FormModel column : columns) {
            FormColumnView view = new FormColumnView(context);
            view.setColumn(column);
            addView(view);
        }
    }

    public static FormMergeMultipleColumn build(Context context, List<FormModel> columns) {
        FormMergeMultipleColumn view = new FormMergeMultipleColumn(context);
        view.setColumns(columns);
        return view;
    }
}
