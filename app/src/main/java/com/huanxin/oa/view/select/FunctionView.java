package com.huanxin.oa.view.select;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.huanxin.oa.R;
import com.huanxin.oa.dialog.LoadingDialog;
import com.huanxin.oa.interfaces.ResponseListener;
import com.huanxin.oa.lookup.LookUpActivity;
import com.huanxin.oa.utils.StringUtil;
import com.huanxin.oa.utils.Toasts;
import com.huanxin.oa.utils.net.NetConfig;
import com.huanxin.oa.utils.net.NetParams;
import com.huanxin.oa.utils.net.NetUtil;
import com.huanxin.oa.view.pick.builder.TimePickerBuilder;
import com.huanxin.oa.view.pick.listener.OnTimeSelectChangeListener;
import com.huanxin.oa.view.pick.listener.OnTimeSelectListener;
import com.huanxin.oa.view.pick.view.TimePickerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.huanxin.oa.utils.StringUtil.getDate;
import static com.huanxin.oa.utils.StringUtil.getDefaulText;
import static com.huanxin.oa.utils.StringUtil.getLookupData;
import static com.huanxin.oa.utils.StringUtil.getTime;
import static com.huanxin.oa.utils.StringUtil.isNotEmpty;

public class FunctionView extends LinearLayout {
    Context context;
    TextView tvTitle;
    TextView tvContent;
    EditText etContent;
    Switch switvhContent;

    OnClickListener onClickListener;

    private int code;
    private String view_type = "";
    private String title;
    private String text;
    private String hint;
    private String data;
    private String lookupName;
    private String lookupFilter;

    private Activity mActivity;
    TimePickerView timePicker;
    TimePickerView datePicker;

    public FunctionView(Context context) {
        this(context, null);
    }

    public FunctionView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

    }

    public FunctionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs);
    }


    private void init() {
        LayoutInflater.from(context).inflate(R.layout.select_view, this, true);
        setGravity(Gravity.CENTER_VERTICAL);
        setOrientation(HORIZONTAL);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, context.getResources().getDimensionPixelOffset(R.dimen.dp_40));
//        params.height = context.getResources().getDimensionPixelOffset(R.dimen.dp_40);
        setPadding(context.getResources().getDimensionPixelOffset(R.dimen.dp_10), context.getResources().getDimensionPixelOffset(R.dimen.dp_10), context.getResources().getDimensionPixelOffset(R.dimen.dp_10), context.getResources().getDimensionPixelOffset(R.dimen.dp_10));
        params.topMargin = context.getResources().getDimensionPixelOffset(R.dimen.dp_1);
        setLayoutParams(params);
        setBackgroundColor(context.getColor(R.color.white));

        tvTitle = findViewById(R.id.tv_title);
        tvContent = findViewById(R.id.tv_content);
        etContent = findViewById(R.id.et_content);
        switvhContent = findViewById(R.id.switch_view);
        tvTitle.setText(TextUtils.isEmpty(title) ? "" : title);
        tvContent.setHint(TextUtils.isEmpty(hint) ? "" : hint);
        tvContent.setText(getDefaulText(context, text));
        this.text = getDefaulText(context,text);
        setView();

    }

    private void setView() {
        switch (view_type.toUpperCase()) {
            case "D":
                setDateLookUp();
                break;
            case "DT":
                setTimeLookUp();
                break;
            case "S":
                if (TextUtils.isEmpty(lookupName)) {
                    setEdit();
                } else {
                    setLookup();
                }
                break;
            case "F":
                if (TextUtils.isEmpty(lookupName)) {
                    setEdit();
                } else {
                    setLookup();
                }
                break;
            case "B":
                setSwitch();
                break;
            default:
                break;
        }
    }

    /*设置日期选择*/
    private void setDateLookUp() {
        tvContent.setVisibility(VISIBLE);
        tvContent.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setDatePick();
            }
        });
    }

    /*设置时间选择*/
    private void setTimeLookUp() {
        tvContent.setVisibility(VISIBLE);
        tvContent.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setTimePick();
            }
        });
    }

    /*设置输入*/
    private void setEdit() {
        etContent.setVisibility(VISIBLE);
        if (view_type.equals("F")) {
            etContent.setInputType(EditorInfo.TYPE_CLASS_NUMBER | EditorInfo.TYPE_NUMBER_FLAG_DECIMAL);
        }
        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                text = s.toString();
            }
        });

    }

    /*设置lookup*/
    private void setLookup() {
        tvContent.setVisibility(VISIBLE);
        tvContent.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(data)) {
                    getData();
                } else {
                    goLookup();
                }
            }
        });
    }

    /*获取数据*/
    private void getData() {
        LoadingDialog.showDialogForLoading(mActivity);
        new NetUtil(getParams(), NetConfig.url + NetConfig.MobileHandler_Method, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
//                Log.e("data", string);
                try {
                    JSONObject jsonObject = new JSONObject(string);
                    boolean isSuccess = jsonObject.optBoolean("success");
                    if (isSuccess) {
                        String keyValue = jsonObject.optString("data");
                        if (StringUtil.isNotEmpty(keyValue)) {
                            JSONObject keyJson = new JSONObject(keyValue);
                            String keyReturn = keyJson.optString("sReturnField");
                            String keyShow = keyJson.optString("sDisplayField");
                            if (StringUtil.isNotEmpty(keyReturn) && StringUtil.isNotEmpty(keyShow)) {
                                initLookupData(jsonObject.optString("tables"), keyReturn, keyShow);
                            } else {
                                loadFail("未返回关键字");
                            }
                        } else {
                            loadFail("未返回关键字");
                        }
                    } else {
                        loadFail(jsonObject.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    loadFail("json数据解析错误");
                } catch (Exception e) {
                    loadFail("数据解析错误");
                }
            }

            @Override
            public void onFail(IOException e) {
                e.printStackTrace();
                loadFail("未获取到数据");
            }
        });
    }

    /*处理Lookup数据*/
    private void initLookupData(String tables, String keyReturn, String keyShow) throws JSONException, Exception {

        //Log.e("jsonArrayLength", jsonArray.length() + "");
        if (tables.contains(keyReturn) && tables.contains(keyShow)) {
            this.data = getLookupData(tables, keyReturn, keyShow);
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    goLookup();
                }
            });
            loadFail("");
        } else {
            loadFail("lookup数据或关键字错误");
        }

    }

    /*获取数据参数*/
    private List<NetParams> getParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("otype", "GetLookUpData"));
        params.add(new NetParams("sLookUpFilters", ""));
        params.add(new NetParams("lookUpName", lookupName));
        return params;
    }

    /*跳转lookup选择*/
    private void goLookup() {
        Intent intent = new Intent();
        intent.setClass(context, LookUpActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("data", data);
        intent.putExtra("code", code);
        mActivity.startActivityForResult(intent, code);
    }

    /*设置switch*/
    private void setSwitch() {
        switvhContent.setVisibility(VISIBLE);
        if (text.equals("1")) {
            switvhContent.setChecked(true);
        } else {
            text = "0";
        }
        switvhContent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    text = "1";
                } else {
                    text = "0";
                }
            }
        });
        if (text.equals("1")) {
            switvhContent.setChecked(true);
        } else {
            switvhContent.setChecked(false);
        }
    }

    /**
     * 设置日期选择器
     */
    private void setDatePick() {
        if (datePicker == null) {
            datePicker = new TimePickerBuilder(context, new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    tvContent.setText(getDate(date));
                    setText(getDate(date));
//                    Toast.makeText(context, getDate(date), Toast.LENGTH_SHORT).show();
//                    Log.i("pvTime", "onTimeSelect");
                }
            })
                    .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                        @Override
                        public void onTimeSelectChanged(Date date) {
//                            Log.i("pvTime", "onTimeSelectChanged");
                        }
                    })
                    .setType(new boolean[]{true, true, true, false, false, false})
                    .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                    .addOnCancelClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
//                            Log.i("pvTime", "onCancelClickListener");
                        }
                    }).setContentTextSize(18).setBgColor(0xFFFFFFFF)
                    .build();

            Dialog mDialog = datePicker.getDialog();
            if (mDialog != null) {

                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        Gravity.BOTTOM);

                params.leftMargin = 0;
                params.rightMargin = 0;
                datePicker.getDialogContainerLayout().setLayoutParams(params);

                Window dialogWindow = mDialog.getWindow();
                if (dialogWindow != null) {
                    dialogWindow.setWindowAnimations(R.style.picker_view_slide_anim);//修改动画样式
                    dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
                    dialogWindow.setDimAmount(0.1f);
                    //当显示只有一列是需要设置window宽度，防止两边有空隙；
                    WindowManager.LayoutParams winParams;
                    winParams = dialogWindow.getAttributes();
                    winParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                    dialogWindow.setAttributes(winParams);
                }
            }
        }
        datePicker.show();
    }

    /**
     * 设置时间选择器
     */
    private void setTimePick() {
        if (timePicker == null) {
            timePicker = new TimePickerBuilder(context, new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    tvContent.setText(getTime(date));
                    setText(getTime(date));
//                    Toast.makeText(context, getDate(date), Toast.LENGTH_SHORT).show();
//                    Log.i("pvTime", "onTimeSelect");
                }
            })
                    .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                        @Override
                        public void onTimeSelectChanged(Date date) {
//                            Log.i("pvTime", "onTimeSelectChanged");
                        }
                    })
                    .setType(new boolean[]{true, true, true, true, true, false})
                    .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                    .addOnCancelClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
//                            Log.i("pvTime", "onCancelClickListener");
                        }
                    }).setContentTextSize(18).setBgColor(0xFFFFFFFF)
                    .build();

            Dialog mDialog = datePicker.getDialog();
            if (mDialog != null) {

                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        Gravity.BOTTOM);

                params.leftMargin = 0;
                params.rightMargin = 0;
                datePicker.getDialogContainerLayout().setLayoutParams(params);

                Window dialogWindow = mDialog.getWindow();
                if (dialogWindow != null) {
                    dialogWindow.setWindowAnimations(R.style.picker_view_slide_anim);//修改动画样式
                    dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
                    dialogWindow.setDimAmount(0.1f);
                    //当显示只有一列是需要设置window宽度，防止两边有空隙；
                    WindowManager.LayoutParams winParams;
                    winParams = dialogWindow.getAttributes();
                    winParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                    dialogWindow.setAttributes(winParams);
                }
            }
        }
        datePicker.show();
    }

    /*请求关闭LoadingDialog和弹出提示*/
    private void loadFail(String message) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LoadingDialog.cancelDialogForLoading();
                if (isNotEmpty(message))
                    Toasts.showShort(context, message);
            }
        });
    }

    public String getView_type() {
        return view_type;
    }

    public void setView_type(String view_type) {
        this.view_type = view_type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        if (tvContent != null)
            tvContent.setText(text);
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public void build(@NonNull String view_type) {
        this.view_type = view_type;
        init();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getLookupName() {
        return lookupName;
    }

    public void setLookupName(String lookupName) {
        this.lookupName = lookupName;
    }

    public String getLookupFilter() {
        return lookupFilter;
    }

    public void setLookupFilter(String lookupFilter) {
        this.lookupFilter = lookupFilter;
    }

    public Activity getmActivity() {
        return mActivity;
    }

    public void setmActivity(Activity mActivity) {
        this.mActivity = mActivity;
    }

}
