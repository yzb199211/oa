package com.huanxin.oa.form.merge;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.huanxin.oa.R;
import com.huanxin.oa.form.model.FormModel;
import com.huanxin.oa.utils.PxUtil;

import java.util.List;

public class FormMergeView2 extends FrameLayout {
    Context context;
    FormModel data;
    int itemHeight;
    int itemWidth;

    int maxWidth;

    int startRow;

    public FormMergeView2(@NonNull Context context) {
        this(context, null);
    }

    public FormMergeView2(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public void setData(FormModel data, int maxWidth) {
        this.data = data;
        this.maxWidth = maxWidth;
        startRow = data.getRow();
        initView();
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
        FormColumnView2 view = new FormColumnView2(context);
        Log.e("length", data.getSpanHeightTotal() + "");
        view.setColumn(data, startRow);
        addView(view);
        setTotal(data);
        if (data.getRowData() == null) {
            if (data.getChild() != null) {
                setTitle(data.getChild());
            }
        } else {
            setRowView(data);
        }
    }

    private void setTitle(List<FormModel> child) {
        for (int i = 0; i < child.size(); i++) {
            FormModel item = child.get(i);
            FormColumnView2 view = new FormColumnView2(context);
            view.setColumn(item, startRow);
            addView(view);
            setTotal(item);
            if (item.getRowData() == null) {
                if (item.getChild() != null) {
                    setTitle(item.getChild());
                }
            } else {
                setRowView(item);
            }
        }

    }


    private void setRowView(FormModel data) {
        if (data.getRowData() != null)
            for (int i = 0; i < data.getRowData().size(); i++) {
                for (int j = 0; j < data.getRowData().get(i).size(); j++) {
                    FormColumnView2 view = new FormColumnView2(context);
                    view.setColumn(data.getRowData().get(i).get(j), startRow);
                    addView(view);
                }
            }
    }

    private void setTotal(FormModel item) {
        if (item.isParent()) {
            FormColumnView2 totalView = new FormColumnView2(context);
            FormModel total = new FormModel();
            total.setTitle(item.getTitle());
            total.setRow(item.getRow() - startRow + (item.getSpanHeightTotal() - 1));
            total.setCol(item.getCol() + 1);
            total.setSpanHeight(1);
            total.setSpanWidth(maxWidth - item.getCol() - 1);
            total.setTitle("总计");
            totalView.setColumn(total, 0);
            addView(totalView);
        }
    }
}
