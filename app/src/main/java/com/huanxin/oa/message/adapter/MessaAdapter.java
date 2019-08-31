package com.huanxin.oa.message.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.huanxin.oa.R;
import com.huanxin.oa.model.message.MessageItem;
import com.huanxin.oa.utils.ImageLoaderUtil;
import com.huanxin.oa.utils.net.NetConfig;

import java.util.List;

public class MessaAdapter extends RecyclerView.Adapter<MessaAdapter.VH> {

    private List<MessageItem> messages = null;
    private Context mContext = null;

    public MessaAdapter(List<MessageItem> messages, Context mContext) {
        this.messages = messages;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        try {
            holder.tvTitle.setText(messages.get(position).getSTitle());
            holder.tvDate.setVisibility(View.GONE);
            holder.tvContent.setVisibility(View.GONE);
            ImageLoaderUtil.loadCircleImg(holder.ivLogo, NetConfig.url_img + messages.get(position).getSImageUrl(), R.mipmap.pic_loading);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static class VH extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvDate;
        TextView tvContent;
        ImageView ivLogo;

        public VH(View v) {
            super(v);
            tvTitle = v.findViewById(R.id.tv_title);
            tvDate = v.findViewById(R.id.tv_date);
            tvContent = v.findViewById(R.id.tv_content);
            ivLogo = v.findViewById(R.id.iv_logo);
        }
    }
}
