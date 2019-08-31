package com.huanxin.oa.message;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.huanxin.oa.BaseActivity;
import com.huanxin.oa.R;
import com.huanxin.oa.dialog.LoadingDialog;
import com.huanxin.oa.message.adapter.MessaAdapter;
import com.huanxin.oa.model.message.MessageBean;
import com.huanxin.oa.model.message.MessageItem;
import com.huanxin.oa.utils.net.NetConfig;
import com.huanxin.oa.utils.net.NetParams;
import com.huanxin.oa.utils.net.NetUtil;
import com.huanxin.oa.utils.Toasts;
import com.huanxin.oa.interfaces.ResponseListener;
import com.huanxin.oa.utils.net.Otype;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MessageActivity extends BaseActivity {
    private final static String TAG = "MessageActivity";
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.rv_message)
    XRecyclerView rvMessage;
    MessaAdapter mAdapter;
    List<MessageItem> messages;
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
        init();
        getData();
    }


    private void init() {
        messages = new ArrayList<>();
        ivBack.setVisibility(View.VISIBLE);
        rlTop.setBackground(getDrawable(R.color.white));
        tvTitle.setText("提醒");
        tvTitle.setTextColor(getColor(R.color.default_text_color));
        rvMessage.setPullRefreshEnabled(false);
        Intent intent = getIntent();
        userid = intent.getStringExtra("userid");
    }

    private void getData() {
        LoadingDialog.showDialogForLoading(this);
        new NetUtil(getParams(), NetConfig.url + NetConfig.Message_Method, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
//                Log.e(TAG, string);
                initData(string);
            }

            @Override
            public void onFail(IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LoadingDialog.cancelDialogForLoading();
                        Toasts.showShort(MessageActivity.this, e.getMessage());
                    }
                });
            }
        });
    }

    private void initData(String string) {
        MessageBean messageBean = new Gson().fromJson(string, MessageBean.class);
        if (messageBean.isSuccess()) {
            messages.addAll(messageBean.getTables().getRemindList());
//            messages = messageBean.getTables().getRemindList();
            setView();
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    LoadingDialog.cancelDialogForLoading();
                    Toasts.showShort(MessageActivity.this, messageBean.getMessage());
                }
            });
        }

    }

    private void setView() {
        if (messages.size() > 0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    LoadingDialog.cancelDialogForLoading();
                    LinearLayoutManager manager = new LinearLayoutManager(MessageActivity.this);
                    mAdapter = new MessaAdapter(messages, MessageActivity.this);
                    rvMessage.setLayoutManager(manager);
                    rvMessage.setAdapter(mAdapter);
                }
            });

        }
    }

    /**
     * 接口参数
     *
     * @return
     */
    private List<NetParams> getParams() {
        List<NetParams> list = new ArrayList<>();
        list.add(new NetParams("userid", userid));
        list.add(new NetParams("otype", Otype.MessageList));
        return list;
    }

    @OnClick({R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            default:
                break;
        }
    }
}
