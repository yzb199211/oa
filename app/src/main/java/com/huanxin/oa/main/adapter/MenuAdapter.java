package com.huanxin.oa.main.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.huanxin.oa.R;
import com.huanxin.oa.main.interfaces.OnItemClickListener;
import com.huanxin.oa.main.utils.Menu;
import com.huanxin.oa.view.dotview.BadgeView;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.VH> {

    private List<Menu> menus = null;
    private Context mContext = null;


    /*实例化图片点击接口*/
    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public MenuAdapter(List<Menu> menus, Context mContext) {
        this.menus = menus;
        this.mContext = mContext;
    }

    public static class VH extends RecyclerView.ViewHolder {
        TextView tvMenu;
        BadgeView bvMenu;

        public VH(View v) {
            super(v);
            tvMenu = v.findViewById(R.id.tv_menu);
            bvMenu = v.findViewById(R.id.bv_menu);
        }
    }


    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final VH holder, final int position) {
        try {
            holder.tvMenu.setText(menus.get(position).getStr());
            holder.bvMenu.setIconSrc(menus.get(position).getImg());
            holder.bvMenu.setBadgeNum(menus.get(position).getMsg());
            /*设置点击事件接口*/
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(v, position);
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return menus.size();

    }
}
