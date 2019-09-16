package com.huanxin.oa.view.review;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.huanxin.oa.R;
import com.huanxin.oa.review.model.ReviewInfo;
import com.huanxin.oa.utils.PxUtil;

import java.util.ArrayList;
import java.util.List;

public class ReviewStyleView extends FrameLayout {

    TextView tvStyleTitle;
    Context context;
    String styleTitle;
    int styleTitleColor;
    int styleTitleBackground;
    int screenWidth;
    int height;
    int width;
    int w, h;
    int spanCounts = 3;
    float textSize;
    List<ReviewInfo> infoList = new ArrayList<>();
    LinearLayout.LayoutParams params;

    public ReviewStyleView(Context context) {
        this(context, null);
    }

    public ReviewStyleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        measureView();
        screenWidth = PxUtil.getWidth(context);
        setTitleText();
        setView();
    }

    private void setTitleText() {
        tvStyleTitle = new TextView(context);
        tvStyleTitle.setText(R.string.review_style_title);
        tvStyleTitle.setWidth(w / 3);
        tvStyleTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.sp_14));
        tvStyleTitle.setBackgroundColor(context.getColor(R.color.default_bg_color));
        tvStyleTitle.setTextColor(context.getColor(R.color.default_text_color));
        tvStyleTitle.setPadding((int) context.getResources().getDimension(R.dimen.dp_10), (int) context.getResources().getDimension(R.dimen.dp_4), 0, (int) context.getResources().getDimension(R.dimen.dp_2));
        tvStyleTitle.setSingleLine();
        addView(tvStyleTitle);
    }

    public void setStyleTitleVisiable(boolean ishow) {
        if (!ishow) {
            tvStyleTitle.setVisibility(GONE);
        }
    }

    /**
     * 设置标题文字
     *
     * @param styleTitle
     */
    public void setStyleTitle(String styleTitle) {
        this.styleTitle = styleTitle;
        tvStyleTitle.setText(styleTitle);
    }

    /**
     * 设置标题颜色
     *
     * @param styleTitleColor 颜色
     */
    public void setStyleTitleColor(int styleTitleColor) {
        this.styleTitleColor = styleTitleColor;
        tvStyleTitle.setTextColor(styleTitleColor);
    }

    /**
     * 设置标题背景色
     *
     * @param styleTitleBackground 背景颜色
     */
    public void setStyleTitleBackground(int styleTitleBackground) {
        this.styleTitleBackground = styleTitleBackground;
        tvStyleTitle.setBackgroundColor(styleTitleBackground);
    }

    //设置单行最大个数
    public void setSpanCounts(int spanCounts) {
        this.spanCounts = spanCounts;
    }

    /**
     * 传入样式List
     *
     * @param spanCounts 当行最大个数
     * @param infoList   样式列表
     */
    public void setInfoList(int spanCounts, List<ReviewInfo> infoList) {
        this.spanCounts = spanCounts;
        this.infoList = infoList;
        setView();
    }

    /**
     * 传入样式List
     *
     * @param infoList
     */
    public void setInfoList(List<ReviewInfo> infoList) {
        this.infoList = infoList;
        setView();
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding(left, top, right, bottom);
        invalidate();
    }

    /**
     * 加载样式
     */
    private void setView() {
        int count = 1;
        int itemWidth = (int) (screenWidth - tvStyleTitle.getPaddingLeft() * 2 - getPaddingLeft() * 2);
        //测量标题
        tvStyleTitle.measure(w, h);
        textSize = tvStyleTitle.getTextSize();
        // 获取高度
        height = tvStyleTitle.getMeasuredHeight();
        for (int i = 0; i < infoList.size(); i++) {

            ReviewInfo item = infoList.get(i);
            ReviewInfoView rivItem = getItem(item);
            //判断是否为单行
            if (item.isSingleLine() == false) {
                //当余数唯1时表示该数据为本行第一项，判断是否是最后一个或者下一个是否为当行，是的话设置长度为最大并且将height加上本项高度。
                if (count % spanCounts == 1) {
                    count++;
                    if (i + 1 == infoList.size() || (i + 1 < infoList.size() && infoList.get(i + 1).isSingleLine() == true))
                        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    else
                        params = new LinearLayout.LayoutParams((int) (itemWidth * (item.getWidthPercent())), ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.setMargins(0, height, 0, 0);
                    rivItem.setLayoutParams(params);
                    rivItem.setPadding(0, (int) context.getResources().getDimension(R.dimen.dp_10), 0, 0);
                    addView(rivItem);
                    rivItem.measure(w, h);
                    width = (int) (width + itemWidth * (item.getWidthPercent()));
                    if (i + 1 == infoList.size() || (i + 1 < infoList.size() && infoList.get(i + 1).isSingleLine() == true))
                        height = height + rivItem.getMeasuredHeight();
                }
                //该项为最后一项时，高度自动加上本行，Width置0,。
                else if (count == spanCounts) {
                    count = 1;
                    params = new LinearLayout.LayoutParams((int) (screenWidth - width), ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.setMargins(width, height, 0, 0);
                    rivItem.setLayoutParams(params);
                    rivItem.setPadding(10, (int) context.getResources().getDimension(R.dimen.dp_10), 0, 0);
                    addView(rivItem);
                    rivItem.measure(w, h);
                    height = height + rivItem.getMeasuredHeight();
                    width = 0;
                }
                //判断是否是最后一个或者下一个是否为当行，是的话设置长度为最大并且将height加上本项高度。
                else if ((count % spanCounts != 1) & (count != spanCounts)) {

                    count++;
                    if (i + 1 == infoList.size() || (i + 1 < infoList.size() && infoList.get(i + 1).isSingleLine() == true))
                        params = new LinearLayout.LayoutParams((int) (itemWidth - width), ViewGroup.LayoutParams.WRAP_CONTENT);
                    else
                        params = new LinearLayout.LayoutParams((int) (itemWidth * (item.getWidthPercent())), ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.setMargins(width, height, 0, 0);
                    rivItem.setLayoutParams(params);
                    rivItem.setPadding(10, (int) context.getResources().getDimension(R.dimen.dp_10), 0, 0);
                    addView(rivItem);
                    rivItem.measure(w, h);
                    if (i + 1 == infoList.size() || (i + 1 < infoList.size() && infoList.get(i + 1).isSingleLine() == true))
                        height = height + rivItem.getMeasuredHeight();
                    width = (int) (width + itemWidth * (item.getWidthPercent()));
                }
            }
            //当数据为单行是默认设置宽度为最大，自动换行，高度加一行
            else {
                count = 1;
                width = 0;
                params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, height, 0, 0);
                rivItem.setLayoutParams(params);
                rivItem.setPadding(0, (int) context.getResources().getDimension(R.dimen.dp_10), 0, 0);
                addView(rivItem);
                rivItem.measure(w, h);
                height = height + rivItem.getMeasuredHeight();
            }
        }
    }

    private ReviewInfoView getItem(ReviewInfo item) {
        ReviewInfoView rivItem = new ReviewInfoView(context);
        rivItem.setBackgroundColor(context.getColor(R.color.white));
        rivItem.setTitle(item.getTitle());
        if (item.getTitleColor() != 0)
            rivItem.setTitleColor(item.getTitleColor());
        if (item.isTitleBold() == true)
            rivItem.setTitleBold(true);
        rivItem.setContent(item.getContent());
        if (item.getContentColor() != 0)
            rivItem.setContentColor(item.getContentColor());
        if (item.isContentBold() == true)
            rivItem.setContentBold(true);
        return rivItem;
    }

    private void measureView() {
        w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
    }
}
