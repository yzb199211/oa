package com.huanxin.oa.view.cycle.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;


import com.huanxin.oa.view.cycle.CircleBanner;
import com.huanxin.oa.view.cycle.listener.ViewHolder;

import java.util.List;

public class CirclePagerAdapter<T> extends PagerAdapter {
    private List<T> list;
    private CircleBanner viewPager;
    private HolderCreator holderCreator;
    private boolean isCanLoop;

    public void setCanLoop(boolean canLoop) {
        isCanLoop = canLoop;
    }

    public CirclePagerAdapter(List<T> list, CircleBanner viewPager, HolderCreator holderCreator) {
        this.list = list;
        this.viewPager = viewPager;
        this.holderCreator = holderCreator;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        View view = getView(position, container);
        container.addView(view);
        return view;
    }


    //  根据图片URL创建对应的ImageView并添加到集合
    private View getView(final int position, ViewGroup container) {
        ViewHolder holder = holderCreator.createViewHolder();
        if (holder == null) {
            throw new RuntimeException("can not return a null holder");
        }
        View view = null;
        if (list != null && list.size() > 0) {
            if (isCanLoop) {
                int size = list.size();
                if (list.size() > 1) {
                    size = list.size() - 2;
                }
                if (position == 0) {
                    //改为
                    view = holder.createView(container.getContext(), 0);
                    holder.onBind(container.getContext(), list.get(0), 0, size);
                } else if (position == list.size() - 1) {
                    view = holder.createView(container.getContext(), 0);
                    holder.onBind(container.getContext(), list.get(list.size() - 1), 0, size);
                } else {
                    view = holder.createView(container.getContext(), position - 1);
                    holder.onBind(container.getContext(), list.get(position), position - 1, size);
                }
            } else {
                view = holder.createView(container.getContext(), position);
                holder.onBind(container.getContext(), list.get(position), position, list.size());
            }
        }

        if (view != null)
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.imageClick(position);
                }
            });
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        super.finishUpdate(container);

    }
}