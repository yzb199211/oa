package com.huanxin.oa.view.review;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.huanxin.oa.R;
import com.huanxin.oa.review.model.ReviewSelect;
import com.huanxin.oa.view.review.adapter.ReviewSelectAdapter;
import com.huanxin.oa.view.review.util.Builder;

import java.util.ArrayList;
import java.util.List;

public class ReviewSelectView extends FrameLayout {
    Context context;
    int spanCounts;
    RecyclerView recyclerView;
    List<String> list = new ArrayList<>();
    List<ReviewSelect> selects = new ArrayList<>();
    ReviewSelectAdapter mApdater;
    GridLayoutManager manager;

    public ReviewSelectView(Context context) {
        this(context, null);
    }

    public ReviewSelectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inti(context, attrs);
    }

    public ReviewSelectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inti(context, attrs);
    }

    private void inti(Context context, AttributeSet attrs) {
        this.context = context;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.reviewSelectView);
        spanCounts = array.getInteger(R.styleable.reviewSelectView_spandCounts, 3);
        array.recycle();
        LayoutInflater.from(context).inflate(R.layout.review_select_layout, this, true);
        recyclerView = findViewById(R.id.rv_select);
        intiView();
    }

    private void intiView() {
        GridDecoration gridDecoration = new Builder(context).horColor(R.color.white).size(20).build();


//        setPadding(PxUtil.getWidth(context)/10,0,PxUtil.getWidth(context)/10,0);
//        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        View view = LayoutInflater.from(context).inflate(R.layout.bottom_submit_layout, this, false);
//        addView(view);
//        recyclerView = new RecyclerView(context);
//        recyclerView.setLayoutParams(params);
//        params.bottomMargin = context.getResources().getDimensionPixelSize(R.dimen.dp_50);
        manager = new GridLayoutManager(context, 3);

        recyclerView.addItemDecoration(gridDecoration);
        recyclerView.setBackgroundColor(context.getColor(R.color.white));

        setRecycleView();

    }

    private void setRecycleView() {
        list.add("衣服");
        list.add("裤子");
        list.add("被子");
        list.add("被子");
        list.add("被子");
        list.add("被子");
        selects.add(new ReviewSelect("衣服", 1, 1));
        selects.add(new ReviewSelect("大衣", 2));
        selects.add(new ReviewSelect("风衣", 3));
        selects.add(new ReviewSelect("卫衣", 4));
        selects.add(new ReviewSelect("裤子", 5, 1));
        selects.add(new ReviewSelect("中裤", 6));
        selects.add(new ReviewSelect("短裤", 7));
        selects.add(new ReviewSelect("八分裤", 8));
        selects.add(new ReviewSelect("八分裤", 8));
        mApdater = new ReviewSelectAdapter(selects, context);
        recyclerView.setAdapter(mApdater);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (selects.get(position).getType() != 1)
                    return 1;
                else
                    return manager.getSpanCount();
            }


        });
        recyclerView.setLayoutManager(manager);
    }
}
