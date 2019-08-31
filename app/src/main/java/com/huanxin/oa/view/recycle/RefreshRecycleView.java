package com.huanxin.oa.view.recycle;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class RefreshRecycleView extends BaseRecycleView<RecyclerView> {
    public RefreshRecycleView(Context context) {
        super(context);
    }

    public RefreshRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected RecyclerView createRecyclerView(Context context, AttributeSet attrs) {
        return new RecyclerView(context, attrs);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }

    public void setLayoutManager(RecyclerView.LayoutManager manager) {
        mRecyclerView.setLayoutManager(manager);
    }

}
