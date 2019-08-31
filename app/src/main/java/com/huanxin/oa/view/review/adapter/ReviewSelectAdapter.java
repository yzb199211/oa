package com.huanxin.oa.view.review.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.huanxin.oa.R;
import com.huanxin.oa.review.model.ReviewSelect;
import com.huanxin.oa.view.review.ClickListener;
import com.huanxin.oa.view.review.ReviewSelectItem;

import java.util.List;

public class ReviewSelectAdapter extends RecyclerView.Adapter {
    List<ReviewSelect> selects;
    Context context;

    public ReviewSelectAdapter(List<ReviewSelect> selects, Context context) {
        this.selects = selects;
        this.context = context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ReviewSelectItem view = new ReviewSelectItem(context);
        view.setNormalColor(context.getColor(R.color.colorAccent));
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((VH) holder).item.setText(selects.get(position).getTitle());
        Log.e("positiom", position + "");
        ((VH) holder).item.setCustomOnClickListener(new ClickListener() {
            @Override
            public void Click(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return selects.size();
    }

    public static class VH extends RecyclerView.ViewHolder {
        ReviewSelectItem item;

        public VH(View v) {
            super(v);
            item = (ReviewSelectItem) v;

        }
    }

}
