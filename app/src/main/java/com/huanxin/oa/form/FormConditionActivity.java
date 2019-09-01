package com.huanxin.oa.form;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huanxin.oa.BaseActivity;
import com.huanxin.oa.R;
import com.huanxin.oa.form.model.FormBean;
import com.huanxin.oa.utils.SharedPreferencesHelper;
import com.huanxin.oa.utils.StringUtil;
import com.huanxin.oa.view.select.FunctionView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FormConditionActivity extends BaseActivity {
    private static final String TAG = "FormConditionActivity";
    private static final int BASE_CODE = 500;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.bottom_review)
    RelativeLayout bottonView;
    @BindView(R.id.tv_disagree)
    TextView tvDisagree;

    SharedPreferencesHelper preferencesHelper;
    private String userId;
    private List<FormBean.ReportConditionBean> conditions;
    private List<FunctionView> viewList;

    public static FormConditionActivity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        setContentView(R.layout.activity_form_condition);
        ButterKnife.bind(this);
        preferencesHelper = new SharedPreferencesHelper(this, getString(R.string.preferenceCache));
        userId = (String) preferencesHelper.getSharedPreference("userid", "");
        init();
    }

    private void init() {
        tvTitle.setText("筛选条件");
        ivBack.setVisibility(View.VISIBLE);
        tvDisagree.setVisibility(View.INVISIBLE);
        conditions = new ArrayList<>();
        viewList = new ArrayList<>();
        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        if (StringUtil.isNotEmpty(data)) {
            conditions = new Gson().fromJson(data, new TypeToken<List<FormBean.ReportConditionBean>>() {
            }.getType());
            initView();
        }

    }

    private void initView() {
        if (conditions.size() > 0) {
            bottonView.setVisibility(View.VISIBLE);
            for (int i = 0; i < conditions.size(); i++) {
                FunctionView view = new FunctionView(this);
//        view.setView_type("S");
                view.setTitle(conditions.get(i).getSCaption());
                view.setHint("请输入" + conditions.get(i).getSCaption());
                view.setText(conditions.get(i).getSValue());
                view.setLookupName(conditions.get(i).getSLookUpName());
                view.setLookupFilter(conditions.get(i).getSLookUpFilters());
                view.setCode(BASE_CODE + i);
                view.setmActivity(this);
                view.build(conditions.get(i).getSFieldType());
                llContent.addView(view);
                viewList.add(view);
            }
        }
    }


    @OnClick({R.id.iv_back, R.id.tv_agree})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_agree:
                break;
            default:
                break;
        }
    }
}
