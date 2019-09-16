package com.huanxin.oa.form.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.huanxin.oa.review.model.ReviewStyle;
import com.huanxin.oa.view.review.util.ReviewStyleView1;

import java.util.List;

public class FormListAdapter extends RecyclerView.Adapter<FormListAdapter.VH> {
    Context context;
    List<ReviewStyle> styles;

    public FormListAdapter(Context context, List<ReviewStyle> styles) {
        this.context = context;
        this.styles = styles;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new VH(new ReviewStyleView1(context));
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.setIsRecyclable(false);
        ((ReviewStyleView1) holder.itemView).setTitleVisiable(false);
        ((ReviewStyleView1) holder.itemView).setInfoList(styles.get(position).getList());
//        ((ReviewStyleView1)holder.itemView).setPadding(0,0,0,context.getResources().getDimensionPixelOffset(R.dimen.dp_10));
    }

    @Override
    public int getItemCount() {
//        Log.e("size",""+styles.size());
        return styles.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        public VH(View v) {
            super(v);
        }
    }
}
