package com.huanxin.oa.view.recycle;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.huanxin.oa.R;

public class FooterView extends BaseHeaderOrFooterView {

    private TextView mFooterTextView;
    private ProgressBar mFooterProgressBar;

    public FooterView(Context context, @NonNull ViewGroup root) {
        super(context, root, FOOTER);
    }

    @Override
    protected void onViewCreated(View view) {
        mFooterTextView = view.findViewById(R.id.pull_to_load_text);
        mFooterProgressBar = view.findViewById(R.id.pull_to_load_progress);
    }

    @Override
    protected int onBindLayoutId() {
        return R.layout.recycle_footer_view;
    }

    @Override
    public void releaseToRefreshOrLoad() {
        mFooterTextView.setText("松开加载更多");
    }

    @Override
    public void pullToRefreshOrLoad() {
        mFooterTextView.setText("上拉加载更多");
    }

    @Override
    public void isRefreshingOrLoading() {
        mFooterTextView.setVisibility(View.GONE);
        mFooterProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void refreshOrLoadComplete() {
        mFooterTextView.setText("上拉加载更多");
        mFooterTextView.setVisibility(View.VISIBLE);
        mFooterProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void getPercentage(float rate) {
        //用不上的话可以不实现具体逻辑
    }
}
