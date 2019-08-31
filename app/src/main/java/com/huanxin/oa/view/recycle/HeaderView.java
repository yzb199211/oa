package com.huanxin.oa.view.recycle;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.huanxin.oa.R;

public class HeaderView extends BaseHeaderOrFooterView {

    private ProgressBar progressBar;
    private ImageView image;
    private TextView text;
    String relaseStr;
    String pullStr;
    String refreshStr;


    public HeaderView(Context context, @NonNull ViewGroup root) {
        super(context, root, HEADER);

    }

    @Override
    protected void onViewCreated(View view) {
        progressBar = view.findViewById(R.id.progress);
        image = view.findViewById(R.id.image);
        text = view.findViewById(R.id.text);
    }

    @Override
    protected int onBindLayoutId() {
        return R.layout.recycle_head_view;
    }

    /**
     * 当header view的topMargin>=0时，说明已经完全显示出来了,修改header view 的提示状态
     * 释放刷新的提示
     */
    @Override
    public void releaseToRefreshOrLoad() {
        text.setText(TextUtils.isEmpty(relaseStr) ? "释放刷新数据" : relaseStr);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void pullToRefreshOrLoad() {
        text.setText(TextUtils.isEmpty(pullStr) ? "下拉刷新数据" : pullStr);
        progressBar.setVisibility(View.GONE);
    }

    /**
     * 正在刷新
     */
    @Override
    public void isRefreshingOrLoading() {
        text.setText(TextUtils.isEmpty(refreshStr) ? "正在刷新..." : refreshStr);
        image.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    /**
     * 刷新完成的回调
     */
    @Override
    public void refreshOrLoadComplete() {
        text.setText(TextUtils.isEmpty(pullStr) ? "下拉刷新数据" : pullStr);
        progressBar.setVisibility(View.GONE);
        image.setVisibility(View.VISIBLE);
    }

    @Override
    public void getPercentage(float rate) {
        //这里设置的是根据下拉头显示的百分比进行一个头部图片动态缩放的效果
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) image.getLayoutParams();
        params.width = (int) (80 * rate);
        params.height = (int) (80 * rate);
        image.setLayoutParams(params);
    }

    public void setRelaseStr(String relaseStr) {
        this.relaseStr = relaseStr;
    }

    public void setPullStr(String pullStr) {
        this.pullStr = pullStr;
    }

    public void setRefreshStr(String refreshStr) {
        this.refreshStr = refreshStr;
    }
}
