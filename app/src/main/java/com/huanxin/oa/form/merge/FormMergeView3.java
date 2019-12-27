package com.huanxin.oa.form.merge;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.huanxin.oa.R;
import com.huanxin.oa.form.model.FormModel;
import com.huanxin.oa.utils.LogUtil;
import com.huanxin.oa.utils.PxUtil;

import java.util.List;

public class FormMergeView3 extends FrameLayout {
    Context context;
    FormModel data;
    int itemHeight;
    int itemWidth;

    int maxWidth;
    int startRow;
    boolean isFirstParent;

    public FormMergeView3(@NonNull Context context) {
        this(context, null);
    }

    public FormMergeView3(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public void setData(FormModel data, int maxWidth, boolean isFirstParent) {
        this.data = data;
        this.maxWidth = maxWidth;
        this.isFirstParent = isFirstParent;
        startRow = data.getRow();
//        Log.e("data", startRow + "");
        initView();
    }

    private void init() {
        initData();
        setBackgroundColor(context.getColor(R.color.white));
    }

    private void initData() {
        itemHeight = context.getResources().getDimensionPixelOffset(R.dimen.dp_40);
        itemWidth = PxUtil.getWidth(context) / 4;
    }

    private void initView() {
        initParam();
        FormColumnView2 view = new FormColumnView2(context);
        view.setColumn(data, startRow, 0, false);
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

    private void initParam() {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(maxWidth * itemWidth, data.getSpanHeight() * itemHeight);
        params.leftMargin = data.getCol() * itemWidth;
        if (!isFirstParent)
            params.topMargin = (data.getRow()) * itemHeight;
        setLayoutParams(params);
    }

    private void setTitle(List<FormModel> child) {
        int row = 0;
        for (int i = 0; i < child.size(); i++) {
            FormModel item = child.get(i);
            item.setRow(row);
            FormMergeView3 view = new FormMergeView3(context);
            view.setData(item, maxWidth - 1, false);
            addView(view);
            row = row + item.getSpanHeightTotal();
        }
    }

    private void setRowView(FormModel data) {
        if (data.getRowData() != null) {
            int startRow = data.getRowData().get(0).get(0).getRow();
            int startCol = data.getRowData().get(0).get(0).getCol();
            for (int i = 0; i < data.getRowData().size(); i++) {
                for (int j = 0; j < data.getRowData().get(i).size(); j++) {
                    FormColumnView2 view = new FormColumnView2(context);
                    view.setColumn(data.getRowData().get(i).get(j), startRow, startCol, true);
                    addView(view);
                }
            }
        }
    }

    private void setTotal(FormModel item) {
        if (item.isParent()) {
//            Log.e("height", item.getSpanHeightTotal() + "");
//            LogUtil.e("total", new Gson().toJson(item));
            FormColumnView2 totalView = new FormColumnView2(context);
            FormModel total = new FormModel();
            total.setTitle(item.getTitle());
            total.setRow((item.getSpanHeight() - 1));
            total.setCol(item.getCol() + 1);
            total.setSpanHeight(1);
            total.setSpanWidth(maxWidth - item.getCol() - 1);
            total.setTitle("总计:"+item.getTotal());
            totalView.setColumn(total, 0, item.getCol() + 1, true);
            addView(totalView);
        }
    }
}
