// Generated code from Butter Knife. Do not modify!
package com.huanxin.oa.review;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.huanxin.oa.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ReviewDetailActivity_ViewBinding implements Unbinder {
  private ReviewDetailActivity target;

  private View view7f0800a4;

  private View view7f080159;

  private View view7f08014f;

  private View view7f080158;

  @UiThread
  public ReviewDetailActivity_ViewBinding(ReviewDetailActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ReviewDetailActivity_ViewBinding(final ReviewDetailActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.iv_back, "field 'ivBack' and method 'onViewClicked'");
    target.ivBack = Utils.castView(view, R.id.iv_back, "field 'ivBack'", ImageView.class);
    view7f0800a4 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tvTitle'", TextView.class);
    target.rlTop = Utils.findRequiredViewAsType(source, R.id.rl_top, "field 'rlTop'", RelativeLayout.class);
    target.ivLogo = Utils.findRequiredViewAsType(source, R.id.iv_logo, "field 'ivLogo'", ImageView.class);
    target.tvName = Utils.findRequiredViewAsType(source, R.id.tv_name, "field 'tvName'", TextView.class);
    target.tvType = Utils.findRequiredViewAsType(source, R.id.tv_type, "field 'tvType'", TextView.class);
    target.llContent = Utils.findRequiredViewAsType(source, R.id.ll_content, "field 'llContent'", LinearLayout.class);
    target.bottomReview = Utils.findRequiredViewAsType(source, R.id.bottom_review, "field 'bottomReview'", RelativeLayout.class);
    target.scrollView = Utils.findRequiredViewAsType(source, R.id.scrolliew, "field 'scrollView'", ScrollView.class);
    view = Utils.findRequiredView(source, R.id.tv_empty, "field 'tvEmpty' and method 'onViewClicked'");
    target.tvEmpty = Utils.castView(view, R.id.tv_empty, "field 'tvEmpty'", TextView.class);
    view7f080159 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_agree, "method 'onViewClicked'");
    view7f08014f = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_disagree, "method 'onViewClicked'");
    view7f080158 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    ReviewDetailActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ivBack = null;
    target.tvTitle = null;
    target.rlTop = null;
    target.ivLogo = null;
    target.tvName = null;
    target.tvType = null;
    target.llContent = null;
    target.bottomReview = null;
    target.scrollView = null;
    target.tvEmpty = null;

    view7f0800a4.setOnClickListener(null);
    view7f0800a4 = null;
    view7f080159.setOnClickListener(null);
    view7f080159 = null;
    view7f08014f.setOnClickListener(null);
    view7f08014f = null;
    view7f080158.setOnClickListener(null);
    view7f080158 = null;
  }
}
