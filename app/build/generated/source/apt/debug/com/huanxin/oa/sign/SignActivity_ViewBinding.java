// Generated code from Butter Knife. Do not modify!
package com.huanxin.oa.sign;

import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.huanxin.oa.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SignActivity_ViewBinding implements Unbinder {
  private SignActivity target;

  private View view7f08004f;

  private View view7f080155;

  private View view7f08015e;

  private View view7f080159;

  private View view7f0800aa;

  @UiThread
  public SignActivity_ViewBinding(SignActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SignActivity_ViewBinding(final SignActivity target, View source) {
    this.target = target;

    View view;
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tvTitle'", TextView.class);
    target.rlTop = Utils.findRequiredViewAsType(source, R.id.rl_top, "field 'rlTop'", RelativeLayout.class);
    target.tvTime = Utils.findRequiredViewAsType(source, R.id.tv_time, "field 'tvTime'", TextView.class);
    target.tvLocation = Utils.findRequiredViewAsType(source, R.id.tv_location, "field 'tvLocation'", TextView.class);
    view = Utils.findRequiredView(source, R.id.btn_camera, "field 'btnCamera' and method 'onViewClicked'");
    target.btnCamera = Utils.castView(view, R.id.btn_camera, "field 'btnCamera'", ImageView.class);
    view7f08004f = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.ivImg = Utils.findRequiredViewAsType(source, R.id.iv_img, "field 'ivImg'", ImageView.class);
    target.flImg = Utils.findRequiredViewAsType(source, R.id.fl_img, "field 'flImg'", FrameLayout.class);
    view = Utils.findRequiredView(source, R.id.tv_agree, "field 'tvAgree' and method 'onViewClicked'");
    target.tvAgree = Utils.castView(view, R.id.tv_agree, "field 'tvAgree'", TextView.class);
    view7f080155 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_disagree, "field 'tvDisagree' and method 'onViewClicked'");
    target.tvDisagree = Utils.castView(view, R.id.tv_disagree, "field 'tvDisagree'", TextView.class);
    view7f08015e = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.line = Utils.findRequiredView(source, R.id.line, "field 'line'");
    target.bottomReview = Utils.findRequiredViewAsType(source, R.id.bottom_review, "field 'bottomReview'", RelativeLayout.class);
    view = Utils.findRequiredView(source, R.id.tv_customer, "field 'tvCustomer' and method 'onViewClicked'");
    target.tvCustomer = Utils.castView(view, R.id.tv_customer, "field 'tvCustomer'", TextView.class);
    view7f080159 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.ivPhoto = Utils.findRequiredViewAsType(source, R.id.iv_photo, "field 'ivPhoto'", ImageView.class);
    target.etRemark = Utils.findRequiredViewAsType(source, R.id.et_remark, "field 'etRemark'", EditText.class);
    view = Utils.findRequiredView(source, R.id.iv_back, "field 'ivBack' and method 'onViewClicked'");
    target.ivBack = Utils.castView(view, R.id.iv_back, "field 'ivBack'", ImageView.class);
    view7f0800aa = view;
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
    SignActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.rlTop = null;
    target.tvTime = null;
    target.tvLocation = null;
    target.btnCamera = null;
    target.ivImg = null;
    target.flImg = null;
    target.tvAgree = null;
    target.tvDisagree = null;
    target.line = null;
    target.bottomReview = null;
    target.tvCustomer = null;
    target.ivPhoto = null;
    target.etRemark = null;
    target.ivBack = null;

    view7f08004f.setOnClickListener(null);
    view7f08004f = null;
    view7f080155.setOnClickListener(null);
    view7f080155 = null;
    view7f08015e.setOnClickListener(null);
    view7f08015e = null;
    view7f080159.setOnClickListener(null);
    view7f080159 = null;
    view7f0800aa.setOnClickListener(null);
    view7f0800aa = null;
  }
}
