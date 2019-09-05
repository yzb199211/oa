package com.huanxin.oa.review;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.huanxin.oa.R;
import com.huanxin.oa.dialog.JudgeDialog;
import com.huanxin.oa.dialog.LoadingDialog;
import com.huanxin.oa.interfaces.ResponseListener;
import com.huanxin.oa.model.review.detial.DetailsBean;
import com.huanxin.oa.model.review.detial.MainBean;
import com.huanxin.oa.model.review.detial.OpinionBean;
import com.huanxin.oa.model.review.detial.ReviewDetail;
import com.huanxin.oa.model.review.detial.TablesBean;
import com.huanxin.oa.review.model.ReviewInfo;
import com.huanxin.oa.review.model.ReviewProcess;
import com.huanxin.oa.review.model.ReviewStyle;
import com.huanxin.oa.utils.CodeUtil;
import com.huanxin.oa.utils.ImageLoaderUtil;
import com.huanxin.oa.utils.SharedPreferencesHelper;
import com.huanxin.oa.utils.StringUtil;
import com.huanxin.oa.utils.Toasts;
import com.huanxin.oa.utils.net.NetConfig;
import com.huanxin.oa.utils.net.NetParams;
import com.huanxin.oa.utils.net.NetUtil;
import com.huanxin.oa.utils.net.Otype;
import com.huanxin.oa.view.review.ReviewProcessView;
import com.huanxin.oa.view.review.util.ReviewStyleView1;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReviewDetailActivity extends AppCompatActivity {
    private final static String TAG = "ReviewDetailActivity";
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.bottom_review)
    RelativeLayout bottomReview;
    @BindView(R.id.scrolliew)
    ScrollView scrollView;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;

    List<ReviewInfo> infoList = new ArrayList<>();
    List<ReviewInfo> styleList = new ArrayList<>();
    List<ReviewStyle> styles = new ArrayList<>();
    List<ReviewProcess> processes = new ArrayList<>();
    List<String> imgs = new ArrayList<>();

    int id;
    int billId;
    int formId;
    int tab;
    String userId;
    String userName;
    String userDepartment;
    int resultCode;
    SharedPreferencesHelper preferencesHelper;
    EditText etRemark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_detail);
        ButterKnife.bind(this);
        init();
        setTop();

//        setData();
//        setInfoView();
//        setStyleView();
//        setProcessView();
    }

    private void init() {
        resultCode = CodeUtil.RESULT_NO;
        preferencesHelper = new SharedPreferencesHelper(this, getString(R.string.preferenceCache));
        userId = (String) preferencesHelper.getSharedPreference("userid", "");
        userName = (String) preferencesHelper.getSharedPreference("userName", "");
        userDepartment = (String) preferencesHelper.getSharedPreference("userDepartment", "");
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        formId = intent.getIntExtra("formId", 0);
        billId = intent.getIntExtra("billId", 0);
        tab = intent.getIntExtra("tab", 0);
//        Log.e(TAG, "tabPosition:" + tab);
        getData();
    }

    /**
     * 获取数据
     */
    private void getData() {
        LoadingDialog.showDialogForLoading(this);
        new NetUtil(getParams(), NetConfig.url + NetConfig.Review_Method, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
//                Log.e(TAG, string);
//                LoadingDialog.cancelDialogForLoading();
                try {
                    ReviewDetail reviewDetail = new Gson().fromJson(string, ReviewDetail.class);
                    if (reviewDetail.isSuccess()) {
                        initData(reviewDetail.getTables());
                    } else {
                        cancelDialog(reviewDetail.getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    cancelDialog(getString(R.string.error_data));
                }
            }

            @Override
            public void onFail(IOException e) {
                Log.e(TAG, e.getMessage());
                cancelDialog(getString(R.string.error_fail));
            }
        });
    }

    /**
     * 获取请求参数
     *
     * @return
     */
    private List<NetParams> getParams() {
        List<NetParams> list = new ArrayList<>();
        list.add(new NetParams("userid", userId));
        list.add(new NetParams("iFormID", formId + ""));
        list.add(new NetParams("iRecNo", billId + ""));
        list.add(new NetParams("otype", "GetFormData"));
//        Log.e(TAG, userId + "," + formId + "，" + billId);
        return list;
    }

    /**
     * 数据初始化
     *
     * @param tables
     * @throws Exception
     */
    private void initData(List<TablesBean> tables) throws Exception {

        List<MainBean> mainBeanList = tables.get(0).getMain();
        List<List<DetailsBean>> detailsBeanList = tables.get(0).getDetails();
        List<OpinionBean> opinionBeanList = tables.get(0).getOpinion();
        List<String> imgList = tables.get(0).getImage();

        initMain(mainBeanList);
        initDetail(detailsBeanList);
        initProgress(opinionBeanList);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LoadingDialog.cancelDialogForLoading();
                setInfo();
                setInfoView();
                setStyleView();
                if (imgList != null && imgList.size() > 0) {
                    imgs.addAll(imgList);
                    setImage();
                }
                setProcessView();
            }
        });
    }

    private void setInfo() {
        tvName.setText(userName);
        tvType.setText(userDepartment);
        tvEmpty.setVisibility(View.GONE);
        if (tab == 0)//新增判断
            bottomReview.setVisibility(View.VISIBLE);
        if (tab == 1) {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) scrollView.getLayoutParams();
            params.bottomMargin = 0;
        }

        scrollView.setVisibility(View.VISIBLE);
    }

    /**
     * 初始化主表数据
     *
     * @param mainBeanList
     * @throws Exception
     */
    private void initMain(List<MainBean> mainBeanList) throws Exception {
        for (int i = 0; i < mainBeanList.size(); i++) {
            MainBean mainBean = mainBeanList.get(i);
            ReviewInfo reviewInfo = new ReviewInfo();
            reviewInfo.setTitle(mainBean.getSFieldsdisplayName());
            reviewInfo.setContent(mainBean.getSFieldsValue());
            reviewInfo.setTitleBold(StringUtil.isBold(mainBean.getINameFontBold()));
            reviewInfo.setContentBold(StringUtil.isBold(mainBean.getIValueFontBold()));
            reviewInfo.setWidthPercent(StringUtil.isPercent(mainBean.getIProportion()));
            reviewInfo.setRow(mainBean.getISerial());
            if (StringUtil.isColor(mainBean.getSNameFontColor()))
                reviewInfo.setTitleColor(Color.parseColor(mainBean.getSNameFontColor()));
            if (StringUtil.isColor(mainBean.getSValueFontColor()))
                reviewInfo.setContentColor(Color.parseColor(mainBean.getSValueFontColor()));
            infoList.add(reviewInfo);
        }

    }

    /**
     * 初始化详情数据
     *
     * @param detailsBeanList
     * @throws Exception
     */
    private void initDetail(List<List<DetailsBean>> detailsBeanList) throws Exception {
        List<DetailsBean> detailsBeans = new ArrayList<>();
        for (int i = 0; i < detailsBeanList.size(); i++) {
            detailsBeans.clear();
            detailsBeans.addAll(detailsBeanList.get(i));
            List<ReviewInfo> reviewInfos = new ArrayList<>();
            for (int j = 0; j < detailsBeans.size(); j++) {
                ReviewInfo reviewInfo = new ReviewInfo();
                DetailsBean detailsBean = detailsBeans.get(j);
                reviewInfo.setTitle(detailsBean.getSFieldsdisplayName());
                reviewInfo.setContent(detailsBean.getSFieldsValue());
                reviewInfo.setTitleBold(StringUtil.isBold(detailsBean.getINameFontBold()));
                reviewInfo.setContentBold(StringUtil.isBold(detailsBean.getIValueFontBold()));
                reviewInfo.setWidthPercent(StringUtil.isPercent(detailsBean.getIProportion()));
                reviewInfo.setRow(detailsBean.getISerial());
                if (StringUtil.isColor(detailsBean.getSNameFontColor()))
                    reviewInfo.setTitleColor(Color.parseColor(detailsBean.getSNameFontColor()));
                if (StringUtil.isColor(detailsBean.getSValueFontColor()))
                    reviewInfo.setContentColor(Color.parseColor(detailsBean.getSValueFontColor()));
                reviewInfos.add(reviewInfo);
            }
            ReviewStyle reviewStyle = new ReviewStyle();
            reviewStyle.setList(reviewInfos);
            styles.add(reviewStyle);
        }
//        Log.e(TAG, new Gson().toJson(styles));
    }

    /**
     * 初始化审批流程数据
     *
     * @param opinionBeanList
     * @throws Exception
     */
    private void initProgress(List<OpinionBean> opinionBeanList) throws Exception {
        for (int i = 0; i < opinionBeanList.size(); i++) {
            OpinionBean opinionBean = opinionBeanList.get(i);
            ReviewProcess reviewProcess = new ReviewProcess();
            reviewProcess.setTitleText(opinionBean.getSName());
            reviewProcess.setContentText(opinionBean.getMesstype());
            reviewProcess.setDateText(StringUtil.getDate(opinionBean.getDDealDate(), StringUtil.DATETYPE));
            reviewProcess.setTvRemarkText(opinionBean.getSCheckIdeal());
            if (StringUtil.isNotEmpty(opinionBean.getSCheckIdeal()))
                reviewProcess.setTvRemarkVisiable(true);
            processes.add(reviewProcess);
        }
        if (tab == 0) {//新增已审核判断
            ReviewProcess reviewProcess = new ReviewProcess();
            reviewProcess.setTitleText(userName);
            reviewProcess.setEtRemarkVisiable(true);
            processes.add(reviewProcess);
        }
    }

    //设置顶部
    private void setTop() {
        rlTop.setBackground(getDrawable(R.color.white));
        tvTitle.setText(R.string.title_review_detail);
        tvTitle.setTextColor(getColor(R.color.default_text_color));
        ivBack.setVisibility(View.VISIBLE);

    }

    /**
     * 设置主表信息
     */
    private void setInfoView() {
        ReviewStyleView1 styleView = new ReviewStyleView1(this);
        //内边距设置放在样式单项加载前，防止长度计算不准确。
        styleView.setPadding(0, (int) getResources().getDimension(R.dimen.dp_10), 0, (int) getResources().getDimension(R.dimen.dp_20));
        styleView.getTvStyleTitle().setVisibility(View.GONE);
        //单项设置一定要在最后，否则可能会出现样式不对的现象。
        styleView.setInfoList(infoList);
        styleView.setBackgroundColor(getColor(R.color.white));
        llContent.addView(styleView);
    }

    /**
     * 设置子表信息
     */
    private void setStyleView() {
        for (int i = 0; i < styles.size(); i++) {
            ReviewStyleView1 styleView = new ReviewStyleView1(this);
            //内边距设置放在样式单项加载前，防止长度计算不准确。
            styleView.setPadding(0, 0, 0, (int) getResources().getDimension(R.dimen.dp_10));
//            styleView.setSpanCounts(styles.get(i).getSpanCounts());
            //单项设置一定要在最后，否则可能会出现样式不对的现象。
            styleView.setInfoList(styles.get(i).getList());
            styleView.setBackgroundColor(getColor(R.color.white));
            llContent.addView(styleView);
//            Log.e(TAG, i + "");
        }
    }

    /*设置图片*/
    private void setImage() {
        GridLayout imgLayout = new GridLayout(this);
        imgLayout.setPadding(getResources().getDimensionPixelOffset(R.dimen.dp_10), getResources().getDimensionPixelOffset(R.dimen.dp_10), getResources().getDimensionPixelOffset(R.dimen.dp_10), getResources().getDimensionPixelOffset(R.dimen.dp_10));
        imgLayout.setColumnCount(4);
        int row = getRow();
        imgLayout.setRowCount(row);
        for (int i = 0; i < row; i++) {

        }
        llContent.addView(imgLayout);
    }

    private void addImgChild(GridLayout imgLayout, int row, int col) {
        GridLayout.Spec rowSpec;
        rowSpec = GridLayout.spec(row, 1.0F);     //设置它的行和列
        GridLayout.Spec columnSpec = GridLayout.spec(col, 1.0F);
        GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);
        params.rightMargin = getResources().getDimensionPixelOffset(R.dimen.dp_1);
        params.topMargin = getResources().getDimensionPixelOffset(R.dimen.dp_1);
        params.leftMargin = getResources().getDimensionPixelOffset(R.dimen.dp_1);
        params.bottomMargin = getResources().getDimensionPixelOffset(R.dimen.dp_1);
        params.height = getResources().getDimensionPixelOffset(R.dimen.dp_60);
        imgLayout.addView(tvTitle, params);
    }

    private int getRow() {
        int row = 0;
        if (imgs.size() % 4 == 0) {
            row = imgs.size() / 4;
        } else {
            row = (imgs.size() - imgs.size() % 4) / 4 + 1;
        }
        return row;
    }

    /**
     * 设置审批流程信息
     */
    private void setProcessView() {

        for (int i = 0; i < processes.size(); i++) {
            ReviewProcessView processView = new ReviewProcessView(this);
            processView.setItem(processes.get(i));
            if (i == 0)
                processView.setLineTop(false);
            if (i == processes.size() - 1) {
                processView.setLineBottom(false);
                processView.setPadding(0, 0, 0, (int) getResources().getDimension(R.dimen.dp_20));
                if (tab == 0)//新增已审核判断
                    etRemark = processView.getEtRemark();
            }
            llContent.addView(processView);
            if (TextUtils.isEmpty(processes.get(i).getImgsrc()))
                ImageLoaderUtil.loadCircleImg(processView.getIvLogo(), R.drawable.placeholder, R.drawable.placeholder);
            else
                ImageLoaderUtil.loadCircleImg(processView.getIvLogo(), processes.get(i).getImgsrc(), R.drawable.placeholder);
        }
    }

    /**
     * 关闭保持页和提示错误
     *
     * @param message
     */
    private void cancelDialog(@Nullable String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LoadingDialog.cancelDialogForLoading();
                if (!TextUtils.isEmpty(message))
                    Toasts.showShort(ReviewDetailActivity.this, message);
            }
        });
    }

    @OnClick({R.id.iv_back, R.id.tv_agree, R.id.tv_disagree, R.id.tv_empty})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                setResult(resultCode);
                finish();
                break;
            case R.id.tv_agree:
                isPush();
                break;
            case R.id.tv_disagree:
                Log.e(TAG, etRemark.getText().toString());
                back();
                break;
            case R.id.tv_empty:
                getData();
                break;
        }
    }

    /**
     * 退回申请
     */
    private void back() {
        LoadingDialog.showDialogForLoading(this);
        new NetUtil(getBackParams(), NetConfig.url + NetConfig.Review_Handler_Method, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
//                Log.e(TAG, string);
                try {
                    initBack(string);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFail(IOException e) {
                cancelDialog(getString(R.string.error_fail));
            }
        });
    }

    /**
     * 退回审核
     *
     * @param string
     * @throws Exception
     */
    private void initBack(String string) throws Exception {
        JSONObject jsonObject = new JSONObject(string);
        boolean isSuccess = jsonObject.optBoolean("success");
        String message = jsonObject.optString("message");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LoadingDialog.cancelDialogForLoading();
                if (isSuccess == true) {
                    setResult(CodeUtil.RESULT_OK);
                    finish();
                } else {
//                    Log.e(TAG, message);
                    Toasts.showLong(ReviewDetailActivity.this, message);
                }
            }
        });
    }

    /**
     * 退回接口参数
     *
     * @return
     */
    private List<NetParams> getBackParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("otype", Otype.ReviewBack));
        params.add(new NetParams("message", etRemark.getText().toString()));
        params.add(new NetParams("iRecNo", id + ""));
//        Log.e(TAG, new Gson().toJson(params));
        return params;
    }

    /**
     * 判断是否推送接口
     */
    private void isPush() {
        LoadingDialog.showDialogForLoading(this);
        new NetUtil(getPushParams(), NetConfig.url + NetConfig.Review_Handler_Method, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
//                Log.e(TAG, string);
                try {
                    initPush(string);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(IOException e) {
                cancelDialog(getString(R.string.error_fail));
            }
        });
    }

    /**
     * 初始化
     *
     * @param string
     * @throws Exception
     */
    private void initPush(String string) throws Exception {
        JSONObject jsonObject = new JSONObject(string);
        boolean isSuccess = jsonObject.optBoolean("success");
        String message = jsonObject.optString("message");
        if (isSuccess == true) {
            if (TextUtils.isEmpty(message)) {
                sendData(false, false);
            } else {

                isPushDialog(message);
            }
        } else {
            cancelDialog(message);
        }
    }

    /**
     * 弹出判断框
     *
     * @param message
     */
    private void isPushDialog(String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LoadingDialog.cancelDialogForLoading();
                new JudgeDialog(ReviewDetailActivity.this, R.style.JudgeDialog, message, new JudgeDialog.OnCloseListener() {
                    @Override
                    public void onClick(boolean confirm) {
                        sendData(confirm, true);
                    }
                }).show();
            }
        });
    }

    /**
     * 获取判断推送参数
     *
     * @return
     */
    private List<NetParams> getPushParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("otype", Otype.ReviewPush));
        params.add(new NetParams("iRecNo", id + ""));
        return params;
    }

    /**
     * 提交同意接口
     *
     * @param isPush
     */
    private void sendData(boolean isPush, boolean isDialog) {
        if (isDialog == true) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    LoadingDialog.showDialogForLoading(ReviewDetailActivity.this);
                }
            });
        }
        new NetUtil(getSendParams(isPush), NetConfig.url + NetConfig.Review_Handler_Method, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                try {
                    initSendData(string);
                } catch (Exception e) {
                    cancelDialog("获取数据失败");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(IOException e) {
                cancelDialog(getString(R.string.error_fail));
            }
        });
    }

    private void initSendData(String data) throws Exception {
        JSONObject jsonObject = new JSONObject(data);
        boolean isSuccess = jsonObject.optBoolean("success");
        String message = jsonObject.optString("message");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isSuccess == true) {
                    setResult(CodeUtil.RESULT_OK);
                    finish();
                } else {
                    Toasts.showShort(ReviewDetailActivity.this, message);
                }
            }
        });
    }

    /**
     * 发送数据参数
     *
     * @param isPush
     * @return
     */
    private List<NetParams> getSendParams(boolean isPush) {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("otype", Otype.ReviewCheck));
        params.add(new NetParams("needPush", isPush + ""));
        params.add(new NetParams("message", etRemark.getText().toString()));
        params.add(new NetParams("iRecNo", id + ""));
        return params;
    }
}
