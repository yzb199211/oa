package com.huanxin.oa.form.merge;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.huanxin.oa.form.model.FormModel;

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
        setOrientation(HORIZONTAL);
    }
}
