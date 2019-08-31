package com.huanxin.oa.view.recycle;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecycleTitleItemDecoration extends RecyclerView.ItemDecoration {
    private final Drawable mDivider;
    private final int mSize;
    private final int titleHeight;
    private final int mOrientation;
    List<View> lists = new ArrayList<>();


    public RecycleTitleItemDecoration(Context context, @ColorRes int color,
                                      @DimenRes int size, int orientation) {
        mDivider = new ColorDrawable(context.getColor(color));
        mSize = context.getResources().getDimensionPixelSize(size);
        mOrientation = orientation;
        titleHeight = context.getResources().getDimensionPixelOffset(size);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        //通过查看源码可知道获取position的方法
        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        if (position > -1) {
            if (position == 0) {
                //默认第一个位置，肯定是要设置悬浮栏的，这里只需要上边top留出空间即可
                outRect.set(0, titleHeight, 0, 0);//里面参数表示：左上右下的内边距padding距离
            } else {
                //继续分情况判断，当滑动到某一个item时(position位置)得到tag标签，与上一个item对应的标签不一致( position-1 位置)，说明这是下一分组了
                if (lists.get(position).getTag() != null && !lists.get(position).getTag().equals(lists.get(position - 1).getTag())) {
                    //说明这是下一组了，需要留出空间好去绘制悬浮栏用
                    outRect.set(0, titleHeight, 0, 0);
                } else {
                    //标签相同说明是同一组的数据，比如都是 A 组下面的数据，那么就不需要再留出空间绘制悬浮栏了，共用同一个 A 组即可
                    outRect.set(0, 0, 0, 0);
                }
            }
        }

    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        //先画出带有背景颜色的矩形条悬浮栏，从哪个位置开始绘制到哪个位置结束，则需要先确定位置，再画文字（即：title）
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        //父view（RecyclerView）有padding值，子view有margin值
        int childCount = parent.getChildCount();//得到的数据其实是一屏可见的item数量并非总item数，再复用
        for(int i = 0; i < childCount; i++){
            View child = parent.getChildAt(i);
            //子view（即：item）有可能设置有margin值，所以需要parms来设置margin值。
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            //以及 获取 position 位置
            int position = params.getViewLayoutPosition();
            if(position > -1){
                if(position == 0){//肯定是要绘制一个悬浮栏 以及 悬浮栏内的文字
                    //画矩形悬浮条以及文字
                   drawRectAndText(c, left, right, child, params, position);
                }else{
                    if(lists.get(position).getTag() != null && !lists.get(position).getTag().equals(lists.get(position - 1).getTag())){
                        //和上一个Tag不一样，说明是另一个新的分组
                        //画矩形悬浮条以及文字
                        drawRectAndText(c, left, right, child, params, position);
                    }else{
                        //说明是一组的，什么也不画，共用同一个Tag
                    }
                }
            }

        }
    }

    private void drawRectAndText(Canvas c, int left, int right, View child, RecyclerView.LayoutParams params, int position) {

        mDivider.setBounds(params.rightMargin+child.getRight(), params.topMargin, right, params.bottomMargin);
        mDivider.draw(c);
    }
}
