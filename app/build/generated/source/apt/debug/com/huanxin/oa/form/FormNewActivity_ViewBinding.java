// Generated code from Butter Knife. Do not modify!
package com.huanxin.oa.form;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.bin.david.form.core.SmartTable;
import com.huanxin.oa.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FormNewActivity_ViewBinding implements Unbinder {
  private FormNewActivity target;

  private View view7f0800aa;

  private View view7f080167;

  @UiThread
  public FormNewActivity_ViewBinding(FormNewActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public FormNewActivity_ViewBinding(final FormNewActivity target, View source) {
    this.target = target;

    View view;
    target.table = Utils.findRequiredViewAsType(source, R.id.table, "field 'table'", SmartTable.class);
    target.llTab = Utils.findRequiredViewAsType(source, R.id.ll_tab, "field 'llTab'", LinearLayout.class);
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tvTitle'", TextView.class);
    view = Utils.findRequiredView(source, R.id.iv_back, "field 'ivBack' and method 'onViewClicked'");
    target.ivBack = Utils.castView(view, R.id.iv_back, "field 'ivBack'", ImageView.class);
    view7f0800aa = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_right, "field 'tvRight' and method 'onViewClicked'");
    target.tvRight = Utils.castView(view, R.id.tv_right, "field 'tvRight'", TextView.class);
    view7f080167 = view;
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
    FormNewActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.table = null;
    target.llTab = null;
    target.tvTitle = null;
    target.ivBack = null;
    target.tvRight = null;

    view7f0800aa.setOnClickListener(null);
    view7f0800aa = null;
    view7f080167.setOnClickListener(null);
    view7f080167 = null;
  }
}
