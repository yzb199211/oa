package com.huanxin.oa.form.merge;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.huanxin.oa.R;
import com.huanxin.oa.form.model.FormModel;
import com.huanxin.oa.utils.PxUtil;

@SuppressLint("AppCompatCustomView")
public class FormColumnView extends TextView {

    Context context;
    FormModel column;

    int row;
    int col;
    int itemWidth;
    int itemHeight;

    int marginTop;
    int marginLeft;

    public FormColumnView(Context context) {
        this(context, null);
    }

    public FormColumnView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        initSize();
        initView();

    }

    private void initView() {
        setTextColor(context.getColor(R.color.default_content_color));
        setBackgroundColor(context.getColor(R.color.blue1));
        setGravity(Gravity.CENTER);
        setSingleLine();
    }

    private void initSize() {
        itemWidth = PxUtil.getWidth(context) / 5;
        itemHeight = context.getResources().getDimensionPixelOffset(R.dimen.dp_40);
    }

    public void setColumn(FormModel column) {
        this.column = column;
        setRow(column.getRow());
        setCol(column.getCol());
        setText(column.getTitle());
        initParam();
    }

    public void initParam() {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(itemWidth-1, column.getSpanHeight() * itemHeight - 1);
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
