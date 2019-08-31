package com.huanxin.oa.view.cycle.adapter;


import com.huanxin.oa.view.cycle.listener.ViewHolder;

public interface HolderCreator<VH extends ViewHolder> {
    /**
     * 创建ViewHolder
     */
    VH createViewHolder();
}