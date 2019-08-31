package com.huanxin.oa.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.huanxin.oa.R;
import com.huanxin.oa.main.interfaces.OnItemClickListener;
import com.huanxin.oa.model.main.FormChildMenusBean;
import com.huanxin.oa.utils.CodeUtil;
import com.huanxin.oa.utils.FontUtil;

import java.util.List;

public class FormAdapter extends RecyclerView.Adapter {

    List<FormChildMenusBean> menus;
    Context context;
    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public FormAdapter(List<FormChildMenusBean> menus, Context context) {
        this.menus = menus;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == CodeUtil.MENUS_TITLE) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_form_menu_title, parent, false);
            return new TitleHolder(view);
        } else {
            View menus = LayoutInflater.from(context).inflate(R.layout.item_menu_usual, parent, false);
            return new MenuHolder(menus);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (menus.get(position).isTitle()) {
            ((TitleHolder) holder).tvTitle.setText(menus.get(position).getSMenuName());
        } else {
            ((MenuHolder) holder).tvMenu.setText(menus.get(position).getSMenuName());
            ((MenuHolder) holder).ivMenu.setImageResource(FontUtil.getImg(menus.get(position).getSIcon()));
            ((MenuHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null)
                        onItemClickListener.onItemClick(v, position);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return menus.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (menus.get(position).isTitle())
            return CodeUtil.MENUS_TITLE;
        else
            return CodeUtil.MENUS_CHIld;
    }

    private class TitleHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;

        public TitleHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
        }
    }

    private class MenuHolder extends RecyclerView.ViewHolder {
        ImageView ivMenu;
        TextView tvMenu;

        public MenuHolder(@NonNull View itemView) {
            super(itemView);
            ivMenu = itemView.findViewById(R.id.iv_menu);
            tvMenu = itemView.findViewById(R.id.tv_menu);
        }
    }


}
