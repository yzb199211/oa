package com.huanxin.oa.view.select;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.huanxin.oa.R;
import com.huanxin.oa.utils.CodeUtil;

public class SelectView extends LinearLayout {
    Context context;
    TextView tvTitle;
    TextView tvContent;
    EditText etContent;
    Switch switvhContent;
    OnClickListener onClickListener;
    int type;
    boolean isCheck = false;
    String title;
    String text;
    String hint;

    private SelectView setType(int type) {
        this.type = type;
        return this;
    }

    public void setText(String text) {
        this.text = text;
        tvContent.setText(text);
    }

    public String getText() {
        switch (type) {
            case CodeUtil.SELECT_TEXT:
                text = tvContent.getText().toString();
                break;
            case CodeUtil.SELECT_EDIT:
                text = etContent.getText().toString();
                break;
            case CodeUtil.SELECT_SWITCH:
                text = isCheck + "";
                break;
            default:
                text = "";
                break;
        }
        return text;
    }

    public SelectView SetTitle(String title) {
        this.title = title;
        return this;
    }

    public void setOnClickListeners(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public SelectView(Context context) {
        this(context, null);
    }

    public SelectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SelectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SelectView);
            title = typedArray.getString(R.styleable.SelectView_title);
            hint = typedArray.getString(R.styleable.SelectView_hint);
            type = typedArray.getInteger(R.styleable.SelectView_type, CodeUtil.SELECT_TEXT);
            typedArray.recycle();
        }
        init(context);
    }

    public interface onClickListener {
        void onClick(View view);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.select_view, this, true);

        setGravity(Gravity.CENTER_VERTICAL);
        setOrientation(HORIZONTAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, this.context.getResources().getDimensionPixelOffset(R.dimen.dp_40));
        setLayoutParams(params);
        tvTitle = findViewById(R.id.tv_title);
        tvContent = findViewById(R.id.tv_content);
        etContent = findViewById(R.id.et_content);

        setView();
        setData();
    }

    private void setView() {
        tvTitle.setText(title);
        switch (type) {
            case CodeUtil.SELECT_TEXT:
                tvContent.setVisibility(VISIBLE);
                tvContent.setHint(hint);
                break;
            case CodeUtil.SELECT_EDIT:
                etContent.setVisibility(VISIBLE);
                etContent.setHint(hint);
                break;
            case CodeUtil.SELECT_SWITCH:
                switvhContent.setVisibility(VISIBLE);
                break;
            default:
                break;
        }
        setSwitch();
    }

    private void setSwitch() {
        switvhContent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isCheck = isChecked;
            }
        });
    }

    private void setData() {
        tvContent.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null)
                    onClickListener.onClick(v);
            }
        });
    }
}
