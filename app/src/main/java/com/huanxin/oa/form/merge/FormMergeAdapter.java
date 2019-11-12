package com.huanxin.oa.form.merge;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class FormMergeAdapter extends RecyclerView.Adapter<FormMergeAdapter.VH> {
    List<List<FormMerge>> list;
    Context context;

    public FormMergeAdapter(Context context, List<List<FormMerge>> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

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
