package com.huanxin.oa.form.merge;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.huanxin.oa.R;
import com.huanxin.oa.form.model.FormBean;
import com.huanxin.oa.form.model.FormModel;
import com.huanxin.oa.form.scroll.MyHorizontalScrollView;
import com.huanxin.oa.utils.LogUtil;
import com.huanxin.oa.utils.PxUtil;
import com.huanxin.oa.utils.StringUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class FormMerge extends LinearLayout {
    Context context;

    int width;
    int itemWidth;
    int levels;

    boolean isSerial = true;

    String data;

    List<FormBean.ReportColumnsBean> columnsTitle;
    FormMergeAdapter2 formAdapter;

    MyHorizontalScrollView scrollTitle;
    MyHorizontalScrollView scollForm;
    RecyclerView rvForm;
    LinearLayout llTitlte;

    HorizontalScrollListener horizontalScrollListener = new HorizontalScrollListener();

    public Handler mHandler = new Handler();

    public FormMerge(Context context) {
        this(context, null);
    }

    public FormMerge(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.form_merge, this, true);
        init();
    }

    private void init() {
        itemWidth = PxUtil.getWidth(context) / 5;
        setOrientation(VERTICAL);
        setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));
        initView();
    }

    private void initView() {
        llTitlte = findViewById(R.id.ll_title);
        rvForm = findViewById(R.id.rv_form);
        scrollTitle = findViewById(R.id.scroll_title);
        scollForm = findViewById(R.id.scroll_form);
        initRecycle();
        setScorll();
        initHandler();
    }

    private void initRecycle() {
        rvForm.setLayoutManager(new LinearLayoutManager(context));
    }

    private void initHandler() {
        //设置横向初始位置，延迟500，不然无效
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollTitle.scrollTo(0, 0);
            }
        }, 500);
    }

    private void setScorll() {
        setScorllHorListener();
    }

    private void setScorllHorListener() {
        scrollTitle.setOnHorizontalScrollListener(horizontalScrollListener);
        scollForm.setOnHorizontalScrollListener(horizontalScrollListener);
    }

    private class HorizontalScrollListener implements MyHorizontalScrollView.OnHorizontalScrollListener {
        @Override
        public void onHorizontalScrolled(MyHorizontalScrollView view, int l, int t, int oldl, int oldt) {
            if (view == scrollTitle) {
                scollForm.scrollTo(l, t);
            } else {
                scrollTitle.scrollTo(l, t);
            }
        }

    }

    private void setTitles() {
        for (int i = 0; i < columnsTitle.size(); i++) {

            if (i == 0)
                llTitlte.addView(getTitleView(columnsTitle.get(i).getSFieldsdisplayName(), true));
            else
                llTitlte.addView(getTitleView(columnsTitle.get(i).getSFieldsdisplayName(), false));
        }
    }

    private TextView getTitleView(String title, boolean isFirst) {
        TextView textView = new TextView(context);
        textView.setLayoutParams(getTitleParams(isFirst));
        textView.setText(title);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(context.getColor(R.color.white));
        textView.setTextSize(14);
        textView.setBackgroundColor(context.getColor(R.color.blue));
        return textView;
    }

    private LinearLayout.LayoutParams getTitleParams(boolean isFirst) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(itemWidth, ViewGroup.LayoutParams.MATCH_PARENT);
        params.rightMargin = 1;
        params.topMargin = 1;
        params.bottomMargin = 1;
        if (isFirst)
            params.leftMargin = 1;
        return params;
    }

    public void setColumnsTitle(List<FormBean.ReportColumnsBean> columnsTitle) {
        this.columnsTitle = columnsTitle;
        width = columnsTitle.size() * itemWidth;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void build() throws Exception {
        setTitles();
        initData();
    }

    private void initData() throws Exception {
        if (StringUtil.isNotEmpty(data)) {
            JSONArray jsonArray = new JSONArray(data);
            if (jsonArray.length() > 0) {
//                refreshView(getStartData(jsonArray));
//                getFormData(getStartData(jsonArray));
//                refreshView(FormMergeUtil.initFormCol(jsonArray, columnsTitle));
                List<FormModel> item = FormMergeUtil.getFormColumns(jsonArray, columnsTitle);
                refreshView((FormMergeUtil.buildByRecursive(item)));
                LogUtil.e("data", new Gson().toJson(FormMergeUtil.buildByRecursive(item)));
            }
        }
    }

    private List<FormModel> getFormData(List<List<FormModel>> startData) throws Exception {
        List<FormModel> list = new ArrayList<>();
        for (int i = 0; i < startData.size(); i++) {
            for (int j = 0; j < levels; j++) {
                startData.get(i).get(j).getTitle();

            }
        }
        return list;
    }

    private List<List<FormModel>> getStartData(JSONArray jsonArray) throws Exception {
        List<List<FormModel>> lists = new ArrayList<>();
        int num = 0;
        for (int i = 0; i < jsonArray.length(); i++) {
            lists.add(getStartRow(jsonArray.getJSONObject(i), i));
        }
        return lists;
    }

    private List<FormModel> getStartRow2(JSONObject jsonObject, int row, int num) throws Exception {
        List<FormModel> list = new ArrayList<>();
        for (int col = 0; col < columnsTitle.size(); col++) {

            FormBean.ReportColumnsBean columnsBean = columnsTitle.get(col);
            FormModel column = new FormModel();
            column.setRow(row);
            column.setCol(col);
//            column.setParent(columnsBean.getIMerge() == 1 ? true : false);
            column.setTitle(getColumnText(columnsBean, jsonObject));
            if (columnsBean.getIMerge() == 1 && isSerial == true) {

                column.setParent(true);
            }
            list.add(column);
        }
        return list;
    }

    private List<FormModel> getStartRow(JSONObject jsonObject, int row) throws Exception {
        List<FormModel> list = new ArrayList<>();
        for (int col = 0; col < columnsTitle.size(); col++) {
            FormBean.ReportColumnsBean columnsBean = columnsTitle.get(col);
            FormModel column = new FormModel();
            column.setRow(row);
            column.setCol(col);
//            column.setParent(columnsBean.getIMerge() == 1 ? true : false);
            column.setTitle(getColumnText(columnsBean, jsonObject));
            if (columnsBean.getIMerge() == 1 && isSerial == true) {
                column.setId(col + 1);
                column.setPid(col);
                column.setParent(true);
            } else {
                isSerial = false;
            }
            list.add(column);
        }
        return list;
    }

    private String getColumnText(FormBean.ReportColumnsBean columnsBean, JSONObject jsonObject) {
        return jsonObject.optString(columnsBean.getSFieldsName());
    }

    //    private void refreshView(List<List<FormModel>> data) {
//        if (formAdapter == null) {
//            formAdapter = new FormMergeAdapter(context, data);
//            rvForm.setAdapter(formAdapter);
//        }
//    }
    private void refreshView(List<FormModel> data) {
        if (formAdapter == null) {
            formAdapter = new FormMergeAdapter2(context, data, columnsTitle.size());
            rvForm.setAdapter(formAdapter);
        }
    }
}
