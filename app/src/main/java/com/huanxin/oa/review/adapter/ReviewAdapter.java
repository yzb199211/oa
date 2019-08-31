package com.huanxin.oa.review.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.huanxin.oa.R;
import com.huanxin.oa.interfaces.OnRecycleClickListener;
import com.huanxin.oa.review.ReviewDetailActivity;
import com.huanxin.oa.review.model.ReviewItem;
import com.huanxin.oa.review.utils.ReviewUtil;
import com.huanxin.oa.utils.ImageLoaderUtil;
import com.huanxin.oa.view.review.ReviewItemView;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.VH> {
    private final static String TAG = "ReviewAdapter";
    Context context;
    List<ReviewItem> reviewItems;
    OnRecycleClickListener onRecycleClickListener;

    public void setOnRecycleClickListener(OnRecycleClickListener onRecycleClickListener) {
        this.onRecycleClickListener = onRecycleClickListener;
    }

    public ReviewAdapter(Context context, List<ReviewItem> reviewItems) {
        this.context = context;
        this.reviewItems = reviewItems;

    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ReviewItemView view = new ReviewItemView(context);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        //取消复用之后数据不会在产生错乱
//        holder.setIsRecyclable(false);
        ReviewItem item = reviewItems.get(position);
        holder.reviewItem.setTag(position);
        holder.llContent.removeAllViews();
//        Log.e(TAG, item.getContent()+"!");
        if (TextUtils.isEmpty(item.getImgSrc()))
            ImageLoaderUtil.loadCircleImg(holder.ivLogo, R.drawable.placeholder, R.drawable.placeholder);
        else
            ImageLoaderUtil.loadCircleImg(holder.ivLogo, item.getImgSrc(), R.drawable.placeholder);
        if (!TextUtils.isEmpty(item.getText())) {
            String[] date = ReviewUtil.getContent(item.getText(), "T");
            if (!TextUtils.isEmpty(item.getText()))
                holder.reviewItem.setDate(date[0]);
            if (date.length > 1)
                holder.tvTime.setText(date[1]);
        }

        if (item.getTextSize() != 0) {
            holder.reviewItem.setDateSize(item.getTextSize());
            holder.tvTime.setTextSize(TypedValue.COMPLEX_UNIT_PX, item.getTextSize());
        }
        if (item.getTextColor() != 0) {
            holder.reviewItem.setDateColor(item.getTextColor());
            holder.tvTime.setTextColor(item.getTextColor());
        }
        holder.reviewItem.setList(item.getContent());

        holder.reviewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRecycleClickListener != null)
                    onRecycleClickListener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return reviewItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class VH extends RecyclerView.ViewHolder {
        ImageView ivLogo;
        TextView tvDate;
        TextView tvTime;
        LinearLayout llContent;
        ReviewItemView reviewItem;

        public VH(View v) {
            super(v);
            ivLogo = v.findViewById(R.id.iv_logo);
            tvDate = v.findViewById(R.id.tv_date);
            llContent = v.findViewById(R.id.ll_content);
            tvTime = v.findViewById(R.id.tv_time);
            reviewItem = (ReviewItemView) v;
        }
    }
}
