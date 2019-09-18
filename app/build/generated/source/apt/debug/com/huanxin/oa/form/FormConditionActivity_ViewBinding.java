// Generated code from Butter Knife. Do not modify!
package com.huanxin.oa.form;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class FormConditionActivity_ViewBinding implements Unbinder {
  private FormConditionActivity target;

  private View view7f0800aa;

  private View view7f080155;

  @UiThread
  public FormConditionActivity_ViewBinding(FormConditionActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public FormConditionActivity_ViewBinding(final FormConditionActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.iv_back, "field 'ivBack' and method 'onViewClicked'");
    target.ivBack = Utils.castView(view, R.id.iv_back, "field 'ivBack'", ImageView.class);
    view7f0800aa = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tvTitle'", TextView.class);
    target.llContent = Utils.findRequiredViewAsType(source, R.id.ll_content, "field 'llContent'", LinearLayout.class);
    target.bottonView = Utils.findRequiredViewAsType(source, R.id.bottom_review, "field 'bottonView'", RelativeLayout.class);
    target.tvDisagree = Utils.findRequiredViewAsType(source, R.id.tv_disagree, "field 'tvDisagree'", TextView.class);
    view = Utils.findRequiredView(source, R.id.tv_agree, "method 'onViewClicked'");
    view7f080155 = view;
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
    FormConditionActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ivBack = null;
    target.tvTitle = null;
    target.llContent = null;
    target.bottonView = null;
    target.tvDisagree = null;

    view7f0800aa.setOnClickListener(null);
    view7f0800aa = null;
    view7f080155.setOnClickListener(null);
    view7f080155 = null;
  }
}
