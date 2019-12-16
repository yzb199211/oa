package com.huanxin.oa.form.merge;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.huanxin.oa.form.model.FormModel;

import java.util.List;

public class FormMergeAdapter2 extends RecyclerView.Adapter<FormMergeAdapter2.VH> {
    Context context;
    List<FormModel> list;
    int colCount;

    public FormMergeAdapter2(Context context, List<FormModel> list, int colCount) {
        this.context = context;
        this.list = list;
        this.colCount = colCount;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(new FormMergeView2(context));
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        ((FormMergeView2) holder.itemView).setData(list.get(position), colCount);
        holder.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        public VH(View v) {
            super(v);
        }
    }
}
