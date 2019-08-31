// Generated code from Butter Knife. Do not modify!
package com.huanxin.oa.chart;

import android.view.View;
import android.widget.GridLayout;
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

public class ChartActivity_ViewBinding implements Unbinder {
  private ChartActivity target;

  private View view7f0800a4;

  @UiThread
  public ChartActivity_ViewBinding(ChartActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ChartActivity_ViewBinding(final ChartActivity target, View source) {
    this.target = target;

    View view;
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tvTitle'", TextView.class);
    target.rlTop = Utils.findRequiredViewAsType(source, R.id.rl_top, "field 'rlTop'", RelativeLayout.class);
    target.tvRight = Utils.findRequiredViewAsType(source, R.id.tv_right, "field 'tvRight'", TextView.class);
    target.llContent = Utils.findRequiredViewAsType(source, R.id.ll_content, "field 'llContent'", LinearLayout.class);
    target.glData = Utils.findRequiredViewAsType(source, R.id.gl_data, "field 'glData'", GridLayout.class);
    view = Utils.findRequiredView(source, R.id.iv_back, "method 'onViewClicked'");
    view7f0800a4 = view;
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
    ChartActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.rlTop = null;
    target.tvRight = null;
    target.llContent = null;
    target.glData = null;

    view7f0800a4.setOnClickListener(null);
    view7f0800a4 = null;
  }
}
