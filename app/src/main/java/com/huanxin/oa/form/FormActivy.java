package com.huanxin.oa.form;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.huanxin.oa.BaseActivity;
import com.huanxin.oa.R;
import com.huanxin.oa.model.form.FormModel;
import com.huanxin.oa.model.form.FormTitle;
import com.huanxin.oa.utils.PxUtil;
import com.huanxin.oa.view.form.GridUtil;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FormActivy extends BaseActivity {
    private final static String TAG = "FormActivy";
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.gl_title)
    GridLayout glTitle;
    @BindView(R.id.gl_form)
    GridLayout glForm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        ButterKnife.bind(this);
        setFormTitle();
        setForm();
        List<FormModel> list = getFromModel();
        Log.e(TAG, GridUtil.getRowSpan(list) + "");
    }

    private void setForm() {
    }

    public static List<FormModel> getFromModel() {
        List<FormModel> ones = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            FormModel one = new FormModel();
            one.setText(i + "");
            List<FormModel> twos = new ArrayList<>();
            for (int j = 0; j < 1; j++) {
                FormModel two = new FormModel();
                List<FormModel> threes = new ArrayList<>();
                for (int k = 0; k < 2; k++) {
                    FormModel three = new FormModel();
                    three.setText("ni");
                    threes.add(three);
                }
//                formModel11.setText("j=" + j);
                two.setFormModels(threes);
                twos.add(two);
            }
            one.setFormModels(twos);
            ones.add(one);
        }
        return ones;
    }

    private void setFormTitle() {
        glTitle.setColumnCount(4);
        glTitle.setRowCount(2);
        for (int i = 0; i < 5; i++) {
            TextView tvTitle = (TextView) LayoutInflater.from(this).inflate(R.layout.item_form, null);
            tvTitle.setText("标题");
            tvTitle.setGravity(Gravity.CENTER);
            tvTitle.setPadding(2, 15, 2, 15);
            tvTitle.setBackgroundColor(getColor(R.color.colorAccent));
            GridLayout.Spec rowSpec;
            if (i == 0)
                rowSpec = GridLayout.spec(0, 2, 1);
            else if (i == 4)
                rowSpec = GridLayout.spec(1, 1, 1);
            else
                rowSpec = GridLayout.spec(0, 1, 1);
            GridLayout.Spec columnSpec;
            //设置它的行和列
            if (i == 4)
                columnSpec = GridLayout.spec(1, 2, 1);
            else if (i == 0) {
                columnSpec = GridLayout.spec(i, 1, 2);
            } else
                columnSpec = GridLayout.spec(i, 1, 1);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);
            params.setGravity(Gravity.FILL);
            params.leftMargin = 1;
            params.bottomMargin = 1;
            glTitle.addView(tvTitle, params);
        }

    }

    private GridLayout.LayoutParams setParams(int col, int last, float weight) {
        GridLayout.Spec rowSpec = GridLayout.spec(0, 1.0F);     //设置它的行和列
        GridLayout.Spec columnSpec = GridLayout.spec(col, 1.0F);
        GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);
        params.setGravity(Gravity.CENTER);
        params.leftMargin = 1;
        if (col == last) {
            params.rightMargin = 1;
        }
        params.width = (int) (PxUtil.getWidth(this) * weight);
        params.height = 60;
        return params;
    }

    private void addItem(int row, int col) {
        TextView tvTitle = (TextView) LayoutInflater.from(this).inflate(R.layout.item_form, null);
        tvTitle.setText("标题");
        tvTitle.setGravity(Gravity.CENTER);
        tvTitle.setBackgroundColor(getColor(R.color.colorAccent));
        GridLayout.Spec rowSpec;
        rowSpec = GridLayout.spec(0, 1.0F);     //设置它的行和列
        GridLayout.Spec columnSpec = GridLayout.spec(col, 1.0F);
        GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);
        params.setGravity(Gravity.CENTER);
        params.leftMargin = 1;
        if (col == 3) {
            params.rightMargin = 1;
        }
        params.width = 264;
        params.height = 60;
        glTitle.addView(tvTitle, params);

    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
