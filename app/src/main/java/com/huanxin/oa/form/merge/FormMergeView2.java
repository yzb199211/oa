package com.huanxin.oa.form.merge;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.huanxin.oa.R;
import com.huanxin.oa.form.model.FormModel;
import com.huanxin.oa.utils.PxUtil;
import com.huanxin.oa.utils.StringUtil;

import java.util.List;

public class FormMergeView2 extends FrameLayout {
    Context context;
    FormModel data;
    int itemHeight;
    int itemWidth;
    int scrennWidth;

    int maxWidth;
    int startRow;
    boolean isFirstParent;

    public FormMergeView2(@NonNull Context context) {
        this(context, null);
    }

    public FormMergeView2(@NonNull Context context, @Nullable AttributeSet attrs) {
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
        setBackgroundColor(context.getColor(R.color.form_menu_bg));
    }

    private void initData() {
        itemHeight = context.getResources().getDimensionPixelOffset(R.dimen.dp_40);
        itemWidth = PxUtil.getWidth(context) / 4;
        scrennWidth = PxUtil.getWidth(context);
    }

    private void initView() {
        initParam();
        FormColumnView3 view = new FormColumnView3(context);
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
        LayoutParams params = new LayoutParams((data.getTotalWidth() + data.getColumnWidth()) * scrennWidth / 100, data.getSpanHeight() * itemHeight);
        params.leftMargin = data.getMarginLeft() * scrennWidth / 100;
        if (!isFirstParent)
            params.topMargin = (data.getRow()) * itemHeight;
        setLayoutParams(params);
    }

    private void setTitle(List<FormModel> child) {
        int row = 0;
        for (int i = 0; i < child.size(); i++) {
            FormModel item = child.get(i);
            item.setRow(row);
            FormMergeView2 view = new FormMergeView2(context);
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
                    FormColumnView3 view = new FormColumnView3(context);
                    view.setColumn(data.getRowData().get(i).get(j), startRow, startCol, true);
                    addView(view);
                }
            }
        }
    }

    private void setTotal(FormModel item) {
        if (item.isParent()) {
            FormColumnView3 totalView = new FormColumnView3(context);
            FormModel total = new FormModel();
            total.setTitle(item.getTitle());
            total.setRow((item.getSpanHeight() - 1));
            total.setCol(item.getCol() + 1);
            total.setSpanHeight(1);
            total.setSpanWidth(maxWidth - item.getCol() - 1);
            total.setMarginLeft(data.getColumnWidth());
            total.setColumnWidth(data.getTotalWidth());
            total.setTitle("总计:" + StringUtil.double2Str(item.getTotal()));
            totalView.setColumn(total, 0, item.getCol() + 1, true);
            totalView.setBackgroundColor(context.getColor(R.color.form_menu_total));
            totalView.setTextColor(context.getColor(R.color.default_text_color));
            totalView.setTypeface(Typeface.DEFAULT_BOLD);
            addView(totalView);
        }
    }
}
