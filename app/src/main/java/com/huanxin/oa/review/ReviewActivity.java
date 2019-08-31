package com.huanxin.oa.review;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.huanxin.oa.R;
import com.huanxin.oa.interfaces.OnRecycleClickListener;
import com.huanxin.oa.interfaces.ResponseListener;
import com.huanxin.oa.model.review.ReviewBean;
import com.huanxin.oa.model.review.ReviewedBean;
import com.huanxin.oa.model.review.ReviewingBean;
import com.huanxin.oa.review.adapter.ReviewAdapter;
import com.huanxin.oa.review.model.ReviewItem;
import com.huanxin.oa.utils.CodeUtil;
import com.huanxin.oa.utils.Toasts;
import com.huanxin.oa.utils.net.NetConfig;
import com.huanxin.oa.utils.net.NetParams;
import com.huanxin.oa.utils.net.NetUtil;
import com.huanxin.oa.view.review.ReviewTabView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReviewActivity extends AppCompatActivity {
    private final String TAG = "ReviewActivity";

    private final int pageSize = 30;
    private int pageNum = 1;
    private int tabPosition = 0;//当前选中类型
    private boolean isRefresh = false;//判断是否是刷新数据
    private boolean isLoad = false;//判断是否是加载更多数据
    private boolean isLast = false;//判断是否是最后一个
    List<ReviewItem> reviewingItems = new ArrayList<>();//未审核列表
    List<ReviewItem> reviewedItems = new ArrayList<>();//已审核列表
    List<ReviewItem> reviewItems = new ArrayList<>();//审核列表
    List<ReviewItem> currentItems = new ArrayList<>();//当前加载到的数据
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.rv_reviewed)
    XRecyclerView rvReview;
    @BindView(R.id.top_line)
    View topLine;
    @BindView(R.id.tab_view)
    ReviewTabView tabView;

    RecyclerView.LayoutManager layoutManager;
    ReviewAdapter adapter;
    String url = NetConfig.url + NetConfig.Review_Method;
    Intent intent;
    private boolean isFirst = true;
    private boolean refreshReviewed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        ButterKnife.bind(this);
        init();
        setTab();
        getMessage(tabPosition, url);
//
    }

    /**
     * 控件绑定
     */
    private void init() {

        tvTitle.setText(R.string.title_review_list);
        tvTitle.setTextColor(getColor(R.color.default_text_color));
        rlTop.setBackgroundColor(getColor(R.color.white));
        ivBack.setVisibility(View.VISIBLE);
        topLine.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.VISIBLE);
        initList();

    }

    /**
     * 初始化列表
     */
    private void initList() {
        rvReview.setLoadingMoreEnabled(false);
        rvReview.setPullRefreshEnabled(false);
        rvReview.setLoadingMoreProgressStyle(2);
        rvReview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                refreshReviewed();
            }

            @Override
            public void onLoadMore() {
                pageNum += pageNum;
                isLoad = true;
                getMessage(tabPosition, url);
            }
        });
    }

    /**
     * 刷新已审核列表
     */
    private void refreshReviewed() {
        reviewedItems.clear();
        reviewItems.clear();
        if (adapter != null)
            adapter.notifyDataSetChanged();
        pageNum = 1;
        isRefresh = true;
        isLast = false;
        getMessage(tabPosition, url);
    }

    /**
     * 刷新未审核列表
     */
    private void reviewReviewing() {
        reviewingItems.clear();
        reviewItems.clear();
        if (adapter != null)
            adapter.notifyDataSetChanged();
        getMessage(tabPosition, url);
    }

    /**
     * 设置切换按钮
     */
    private void setTab() {
        List<String> list = new ArrayList<>();
        list.add("未审核");
        list.add("已审核");
        tabView.addTabs(list);
        tabView.setTabSelectListener(new ReviewTabView.TabSelectListener() {
            @Override
            public void tabSelect(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position != tabPosition) {
                    isTab(position);
                    tabPosition = position;
                }
            }
        });
    }

    /**
     * 判断选中那个按钮
     *
     * @param position
     */
    private void isTab(int position) {
        if (position == 0) {
            rvReview.setPullRefreshEnabled(false);
            rvReview.setLoadingMoreEnabled(false);
            setReviewing(position);
        } else if (position == 1) {
            rvReview.setPullRefreshEnabled(true);
            if (isLast == false)
                rvReview.setLoadingMoreEnabled(true);
            setReviewed(position);
        }
    }

    /**
     * 设置已审核列表
     *
     * @param position
     */
    private void setReviewed(int position) {
        if (refreshReviewed == true)
            reviewedItems.clear();
        if (reviewedItems.size() == 0)
            getMessage(position, url);
        else {
            reviewItems.clear();
            reviewItems.addAll(reviewedItems);
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 设置未审核列表
     *
     * @param position
     */
    private void setReviewing(int position) {
        if (reviewingItems.size() == 0) {
            getMessage(position, url);
        } else {
            reviewItems.clear();
            reviewItems.addAll(reviewingItems);
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 获取数据
     */
    private void getMessage(int tab, String url) {
        new NetUtil(getParams(tab), url, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                try {
                    if (tab == 0)
                        initReviewing(tab, string);
                    else {
                        initReviewed(tab, string);
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isRefresh == true) {
                            rvReview.refreshComplete();
                            isRefresh = false;
                        }
                        if (isLoad == true) {
                            pageNum -= pageNum;
                            rvReview.loadMoreComplete();
                            isLoad = false;
                        }
                        Toasts.showLong(ReviewActivity.this, e.getMessage());
                    }
                });

            }
        });
    }

    /**
     * 初始化已审核数据
     *
     * @param tab
     * @param string
     */
    private void initReviewed(int tab, String string) {
        Gson gson = new Gson();
        ReviewBean reviewBean = gson.fromJson(string, ReviewBean.class);
        if (!TextUtils.isEmpty(string))
            if (reviewBean.isSuccess()) {
                List<ReviewedBean> reviewedList = new ArrayList<>();
                reviewedList.addAll(reviewBean.getTables().getCheckList());
                initReviewed(tab, reviewedList);
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isRefresh == true) {
                            rvReview.refreshComplete();
                            isRefresh = false;
                        }
                        if (isLoad == true) {
                            rvReview.loadMoreComplete();
                            isLoad = false;
                        }
                        Toasts.showShort(ReviewActivity.this, reviewBean.getMessage());
                    }
                });
            }
    }

    /**
     * 初始化已审核列表
     *
     * @param tab
     * @param reviewedList
     */
    private void initReviewed(int tab, List<ReviewedBean> reviewedList) {
        currentItems.clear();
        for (int i = 0; i < reviewedList.size(); i++) {
            ReviewedBean reviewedBean = reviewedList.get(i);
            ReviewItem reviewItem = setTime(reviewedBean.getDDealDate());
            reviewItem.setContent(reviewedBean.getSAppContent());
            reviewItem.setId(reviewedBean.getIRecNo());
            reviewItem.setFormId(reviewedBean.getIFormid());
            reviewItem.setBillId(reviewedBean.getIBillRecNo());
            currentItems.add(reviewItem);
        }
        setView(tab);
    }

    /**
     * 接口参数
     *
     * @return
     */
    private List<NetParams> getParams(int tab) {
        intent = getIntent();
        List<NetParams> list = new ArrayList<>();
        list.add(new NetParams("userid", intent.getStringExtra("userid")));
        if (tab == 0) {
            list.add(new NetParams("otype", "GetNoCheckList"));
        } else {
            list.add(new NetParams("otype", "GetCheckedList"));
            list.add(new NetParams("pageNo", pageNum + ""));
            list.add(new NetParams("pageSize", pageSize + ""));
        }
        return list;
    }

    /**
     * 初始化未审核数据
     *
     * @param tab
     * @param string
     */
    private void initReviewing(int tab, String string) {
        Gson gson = new Gson();
        ReviewBean reviewBean = gson.fromJson(string, ReviewBean.class);
        if (reviewBean.isSuccess()) {
            List<ReviewingBean> reviewingList = new ArrayList<>();
            reviewingList.addAll(reviewBean.getTables().getNoCheckList());
            initReviewing(tab, reviewingList);

        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toasts.showShort(ReviewActivity.this, reviewBean.getMessage());
                }
            });
        }
    }

    /**
     * 初始化未审核列表数据
     *
     * @param tab
     * @param reviewingList
     */
    private void initReviewing(int tab, List<ReviewingBean> reviewingList) {
        currentItems.clear();
        for (int i = 0; i < reviewingList.size(); i++) {
            ReviewingBean reviewingBean = reviewingList.get(i);
            ReviewItem reviewItem = setTime(reviewingBean.getdInputDate());
            reviewItem.setContent(reviewingBean.getSAppContent());
            reviewItem.setId(reviewingBean.getIRecNo());
            reviewItem.setFormId(reviewingBean.getIFormid());
            reviewItem.setBillId(reviewingBean.getIBillRecNo());
            currentItems.add(reviewItem);
        }
        setView(tab);
    }

    /**
     * 设置时间
     *
     * @param strDate
     * @return
     */
    private ReviewItem setTime(String strDate) {
        ReviewItem reviewItem = new ReviewItem();
        reviewItem.setText(strDate);
        reviewItem.setTextSize(getResources().getDimensionPixelSize(R.dimen.sp_12));
        reviewItem.setTextBold(false);
        reviewItem.setTextColor(getColor(R.color.default_content_color));
        return reviewItem;
    }

    /**
     * 加载列表
     *
     * @param tab
     */
    private void setView(int tab) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isFirst == true)
                    setFrist(tab);
                else {
                    if (tab == 0) {
                        setReviewing();
                    } else
                        setReviewed();
                }
            }
        });
    }

    /**
     * 新增
     * 加载未审核数据
     */
    private void setReviewing() {
        reviewItems.clear();
        reviewingItems.clear();
        reviewingItems.addAll(currentItems);
        reviewItems.addAll(currentItems);
        adapter.notifyDataSetChanged();
    }

    /**
     * 加载已审核数据
     */
    private void setReviewed() {
        if (isLoad != true)
            reviewItems.clear();
        reviewedItems.addAll(currentItems);
        reviewItems.addAll(currentItems);
        if (currentItems.size() < pageSize) {
            rvReview.setLoadingMoreEnabled(false);
        }
        adapter.notifyDataSetChanged();
        setRefresh();
        setLoad();
    }

    /**
     * 第一次绑定数据
     *
     * @param tab
     */
    private void setFrist(int tab) {
        isFirst = false;
        reviewItems.clear();
        if (tab == 0)//新增逻辑
            reviewingItems.addAll(currentItems);
        else
            reviewedItems.addAll(currentItems);
        reviewItems.addAll(currentItems);
        layoutManager = new LinearLayoutManager(ReviewActivity.this);
        adapter = new ReviewAdapter(ReviewActivity.this, reviewItems);
        adapter.setOnRecycleClickListener(new OnRecycleClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent();
                intent.putExtra("formId", reviewItems.get(position).getFormId());
                intent.putExtra("billId", reviewItems.get(position).getBillId());
                intent.putExtra("id", reviewItems.get(position).getId());
                intent.putExtra("tab", tabPosition);
//                Log.e(TAG, "tabPosition:" + tabPosition);
                intent.setClass(ReviewActivity.this, ReviewDetailActivity.class);
//                startActivity(intent);
                if (tab == 0)
                    startActivityForResult(intent, CodeUtil.REVIEWING_CODE);
                else
                    startActivityForResult(intent, CodeUtil.REVIEWED_CODE);
            }
        });
        rvReview.setLayoutManager(layoutManager);
        rvReview.setAdapter(adapter);
    }

    /**
     * 刷新完成
     */
    public void setRefresh() {
        if (isRefresh == true) {
            rvReview.refreshComplete();
            isRefresh = false;
        }

    }

    /**
     * 加载完成
     */
    public void setLoad() {
        if (isLoad == true) {
            rvReview.loadMoreComplete();
            isLoad = false;
        }
    }


    @OnClick({R.id.iv_back, R.id.tv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_right:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 200) {
            refreshReviewed = true;
            reviewingItems.clear();
            reviewReviewing();
        }
    }
}
