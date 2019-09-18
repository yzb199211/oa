// Generated code from Butter Knife. Do not modify!
package com.huanxin.oa;

import android.view.View;
import android.widget.Button;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PickActivity_ViewBinding implements Unbinder {
  private PickActivity target;

  private View view7f080051;

  private View view7f080055;

  private View view7f080053;

  @UiThread
  public PickActivity_ViewBinding(PickActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public PickActivity_ViewBinding(final PickActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.btn_date, "field 'btnDate' and method 'onViewClicked'");
    target.btnDate = Utils.castView(view, R.id.btn_date, "field 'btnDate'", Button.class);
    view7f080051 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_time, "field 'btnTime' and method 'onViewClicked'");
    target.btnTime = Utils.castView(view, R.id.btn_time, "field 'btnTime'", Button.class);
    view7f080055 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_other, "field 'btnOther' and method 'onViewClicked'");
    target.btnOther = Utils.castView(view, R.id.btn_other, "field 'btnOther'", Button.class);
    view7f080053 = view;
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
    PickActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.btnDate = null;
    target.btnTime = null;
    target.btnOther = null;

    view7f080051.setOnClickListener(null);
    view7f080051 = null;
    view7f080055.setOnClickListener(null);
    view7f080055 = null;
    view7f080053.setOnClickListener(null);
    view7f080053 = null;
  }
}
