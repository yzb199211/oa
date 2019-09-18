// Generated code from Butter Knife. Do not modify!
package com.huanxin.oa.main.fragment;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.huanxin.oa.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FormFragment_ViewBinding implements Unbinder {
  private FormFragment target;

  private View view7f080160;

  @UiThread
  public FormFragment_ViewBinding(final FormFragment target, View source) {
    this.target = target;

    View view;
    target.rlTop = Utils.findRequiredViewAsType(source, R.id.rl_top, "field 'rlTop'", RelativeLayout.class);
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tvTitle'", TextView.class);
    target.rvForm = Utils.findRequiredViewAsType(source, R.id.rv_form, "field 'rvForm'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.tv_empty, "field 'tvEmpty' and method 'onViewClicked'");
    target.tvEmpty = Utils.castView(view, R.id.tv_empty, "field 'tvEmpty'", TextView.class);
    view7f080160 = view;
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
    FormFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rlTop = null;
    target.tvTitle = null;
    target.rvForm = null;
    target.tvEmpty = null;

    view7f080160.setOnClickListener(null);
    view7f080160 = null;
  }
}
