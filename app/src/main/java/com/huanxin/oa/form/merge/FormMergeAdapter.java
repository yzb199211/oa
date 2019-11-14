package com.huanxin.oa.form.merge;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.huanxin.oa.form.FormMergeView;
import com.huanxin.oa.form.model.FormModel;

import java.util.List;


public class FormMergeAdapter extends RecyclerView.Adapter<FormMergeAdapter.VH> {
    List<List<FormModel>> list;
    Context context;

    public FormMergeAdapter(Context context, List<List<FormModel>> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        VH vh = new VH(new FormMergeMultipleColumn(context));
        VH vh = new VH(new FormMergeView(context));

        vh.setIsRecyclable(false);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        ((FormMergeView) holder.itemView).setColumns(list);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class VH extends RecyclerView.ViewHolder {
        public VH(View v) {
            super(v);
        }
    }
}
