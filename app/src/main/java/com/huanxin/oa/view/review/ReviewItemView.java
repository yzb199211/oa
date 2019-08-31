package com.huanxin.oa.view.review;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.huanxin.oa.R;
import com.huanxin.oa.review.model.ReviewItem;
import com.huanxin.oa.review.model.ReviewItems;
import com.huanxin.oa.review.utils.ReviewUtil;

import java.util.List;

public class ReviewItemView extends FrameLayout {
    Context context;
    ImageView ivLogo;
    LinearLayout llContent;
    TextView tvDate;
    TextView tvTime;
    String dateText;
    int dateTextColor;
    int dateTextSize;
    boolean dateIsBold;
    boolean dateVisiable;
    List<ReviewItems> list;


    public ReviewItemView(@NonNull Context context) {
        this(context, null);
    }

    public ReviewItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inti(context, attrs);
    }


    public ReviewItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inti(context, attrs);
    }

    private void inti(Context context, AttributeSet attrs) {
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.item_review, this, true);
        ivLogo = findViewById(R.id.iv_logo);
        tvDate = findViewById(R.id.tv_date);
        llContent = findViewById(R.id.ll_content);
        tvTime = findViewById(R.id.tv_time);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(params);
        setPadding(context.getResources().getDimensionPixelSize(R.dimen.dp_10), context.getResources().getDimensionPixelSize(R.dimen.dp_10), 0, context.getResources().getDimensionPixelSize(R.dimen.dp_10));
    }

    /**
     * 设置日期
     *
     * @param dateText
     */
    public void setDate(String dateText) {
        this.dateText = dateText;
        tvDate.setText(dateText);
    }

    /**
     * 设置日期字体颜色
     *
     * @param dateTextColor
     */
    public void setDateColor(int dateTextColor) {
        this.dateTextColor = dateTextColor;
        tvDate.setTextColor(dateTextColor);
    }

    /**
     * 设置日期字体是否为粗体
     *
     * @param isBold
     */
    public void setDateBold(boolean isBold) {
        this.dateIsBold = isBold;
        if (isBold)
            tvDate.setTypeface(Typeface.DEFAULT_BOLD);
        else
            tvDate.setTypeface(Typeface.DEFAULT);
    }

    /**
     * 设置日期是否显示
     *
     * @param visiable
     */
    public void setDateVisiable(boolean visiable) {
        this.dateVisiable = visiable;
        if (dateVisiable)
            tvDate.setVisibility(VISIBLE);
        else
            tvDate.setVisibility(GONE);
    }

    /**
     * 设置日期字体大小
     *
     * @param dateTextSize
     */
    public void setDateSize(int dateTextSize) {
        this.dateTextSize = dateTextSize;
        tvDate.setTextSize(TypedValue.COMPLEX_UNIT_PX, dateTextSize);
    }

    /**
     * 传入数据，添加内容
     *
     * @param list
     */
    public void setList(List<ReviewItems> list) {
        this.list = list;
        llContent.removeAllViews();
        if (list != null)
            for (int i = 0; i < list.size(); i++) {
                TextView textView = new TextView(context);
                textView.setText(list.get(i).getText());
                textView.setTextColor(list.get(i).getTextColor());
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, list.get(i).getTextSize());
                textView.setSingleLine();
                if (i == 0)
                    textView.setMaxEms(12);
                textView.setEllipsize(TextUtils.TruncateAt.END);
                textView.setPadding(0, 0
                        , 0, (int) context.getResources().getDimension(R.dimen.dp_5));
                llContent.addView(textView);
            }
    }

    public void setList(String content) {
//        llContent.removeAllViews();
        String[] arr = ReviewUtil.getContent(content, "\\|");
        for (int i = 0; i < arr.length; i++) {
            TextView textView = new TextView(context);
            textView.setText(arr[i]);
            if (i == 0) {
                textView.setMaxEms(12);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.sp_14));
                textView.setTextColor(context.getColor(R.color.default_text_color));
            } else {
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.sp_12));
                textView.setTextColor(context.getColor(R.color.default_content_color));
            }

            textView.setSingleLine();
            textView.setEllipsize(TextUtils.TruncateAt.END);
            textView.setPadding(0, 0
                    , 0, (int) context.getResources().getDimension(R.dimen.dp_5));
            llContent.addView(textView);
        }
    }

    public int getDateTextColor() {
        return dateTextColor;
    }

    public int getDateTextSize() {
        return dateTextSize;
    }

    public ImageView getIvLogo() {
        return ivLogo;
    }

    public String getDateText() {
        return dateText;
    }

    public boolean isDateIsBold() {
        return dateIsBold;
    }

    public boolean isDateVisiable() {
        return dateVisiable;
    }

    public TextView getTvTime() {
        return tvTime;
    }
}
