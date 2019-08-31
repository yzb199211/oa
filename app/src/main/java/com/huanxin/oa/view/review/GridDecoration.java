package com.huanxin.oa.view.review;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.huanxin.oa.view.review.util.Builder;

public class GridDecoration extends RecyclerView.ItemDecoration {
    Builder builder;
    Paint paintVer;
    Paint paintHor;

    public GridDecoration(Builder builder) {
        this.builder = builder;
        inti(builder);
    }

    private void inti(Builder builder) {
        this.builder = builder;
        paintHor = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintHor.setStyle(Paint.Style.FILL);
        paintHor.setColor(builder.horColor);
        paintVer = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintVer.setStyle(Paint.Style.FILL);
        paintVer.setColor(builder.verColor);
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        drawHor(c, parent);
        drawVer(c, parent);
    }

    /**
     * 画竖线
     *
     * @param c
     * @param parent
     */
    private void drawVer(Canvas c, RecyclerView parent) {
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (builder.isExistHeadView && i == 0)
                continue;

            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getTop() - params.topMargin;
            final int bottom = child.getBottom() + params.bottomMargin;
            final int left = child.getRight() + params.rightMargin;
            final int right = left + builder.dividerVerSize;

            c.drawRect(left, top, right, bottom, paintVer);
        }
    }

    /**
     * 画横线
     *
     * @param c
     * @param parent
     */
    private void drawHor(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (builder.isExistHeadView && i == 0)
                continue;
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getLeft() - params.leftMargin;
            final int right = child.getRight() + params.rightMargin
                    + builder.dividerVerSize;
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + builder.dividerHorSize;
            c.drawRect(left, top, right, bottom, paintHor);
        }
    }

    /**
     * 获取列数
     */
    private int getSpanCount(RecyclerView parent) {
        // 列数
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {

            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();

        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager)
                    .getSpanCount();
        }
        return spanCount;
    }

    /**
     * 判断是否最后一行
     *
     * @param parent
     * @param pos
     * @param spanCount
     * @param childCount
     * @return
     */
    private boolean isLastRaw(RecyclerView parent, int pos, int spanCount,
                              int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            childCount = childCount - childCount % spanCount;
            if (pos >= childCount)
                return true;
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
            // StaggeredGridLayoutManager 且纵向滚动
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                childCount = childCount - childCount % spanCount;

                if (pos >= childCount)
                    return true;
            } else
            // StaggeredGridLayoutManager 且横向滚动
            {

                if ((pos + 1) % spanCount == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int spanCount = getSpanCount(parent);
        int childCount = parent.getAdapter().getItemCount();
        int itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        if (builder.isExistHeadView)
            itemPosition -= 1;
        if (itemPosition < 0)
            return;
        int column = itemPosition % spanCount;
        int bottom = 0;

        int left = column * builder.dividerVerSize / spanCount;
        int right = builder.dividerVerSize - (column + 1) * builder.dividerVerSize / spanCount;

        if (!(isLastRaw(parent, itemPosition, spanCount, childCount) && !builder.isShowLastDivider))
            bottom = builder.dividerHorSize;

        outRect.set(left, 0, right, bottom);
        marginOffsets(outRect, spanCount, itemPosition);
    }

    /**
     * 设置偏移量
     *
     * @param outRect
     * @param spanCount
     * @param itemPosition
     */
    private void marginOffsets(Rect outRect, int spanCount, int itemPosition) {
        if (builder.marginRight == 0 && builder.marginLeft == 0)
            return;
        int itemShrink = (builder.marginLeft + builder.marginRight) / spanCount;

        outRect.left += (builder.marginLeft - (itemPosition % spanCount) * itemShrink);

        outRect.right += ((itemPosition % spanCount) + 1) * itemShrink - builder.marginLeft;
    }

}
