package com.huanxin.oa.form.merge;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.huanxin.oa.R;
import com.huanxin.oa.form.model.FormModel;
import com.huanxin.oa.utils.PxUtil;

@SuppressLint("AppCompatCustomView")
public class FormColumnView3 extends TextView {

    Context context;
    FormModel column;

    int row;
    int col;

    int startRow;
    int startCol;

    int itemWidth;
    int itemHeight;

    int marginTop;
    int marginLeft;

    boolean isDetail;

    public FormColumnView3(Context context) {
        this(context, null);
    }

    public FormColumnView3(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
//        initSize();
        initView();
    }

    private void initView() {
        setTextColor(context.getColor(R.color.default_content_color));
        setBackgroundColor(context.getColor(R.color.form_menu_white));
        setGravity(Gravity.CENTER);
        setSingleLine();
    }

    private void initSize() {
        int width = PxUtil.getWidth(context);
        itemWidth = PxUtil.getWidth(context) * column.getColumnWidth() / 100;
        itemHeight = context.getResources().getDimensionPixelOffset(R.dimen.dp_40);
    }

    public void setColumn(FormModel column, int startRow, int startCol, boolean isDetail) {
        this.column = column;
        this.startRow = startRow;
        this.isDetail = isDetail;
        this.startRow = startCol;
        initSize();
        if (isDetail) {
            marginTop = (column.getRow() - startRow) * itemHeight;
            marginLeft = (column.getMarginLeft() / 100) * itemWidth;
        }
        if (startCol == 0) {
            setTextColor(context.getColor(R.color.form_menu_title));
            setBackgroundColor(context.getColor(R.color.form_menu_item));
        }
        setRow(column.getRow());
        setCol(column.getCol());
        setText(column.getTitle());
        initParam();
    }

    public void initParam() {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(itemWidth - 1, column.getSpanHeight() * itemHeight - 1);
        params.topMargin = marginTop;
        params.leftMargin = marginLeft;
        setLayoutParams(params);
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getMarginTop() {
        return marginTop;
    }

    public void setMarginTop(int marginTop) {
        this.marginTop = marginTop;
    }

    public int getMarginLeft() {
        return marginLeft;
    }

    public void setMarginLeft(int marginLeft) {
        this.marginLeft = marginLeft;
    }
}
