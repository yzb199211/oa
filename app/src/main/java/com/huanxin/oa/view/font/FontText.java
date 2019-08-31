package com.huanxin.oa.view.font;


import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;

import androidx.appcompat.widget.AppCompatTextView;

import com.huanxin.oa.R;
import com.huanxin.oa.utils.FontUtil;

public class FontText extends AppCompatTextView {

    public FontText(Context context) {
        super(context);
        setTypeface(FontUtil.getTypeface(context));
    }

    public FontText(Context context, AttributeSet attr) {
        super(context, attr);
        setTypeface(FontUtil.getTypeface(context));
        setGravity(Gravity.CENTER);
        setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimensionPixelSize(R.dimen.sp_24));
    }
}
