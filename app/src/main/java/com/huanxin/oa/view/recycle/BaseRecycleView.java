package com.huanxin.oa.view.recycle;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;

public abstract class BaseRecycleView<T extends RecyclerView> extends LinearLayout {
    protected T mRecyclerView;

    private boolean isCanScrollAtRereshing = false;//刷新时是否可滑动
    private boolean isCanPullDown = true;//是否可下拉
    private boolean isCanPullUp = true;//是否可上拉
    // pull state
    private static final int PULL_UP_STATE = 0;
    private static final int PULL_DOWN_STATE = 1;
    // refresh states
    private static final int PULL_TO_REFRESH = 2;
    private static final int RELEASE_TO_REFRESH = 3;
    private static final int REFRESHING = 4;

    //记住上次落点的坐标
    private int mLastMotionY;
    //头状态
    private int mHeaderState;
    //尾状态
    private int mFooterState;
    //下拉状态
    private int mPullState;
    HeaderView mHeaderView;
    FooterView mFooterView;


    //刷新接口-提供下拉刷新+上拉加载的回调方法
    private OnRefreshListener refreshListener;

    /**
     * Interface definition for a callback to be invoked when list/grid footer
     * view should be refreshed.
     */
    public interface OnRefreshListener {
        //下拉刷新的回调方法
        void onPullToRefresh(BaseRecycleView view);

        //上拉加载的回调方法
        void onPullToLoadMore(BaseRecycleView view);
    }

    public BaseRecycleView(Context context) {
        this(context, null);
    }

    public BaseRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inti(context, attrs);
    }


    public BaseRecycleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inti(context, attrs);
    }

    private void inti(Context context, AttributeSet attrs) {
        mRecyclerView = createRecyclerView(context, attrs);
        //设置RecyclerView全屏显示
        mRecyclerView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        //这里仅仅添加了一个RecyclerView只是做占位使用，在我们具体设置头布局的时候
        //会清空LinearLayout中所有的View，重新添加头布局，然后添加RecyclerView
        addView(mRecyclerView);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            //刷新时禁止滑动
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (!isCanScrollAtRereshing) {
                    if (mHeaderState == REFRESHING || mFooterState == REFRESHING) {
                        return true;
                    }
                }
                break;
        }

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int y = (int) ev.getRawY();
        int x = (int) ev.getRawX();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 首先拦截down事件,记录y坐标
                mLastMotionY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                // deltaY > 0 是向下运动,< 0是向上运动
                int deltaY = y - mLastMotionY;
                if (isRefreshViewScroll(deltaY)) {
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return false;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                int deltaY = y - mLastMotionY;
                if (isCanPullDown && mPullState == PULL_DOWN_STATE) {
                    //头布局准备刷新
                    headerPrepareToRefresh(deltaY);
                } else if (isCanPullUp && mPullState == PULL_UP_STATE) {
                    //尾布局准备加载
                    footerPrepareToRefresh(deltaY);
                }
                mLastMotionY = y;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                //当我们的手指离开屏幕的时候，应该判断做什么处理
                int topMargin = getHeaderTopMargin();
                if (isCanPullDown && mPullState == PULL_DOWN_STATE) {
                    if (topMargin >= 0) {
                        // 开始刷新
                        headerRefreshing();
                    } else {
                        // 还没有执行刷新，重新隐藏
                        setHeaderTopMargin(-mHeaderView.getViewHeight());
                    }
                } else if (isCanPullUp && mPullState == PULL_UP_STATE) {
                    if (Math.abs(topMargin) >= mHeaderView.getViewHeight() + mFooterView.getViewHeight()) {
                        // 开始执行footer 刷新
                        footerRefreshing();
                    } else {
                        // 还没有执行刷新，重新隐藏
                        setHeaderTopMargin(-mHeaderView.getViewHeight());
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * header 准备刷新,手指移动过程,还没有释放
     *
     * @param deltaY ,手指滑动的距离
     */
    private void headerPrepareToRefresh(int deltaY) {
        int newTopMargin = changingHeaderViewTopMargin(deltaY);
        // 当headerview的topMargin>=0时，说明已经完全显示出来了,修改header view的提示状态
        if (newTopMargin >= 0 && mHeaderState != RELEASE_TO_REFRESH) {
            //调用我们自定义头布局的释放刷新操作，具体代码在自定义headerview中实现
            mHeaderView.releaseToRefreshOrLoad();
            mHeaderState = RELEASE_TO_REFRESH;
        } else if (newTopMargin < 0 && newTopMargin > -mHeaderView.getViewHeight()) {// 拖动时没有释放
            //调用我们自定义头布局的下拉刷新操作，具体代码在自定义headerview中实现
            mHeaderView.pullToRefreshOrLoad();
            mHeaderState = PULL_TO_REFRESH;
        }
    }

    /**
     * footer准备刷新,手指移动过程,还没有释放 移动footerview高度同样和移动header view
     * 高度是一样，都是通过修改headerview的topmargin的值来达到
     *
     * @param deltaY ,手指滑动的距离
     */
    private void footerPrepareToRefresh(int deltaY) {
        int newTopMargin = changingHeaderViewTopMargin(deltaY);
        // 如果header view topMargin 的绝对值大于或等于header + footer 的高度
        // 说明footer view 完全显示出来了，修改footer view 的提示状态
        if (Math.abs(newTopMargin) >= (mHeaderView.getViewHeight() + mFooterView.getViewHeight()) && mFooterState != RELEASE_TO_REFRESH) {
            //调用我们自定义尾布局的释放加载操作，具体代码在自定义footerview中实现
            mFooterView.releaseToRefreshOrLoad();
            mFooterState = RELEASE_TO_REFRESH;
        } else if (Math.abs(newTopMargin) < (mHeaderView.getViewHeight() + mFooterView.getViewHeight())) {
            //调用我们自定义尾布局的上拉加载操作，具体代码在自定义footerview中实现
            mFooterView.pullToRefreshOrLoad();
            mFooterState = PULL_TO_REFRESH;
        }
    }

    /**
     * header refreshing
     */
    public void headerRefreshing() {
        mHeaderState = REFRESHING;
        //设置头布局完全显示
        setHeaderTopMargin(0);
        //通过自定义头布局回调方法，实行我们自定义的逻辑
        mHeaderView.isRefreshingOrLoading();
        if (refreshListener != null) {
            //接口回调，用于处理网络
            refreshListener.onPullToRefresh(this);
        }
    }

    /**
     * footer refreshing
     */
    private void footerRefreshing() {
        mFooterState = REFRESHING;
        //将我们的头布局margin设为头布局+尾布局高度和，这样尾布局将完全显示
        int top = mHeaderView.getViewHeight() + mFooterView.getViewHeight();
        setHeaderTopMargin(-top);
        //通过自定义尾布局回调方法，实行我们自定义的逻辑
        mFooterView.isRefreshingOrLoading();
        if (refreshListener != null) {
            //接口回调，用于网络处理
            refreshListener.onPullToLoadMore(this);
        }
    }

    /**
     * 设置header view 的topMargin的值
     *
     * @param topMargin ，为0时，说明header view 刚好完全显示出来； 为-mHeaderViewHeight时，说明完全隐藏了
     * @description
     */
    private void setHeaderTopMargin(int topMargin) {
        LayoutParams params = (LayoutParams) mHeaderView.getView().getLayoutParams();
        params.topMargin = topMargin;
        mHeaderView.getView().setLayoutParams(params);
        invalidate();
    }


    /**
     * 修改Headerview topmargin的值
     *
     * @param deltaY
     * @description
     */
    private int changingHeaderViewTopMargin(int deltaY) {
        LayoutParams params = (LayoutParams) mHeaderView.getView().getLayoutParams();
        float newTopMargin = params.topMargin + deltaY * 0.3f;
        setHeaderViewSize(params);
        // 这里对上拉做一下限制,因为当前上拉后然后不释放手指直接下拉,会把下拉刷新给触发了
        // 表示如果是在上拉后一段距离,然后直接下拉
        if (deltaY > 0 && mPullState == PULL_UP_STATE && Math.abs(params.topMargin) <= mHeaderView.getViewHeight()) {
            //如果每次偏移量>0，说明是下拉刷新，并且params.topMargin绝对值小于等于头布局高度
            //说明头布局还未完全显示，直接返回
            return params.topMargin;
        }
        // 同样地,对下拉做一下限制,避免出现跟上拉操作时一样的bug
        if (deltaY < 0 && mPullState == PULL_DOWN_STATE && Math.abs(params.topMargin) >= mHeaderView.getViewHeight()) {
            return params.topMargin;
        }
        params.topMargin = (int) newTopMargin;
        mHeaderView.getView().setLayoutParams(params);
        invalidate();
        return params.topMargin;
    }

    /**
     * 此处要做的事情是通过布局返回一个百分比大小，供有的头布局动画使用
     *
     * @param params
     */
    private void setHeaderViewSize(LayoutParams params) {
        if ((mHeaderView.getViewHeight() + params.topMargin) <= mHeaderView.getViewHeight()) {
            //如果我们头布局的高度是正值，params.topMargin是负值
            //当头布局从完全隐藏到刚好显示的过程是0～mHeaderView.getViewHeight()的过程
            //所以用它做分子，分母就是我们的头布局高度
            //计算并通过方法返回比例
            DecimalFormat format = new DecimalFormat("0.00");
            float differ = mHeaderView.getViewHeight() + params.topMargin;
            float total = mHeaderView.getViewHeight();
            float rate = differ / total;
            mHeaderView.getPercentage(Float.parseFloat(format.format(rate)));
        } else {
            //走到这儿说明我们的头布局已经被继续下拉，超过了本身的大小
            //所以返回1恒定值表示100%，不在继续增加
            mHeaderView.getPercentage(1.00f);
        }
    }

    ;

    /**
     * 获取当前header view 的topMargin
     *
     * @description
     */
    private int getHeaderTopMargin() {
        LayoutParams params = (LayoutParams) mHeaderView.getView().getLayoutParams();
        return params.topMargin;
    }


    private boolean isRefreshViewScroll(int deltaY) {
        if (mHeaderState == REFRESHING || mFooterState == REFRESHING) {
            return false;
        }
        if (deltaY >= -20 && deltaY <= 20)
            return false;
        if (mRecyclerView != null) {
            if (deltaY > 0) {
                View child = mRecyclerView.getChildAt(0);
                if (child == null) {
                    // 如果mRecyclerView中没有数据,不拦截
                    return false;
                }
                if (isScrollTop() && child.getTop() == 0) {
                    //如果滑动到了顶端，要拦截事件交由自己处理
                    mPullState = PULL_DOWN_STATE;
                    return true;
                }
                int top = child.getTop();
                int padding = mRecyclerView.getPaddingTop();
                if (isScrollTop() && Math.abs(top - padding) <= 8) {
                    // 这里之前用3可以判断,但现在不行,还没找到原因
                    mPullState = PULL_DOWN_STATE;
                    return true;
                }
            } else if (deltaY < 0) {
                View lastChild = mRecyclerView.getChildAt(mRecyclerView.getChildCount() - 1);
                if (lastChild == null) {
                    // 如果mRecyclerView中没有数据,不拦截
                    return false;
                }
                // 最后一个子view的Bottom小于父View的高度说明mRecyclerView的数据没有填满父view,
                // 等于父View的高度说明mRecyclerView已经滑动到最后
                if (lastChild.getBottom() <= getHeight() && isScrollBottom()) {
                    mPullState = PULL_UP_STATE;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断mRecyclerView是否滑动到顶部
     *
     * @return
     */
    boolean isScrollTop() {
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        if (linearLayoutManager.findFirstVisibleItemPosition() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 设置是否可以在刷新时滑动
     *
     * @param canScrollAtRereshing
     */
    public void setCanScrollAtRereshing(boolean canScrollAtRereshing) {
        isCanScrollAtRereshing = canScrollAtRereshing;
    }

    /**
     * 设置是否可上拉
     *
     * @param canPullUp
     */
    public void setCanPullUp(boolean canPullUp) {
        isCanPullUp = canPullUp;
    }

    /**
     * 设置是否可下拉
     *
     * @param canPullDown
     */
    public void setCanPullDown(boolean canPullDown) {
        isCanPullDown = canPullDown;
    }

    /**
     * 判断mRecyclerView是否滑动到底部
     *
     * @return
     */
    boolean isScrollBottom() {
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        if (linearLayoutManager.findLastVisibleItemPosition() == (mRecyclerView.getAdapter().getItemCount() - 1)) {
            return true;
        } else {
            return false;
        }
    }

    protected abstract T createRecyclerView(Context context, AttributeSet attrs);

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }


    /**
     * 用于设置刷新列表头部显示样式
     *
     * @param headerView
     * @return
     */
    public BaseRecycleView setHeaderView(HeaderView headerView) {
        this.mHeaderView = headerView;
        //清除所有的view，重新添加布局
        removeAllViews();
        //添加我们自定义的头布局
        addView(mHeaderView.getView(), mHeaderView.getParams());
        addView(mRecyclerView);
        return this;
    }

    /**
     * 用于设置刷新列表底部显示样式
     *
     * @param footerView
     * @return
     */
    public BaseRecycleView setFooterView(FooterView footerView) {
        this.mFooterView = footerView;
        //添加我们自定义的尾布局
        addView(mFooterView.getView(), mFooterView.getParams());
        return this;
    }

    /**
     * header view 完成更新后恢复初始状态
     */
    public void onHeaderRefreshComplete() {
        setHeaderTopMargin(-mHeaderView.getViewHeight());
        //通过自定义头布局更新我们刷新完成后的头布局各控件的状态和显示
        mHeaderView.refreshOrLoadComplete();
        mHeaderState = PULL_TO_REFRESH;
    }

    /**
     * footer view 完成更新后恢复初始状态
     */
    public void onFooterRefreshComplete() {
        setHeaderTopMargin(-mHeaderView.getViewHeight());
        //通过自定义尾布局更新我们刷新完成后的尾布局各控件的状态和显示
        mFooterView.refreshOrLoadComplete();
        mFooterState = PULL_TO_REFRESH;
        if (mRecyclerView != null) {
            //加载完后列表停留在最后一项
            mRecyclerView.scrollToPosition(mRecyclerView.getAdapter().getItemCount() - 1);
        }
    }

}
