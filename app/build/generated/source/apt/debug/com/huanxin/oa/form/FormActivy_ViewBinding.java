// Generated code from Butter Knife. Do not modify!
package com.huanxin.oa.form;

import android.view.View;
import android.widget.GridLayout;
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

public class FormActivy_ViewBinding implements Unbinder {
  private FormActivy target;

  private View view7f0800aa;

  @UiThread
  public FormActivy_ViewBinding(FormActivy target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public FormActivy_ViewBinding(final FormActivy target, View source) {
    this.target = target;

    View view;
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tvTitle'", TextView.class);
    target.rlTop = Utils.findRequiredViewAsType(source, R.id.rl_top, "field 'rlTop'", RelativeLayout.class);
    target.glTitle = Utils.findRequiredViewAsType(source, R.id.gl_title, "field 'glTitle'", GridLayout.class);
    target.glForm = Utils.findRequiredViewAsType(source, R.id.gl_form, "field 'glForm'", GridLayout.class);
    view = Utils.findRequiredView(source, R.id.iv_back, "method 'onViewClicked'");
    view7f0800aa = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    FormActivy target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.rlTop = null;
    target.glTitle = null;
    target.glForm = null;

    view7f0800aa.setOnClickListener(null);
    view7f0800aa = null;
  }
}
