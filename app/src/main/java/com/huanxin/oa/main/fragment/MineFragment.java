package com.huanxin.oa.main.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huanxin.oa.R;
import com.huanxin.oa.login.LoginActivity;
import com.huanxin.oa.main.MainActivity;
import com.huanxin.oa.main.utils.MineItem;
import com.huanxin.oa.main.utils.MineUtil;
import com.huanxin.oa.model.login.PersonBean;
import com.huanxin.oa.model.main.PersonModel;
import com.huanxin.oa.view.MineItemView;
import com.huanxin.oa.view.triangle.RigthAngleStriangle;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class MineFragment extends Fragment implements View.OnClickListener {
    private final static String TAG = "MineFragment";

    private static final int RASCODE = 100;
    private static final int CANCELCODE = 101;

    private RelativeLayout rlTop;
    private TextView tvTitle;
    private LinearLayout llContent;
    private CardView cardView;
    private TextView tvName;
    private TextView tvContent;
    private TextView tvDepartment;
    private ImageView ivLogo;
    private TextView tvCode;
    private RigthAngleStriangle rasEdit;
    private TextView btnCancel;

    List<PersonModel> personModels = new ArrayList<>();
    List<MineItem> itemList = new ArrayList<>();
    List<PersonBean> personBeans = new ArrayList<>();
    String initData;

    public MineFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        initData = ((MainActivity) context).getMineData();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            intiData();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        initView(view);
        setTop();
        setData();
        setButton();
        return view;
    }

    //初始化数据
    private void intiData() throws Exception {
        personBeans = new Gson().fromJson(initData, new TypeToken<List<PersonBean>>() {
        }.getType());
        Log.d(TAG, personBeans.get(0).getSTel() + "");
        personModels.add(new PersonModel(1));
        personModels.add(new PersonModel(2, personBeans.get(0).getSTel()));
        personModels.add(new PersonModel(3));
        personModels.add(new PersonModel(4));
        personModels.add(new PersonModel(5));
        personModels.add(new PersonModel(6));
        MineUtil mineUtil = new MineUtil(getActivity(), personModels);
        itemList = mineUtil.getItemList();
    }

    //加载子项
    private void setData() {
        if (itemList.size() > 0) {
            for (int i = 0; i < itemList.size(); i++) {
                MineItem item = itemList.get(i);
                MineItemView itemView = new MineItemView(getActivity());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                if (item.getMarginTop() == 10)
                    params.setMargins(0, (int) getContext().getResources().getDimension(R.dimen.dp_10), 0, 0);//4个参数按顺序分别是左上右下
                else
                    params.setMargins(0, (int) getContext().getResources().getDimension(R.dimen.dp_1), 0, 0);//4个参数按顺序分别是左上右下
                itemView.setLayoutParams(params);
                itemView.setBackground(getContext().getDrawable(R.color.white));
                itemView.setGravity(Gravity.CENTER_VERTICAL);
                itemView.setMinimumHeight((int) getContext().getResources().getDimension(R.dimen.dp_50));
                itemView.setTag(item.getId());
                itemView.setCenterText(item.getCenterText());
                if (item.isCenterTextIsBlod() == true)
                    itemView.setCenterTextIsBlod(true);
                itemView.setCenterContentText(item.getCenterContentText());
                if (item.isCenterContentIsBlod() == true)
                    itemView.setCenterContentIsBlod(true);
                if (item.isRightVisible() == true)
                    itemView.setRightVisible(true);
                if (item.getLeftSrc() != 0)
                    itemView.setLeftSrc(item.getLeftSrc());
                itemView.setOnClickListener(this);
                llContent.addView(itemView);
            }
        }

    }

    //设置取消按钮
    private void setButton() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.btn_cancel, llContent, false);
        btnCancel = view.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(this);
        btnCancel.setTag(CANCELCODE);
        llContent.addView(btnCancel);
    }

    //初始化控件
    private void initView(View view) {
        rlTop = view.findViewById(R.id.rl_top);
        tvTitle = view.findViewById(R.id.tv_title);
        llContent = view.findViewById(R.id.ll_content);
        cardView = view.findViewById(R.id.cardView);
        tvName = view.findViewById(R.id.tv_name);
        tvCode = view.findViewById(R.id.tv_code);
        tvContent = view.findViewById(R.id.tv_content);
        tvDepartment = view.findViewById(R.id.tv_department);
        ivLogo = view.findViewById(R.id.iv_logo);
        tvCode = view.findViewById(R.id.tv_code);
        rasEdit = view.findViewById(R.id.rasEdit);
        rasEdit.setTag(RASCODE);
        rasEdit.setOnClickListener(this);
        try {
            bindData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //绑定数据
    private void bindData() throws Exception {
        PersonBean personBean = personBeans.get(0);
        tvName.setText(personBean.getSName());
        tvContent.setText(personBean.getSType());
        tvDepartment.setText(personBean.getSClassName());
        tvCode.setText(personBean.getSCode());
    }

    //设置顶部
    private void setTop() {
        rlTop.setBackground(getContext().getDrawable(R.color.white));
        tvTitle.setText(R.string.title_user_center);
        tvTitle.setTextColor(getContext().getColor(R.color.default_text_color));
    }

    @Override
    public void onClick(View v) {
        switch ((int) v.getTag()) {
            case CANCELCODE:
                Intent intent = new Intent();
                intent.setClass(getActivity(), LoginActivity.class);
                getActivity().finish();
                break;
            default:
                break;
        }
    }
}
