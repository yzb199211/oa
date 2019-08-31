package com.huanxin.oa.view.review;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.huanxin.oa.R;
import com.huanxin.oa.review.model.ReviewProcess;
import com.huanxin.oa.utils.ImageLoaderUtil;
import com.huanxin.oa.utils.PxUtil;

public class ReviewProcessView extends RelativeLayout {
    Context context;
    LinearLayout llContent;
    TextView tvTitle;
    TextView tvContent;
    ImageView ivLogo;
    TextView tvDate;
    TextView tvRemark;
    EditText etRemark;
    View lineTop;
    View lineBottom;

    int llPaddingRight;
    int llPaddingTop;
    int llPaddingBottom;
    int imgsrc;
    String titleText;
    int titleTextColor;
    int titleTextSize;
    int titleWidth;
    String contentText;
    int contentTextColor;
    int contentTextSize;
    int contentPaddingLeft;
    String dateText;
    int dateTextColor;
    int dateTextSize;
    boolean tvRemarkVisiable;
    String tvRemarkText;
    int tvRemarkTextColor;
    int tvRemarkTextSize;
    boolean etRemarkVisiable;
    String etRemarkText;
    String etRemarkHint;
    int etRemarkTextSize;
    int etRemarkTextColor;
    int lineColor;
    int w, h;
    int linePositionW;
    ReviewProcess process;

    public ReviewProcessView(@NonNull Context context) {
        this(context, null);
    }

    public ReviewProcessView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }


    public ReviewProcessView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        measureView();
        linePositionW = (int) (PxUtil.dip2px(context, context.getResources().getDimension(R.dimen.dp_45)) / PxUtil.getDensity(context));
        intiTypeArray(attrs);
        intiView(context);
        setView(context);

    }

    private void intiTypeArray(AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.reviewProcess);
        llPaddingRight = a.getInteger(R.styleable.reviewProcess_llPaddingRight, -1);
        llPaddingTop = a.getInteger(R.styleable.reviewProcess_llPaddingTop, -1);
        llPaddingBottom = a.getInteger(R.styleable.reviewProcess_llPaddingRight, -1);
        titleText = a.getString(R.styleable.reviewProcess_titleText);
        titleTextColor = a.getColor(R.styleable.reviewProcess_titleColor, -1);
        titleTextSize = a.getInteger(R.styleable.reviewProcess_titleSize, -1);
        titleWidth = a.getInteger(R.styleable.reviewProcess_titleWidth, -1);
        contentText = a.getString(R.styleable.reviewProcess_contentText);
        contentTextColor = a.getColor(R.styleable.reviewProcess_contentTextColor, -1);
        contentTextSize = a.getInteger(R.styleable.reviewProcess_contentTextSize, -1);
        contentPaddingLeft = a.getInteger(R.styleable.reviewProcess_contentPaddingLeft, -1);
        dateText = a.getString(R.styleable.reviewProcess_dateText);
        dateTextColor = a.getColor(R.styleable.reviewProcess_dateTextColor, -1);
        dateTextSize = a.getInteger(R.styleable.reviewProcess_dateTextSize, -1);
        tvRemarkText = a.getString(R.styleable.reviewProcess_tvRemarkText);
        tvRemarkVisiable = a.getBoolean(R.styleable.reviewProcess_tvRemarkVisiable, false);
        tvRemarkTextColor = a.getColor(R.styleable.reviewProcess_tvRemarkTextColor, -1);
        tvRemarkTextSize = a.getColor(R.styleable.reviewProcess_tvRemarkTextSize, -1);
        etRemarkVisiable = a.getBoolean(R.styleable.reviewProcess_etRemarkVisiable, false);
        etRemarkHint = a.getString(R.styleable.reviewProcess_etRemarkHint);
        etRemarkText = a.getString(R.styleable.reviewProcess_etRemarkText);
        etRemarkTextColor = a.getColor(R.styleable.reviewProcess_etRemarkTextColor, -1);
        etRemarkTextSize = a.getInteger(R.styleable.reviewProcess_etRemarkTextSize, -1);
        imgsrc = a.getInteger(R.styleable.reviewProcess_imgsrc, -1);
        a.recycle();
    }

    private void intiView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.item_review_process, this, true);
        llContent = findViewById(R.id.ll_content);
        tvTitle = findViewById(R.id.tv_title);
        tvContent = findViewById(R.id.tv_content);
        tvDate = findViewById(R.id.tv_date);
        tvRemark = findViewById(R.id.tv_remark);
        etRemark = findViewById(R.id.et_remark);
        ivLogo = findViewById(R.id.iv_logo);
        lineTop = findViewById(R.id.line_top);
        lineBottom = findViewById(R.id.line_bottom);
        setBackgroundColor(context.getColor(R.color.white));
    }

    private void setView(Context context) {
        try {
            if (llPaddingTop != -1 && llPaddingRight != -1 && llPaddingBottom != -1)
                setLlPadding((int) context.getResources().getDimension(R.dimen.dp_20), llPaddingTop, llPaddingRight, llPaddingBottom);
            intiTitle();
            intiContent(context);
            intiDate();
            intiRemark();
            measure(w, h);
            ivLogo.measure(w, h);
        } catch (Exception e) {
            e.printStackTrace();

        }


    }

    /**
     * 初始化内间距
     *
     * @param llPaddingLeft
     * @param llPaddingTop
     * @param llPaddingRight
     * @param llPaddingBottom
     * @throws Exception
     */
    private void setLlPadding(int llPaddingLeft, int llPaddingTop, int llPaddingRight, int llPaddingBottom) throws Exception {
        llContent.setPadding(llPaddingLeft, llPaddingTop, llPaddingRight, llPaddingBottom);
    }

    /**
     * 初始化标题
     *
     * @throws Exception
     */
    private void intiTitle() throws Exception {
        if (!TextUtils.isEmpty(titleText))
            tvTitle.setText(titleText);
        if (titleTextColor != -1)
            tvTitle.setTextColor(titleTextColor);
        if (titleTextSize != -1)
            tvTitle.setTextSize(titleTextSize);
        if (titleWidth != -1)
            tvTitle.setWidth(titleWidth);
    }

    /**
     * 初始化内容
     *
     * @param context
     * @throws Exception
     */
    private void intiContent(Context context) throws Exception {
        if (!TextUtils.isEmpty(contentText))
            tvContent.setText(contentText);
        if (contentPaddingLeft != -1) {
            tvContent.setPadding(contentPaddingLeft, 0, 0, 0);
        }
        if (contentTextColor != -1)
            tvContent.setTextColor(contentTextColor);
        if (contentTextSize != -1)
            tvContent.setTextSize(contentTextSize);
    }

    /**
     * 初始化时间
     *
     * @throws Exception
     */
    private void intiDate() throws Exception {
        if (!TextUtils.isEmpty(dateText))
            tvDate.setText(dateText);
        if (dateTextColor != -1)
            tvDate.setTextColor(dateTextColor);
        if (dateTextSize != -1)
            tvDate.setTextSize(dateTextSize);
    }

    /**
     * 初始化备注
     *
     * @throws Exception
     */
    private void intiRemark() throws Exception {
        if (tvRemarkVisiable == true)
            tvRemark.setVisibility(VISIBLE);
        if (etRemarkVisiable == true)
            etRemark.setVisibility(VISIBLE);
        if (tvRemarkVisiable == true && etRemarkVisiable == true) {
            etRemark.setVisibility(GONE);
            tvRemarkVisiable = false;
        }
        if (!TextUtils.isEmpty(tvRemarkText))
            tvRemark.setText(tvRemarkText);
        if (tvRemarkTextColor != -1)
            tvRemark.setTextColor(tvRemarkTextColor);
        if (tvRemarkTextSize != -1)
            tvRemark.setTextSize(tvRemarkTextSize);
        if (etRemarkTextColor != -1)
            etRemark.setTextColor(etRemarkTextColor);
        if (!TextUtils.isEmpty(etRemarkText))
            etRemark.setText(etRemarkText);
        if (etRemarkTextSize != -1)
            etRemark.setTextSize(etRemarkTextSize);
        if (!TextUtils.isEmpty(etRemarkHint))
            etRemark.setHint(etRemarkHint);
    }


    private void measureView() {
        w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
    }

    public void setImgsrc(int imgsrc) {
        this.imgsrc = imgsrc;
        ImageLoaderUtil.loadCircleImg(ivLogo, imgsrc, R.drawable.placeholder);
    }

    public void setTitle(String titleText) {
        this.titleText = titleText;
        tvTitle.setText(titleText);
    }

    public void setTitleColor(int titleTextColor) {
        this.titleTextColor = titleTextColor;
        tvTitle.setTextColor(titleTextColor);
    }

    public void setTitleSize(int titleTextSize) {
        this.titleTextSize = titleTextSize;
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX,titleTextSize);
    }

    public void setTitleWidth(int titleWidth) {
        this.titleWidth = titleWidth;
        tvTitle.setWidth(titleWidth);
    }

    public void setContent(String contentText) {
        this.contentText = contentText;
        tvContent.setText(contentText);
    }

    public void setContentColor(int contentTextColor) {
        this.contentTextColor = contentTextColor;
        tvContent.setTextColor(contentTextColor);
    }

    public void setContentSize(int contentTextSize) {
        this.contentTextSize = contentTextSize;
        tvContent.setTextSize(TypedValue.COMPLEX_UNIT_PX,contentTextSize);
    }

    public void setContentPaddingLeft(int contentPaddingLeft) {
        this.contentPaddingLeft = contentPaddingLeft;
        tvContent.setPadding(contentPaddingLeft, 0, 0, 0);
    }

    public void setDate(String dateText) {
        this.dateText = dateText;
        tvDate.setText(dateText);
    }

    public void setDateColor(int dateTextColor) {
        this.dateTextColor = dateTextColor;
        tvDate.setTextColor(dateTextColor);
    }

    public void setDateSize(int dateTextSize) {
        this.dateTextSize = dateTextSize;
        tvDate.setTextSize(TypedValue.COMPLEX_UNIT_PX,dateTextSize);
    }

    public void setTvRemarkVisiable(boolean tvRemarkVisiable) {
        this.tvRemarkVisiable = tvRemarkVisiable;
        if (tvRemarkVisiable == true)
            tvRemark.setVisibility(VISIBLE);
        else
            tvRemark.setVisibility(GONE);
    }

    public void setTvRemarkText(String tvRemarkText) {
        this.tvRemarkText = tvRemarkText;
        tvRemark.setText(tvRemarkText);
    }

    public void setTvRemarkTextColor(int tvRemarkTextColor) {
        this.tvRemarkTextColor = tvRemarkTextColor;
        tvRemark.setTextColor(tvRemarkTextColor);
    }

    public void setTvRemarkTextSize(int tvRemarkTextSize) {
        this.tvRemarkTextSize = tvRemarkTextSize;
        tvRemark.setTextSize(TypedValue.COMPLEX_UNIT_PX,tvRemarkTextSize);
    }

    public void setEtRemarkVisiable(boolean etRemarkVisiable) {
        this.etRemarkVisiable = etRemarkVisiable;
        if (etRemarkVisiable == true)
            etRemark.setVisibility(VISIBLE);
        else
            etRemark.setVisibility(GONE);
    }

    public void setEtRemarkText(String etRemarkText) {
        this.etRemarkText = etRemarkText;
        etRemark.setText(etRemarkText);
    }

    public void setEtRemarkHint(String etRemarkHint) {
        this.etRemarkHint = etRemarkHint;
        etRemark.setHint(etRemarkHint);
    }

    public void setEtRemarkTextSize(int etRemarkTextSize) {
        this.etRemarkTextSize = etRemarkTextSize;
        etRemark.setTextSize(etRemarkTextSize);
    }

    public void setEtRemarkTextColor(int etRemarkTextColor) {
        this.etRemarkTextColor = etRemarkTextColor;
        etRemark.setTextColor(etRemarkTextColor);
    }

    public void setItem(ReviewProcess process) {
        this.process = process;
        loadView(process);
    }

    /**
     * 设置数据
     *
     * @param process
     */
    private void loadView(ReviewProcess process) {
        //标题设置
        if (!TextUtils.isEmpty(process.getTitleText()))
            setTitle(process.getTitleText());
        if (process.getTitleTextColor() != 0)
            setTitleColor(process.getTitleTextColor());
        if (process.getTitleTextSize() != 0)
            setTitleSize(process.getTitleTextSize());
        //内容设置
        if (!TextUtils.isEmpty(process.getContentText()))
            setContent(process.getContentText());
        if (process.getContentTextColor() != 0)
            setContentColor(process.getContentTextColor());
        if (process.getContentTextSize() != 0)
            setContentSize(process.getContentTextSize());
        if (process.getContentPaddingLeft() != 0)
            setContentPaddingLeft(process.getContentPaddingLeft());
        //时间设置
        if (!TextUtils.isEmpty(process.getDateText()))
            setDate(process.getDateText());
        if (process.getDateTextColor() != 0)
            setDateColor(process.getDateTextColor());
        if (process.getDateTextSize() != 0)
            setDateSize(process.getDateTextSize());
        //备注设置
        if (!TextUtils.isEmpty(process.getTvRemarkText()))
            setTvRemarkText(process.getTvRemarkText());
        if (process.getTvRemarkTextColor() != 0)
            setTvRemarkTextColor(process.getTvRemarkTextColor());
        if (process.getTvRemarkTextSize() != 0)
            setTvRemarkTextSize(process.getTvRemarkTextSize());

        if (!TextUtils.isEmpty(process.getEtRemarkText()))
            setEtRemarkText(process.getEtRemarkText());
        if (process.getEtRemarkTextColor() != 0)
            setEtRemarkTextColor(process.getEtRemarkTextColor());
        if (process.getEtRemarkTextSize() != 0)
            setEtRemarkTextSize(process.getEtRemarkTextSize());
        if (!TextUtils.isEmpty(process.getEtRemarkHint()))
            setEtRemarkHint(process.getEtRemarkHint());
        //设置备注显示编辑还是显示文本
        setTvRemarkVisiable(process.isTvRemarkVisiable());
        setEtRemarkVisiable(process.isEtRemarkVisiable());
        if (process.isEtRemarkVisiable() == true && process.isTvRemarkVisiable() == true)
            setTvRemarkVisiable(false);
        measure(w, h);
        int height = (int) (PxUtil.dip2px(context, context.getResources().getDimension(R.dimen.dp_60)) / PxUtil.getDensity(context));
        height = (int) (getMeasuredHeight() - height + context.getResources().getDimension(R.dimen.dp_20));
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) lineBottom.getLayoutParams();
        params.height = height;
        lineBottom.setLayoutParams(params);
    }

    /**
     * 设置进程线颜色
     *
     * @param lineColor
     */
    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
        lineTop.setBackgroundColor(lineColor);
        lineBottom.setBackgroundColor(lineColor);
    }

    public void setLineTop(boolean isShow) {
        if (isShow == true)
            lineTop.setVisibility(VISIBLE);
        else
            lineTop.setVisibility(GONE);

    }

    public void setLineBottom(boolean isShow) {
        if (isShow == true)
            lineBottom.setVisibility(VISIBLE);
        else
            lineBottom.setVisibility(GONE);
    }

    public ImageView getIvLogo() {
        return ivLogo;
    }

    public View getLineTop() {
        return lineTop;
    }

    public View getLineBottom() {
        return lineBottom;
    }

    public int getLlPaddingRight() {
        return llPaddingRight;
    }

    public int getLlPaddingTop() {
        return llPaddingTop;
    }

    public int getLlPaddingBottom() {
        return llPaddingBottom;
    }

    public int getImgsrc() {
        return imgsrc;
    }

    public String getTitleText() {
        return titleText;
    }

    public int getTitleTextColor() {
        return titleTextColor;
    }

    public int getTitleTextSize() {
        return titleTextSize;
    }

    public int getTitleWidth() {
        return titleWidth;
    }

    public String getContentText() {
        return contentText;
    }

    public int getContentTextColor() {
        return contentTextColor;
    }

    public int getContentTextSize() {
        return contentTextSize;
    }

    public int getContentPaddingLeft() {
        return contentPaddingLeft;
    }

    public String getDateText() {
        return dateText;
    }

    public int getDateTextColor() {
        return dateTextColor;
    }

    public int getDateTextSize() {
        return dateTextSize;
    }

    public boolean isTvRemarkVisiable() {
        return tvRemarkVisiable;
    }

    public String getTvRemarkText() {
        return tvRemarkText;
    }

    public int getTvRemarkTextColor() {
        return tvRemarkTextColor;
    }

    public int getTvRemarkTextSize() {
        return tvRemarkTextSize;
    }

    public boolean isEtRemarkVisiable() {
        return etRemarkVisiable;
    }

    public String getEtRemarkText() {
        return etRemarkText;
    }

    public String getEtRemarkHint() {
        return etRemarkHint;
    }

    public int getEtRemarkTextSize() {
        return etRemarkTextSize;
    }

    public int getEtRemarkTextColor() {
        return etRemarkTextColor;
    }

    public int getLineColor() {
        return lineColor;
    }

    public EditText getEtRemark() {
        return etRemark;
    }
}
