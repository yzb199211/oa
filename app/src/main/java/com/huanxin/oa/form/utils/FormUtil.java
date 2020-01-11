package com.huanxin.oa.form.utils;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;

import com.huanxin.oa.form.model.FormChartBean;

public class FormUtil {
    public static TextView getColumn(Context context, FormChartBean styles) {
        TextView textView = new TextView(context);
        textView.setGravity(styles.getGravity() | Gravity.CENTER_VERTICAL);
        textView.setTypeface(styles.getTypeface());
        textView.setTextColor(context.getColor(styles.getTextColor()));
        textView.setText(styles.getText());
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimensionPixelSize(styles.getTextSize()));
        textView.setBackgroundColor(context.getColor(styles.getBackgroundColor()));
        textView.setPadding(context.getResources().getDimensionPixelOffset(styles.getPaddingLeft()), context.getResources().getDimensionPixelOffset(styles.getPaddingTop()), context.getResources().getDimensionPixelOffset(styles.getPaddingRight()), context.getResources().getDimensionPixelOffset(styles.getPaddingBottom()));
        return textView;
    }

}
