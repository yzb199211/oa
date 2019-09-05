package com.huanxin.oa.review;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.huanxin.oa.R;
import com.huanxin.oa.utils.ImageLoaderUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReviewImageActivity extends AppCompatActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.img)
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_image);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        ivBack.setBackgroundResource(R.mipmap.icon_back_white);
        Intent intent = getIntent();
        ImageLoaderUtil.loadImg(img, intent.getStringExtra("img"));
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
