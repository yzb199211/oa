// Generated code from Butter Knife. Do not modify!
package com.huanxin.oa.lookup;

import android.view.View;
import android.widget.EditText;
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

public class LookUpActivity_ViewBinding implements Unbinder {
  private LookUpActivity target;

  private View view7f0800aa;

  @UiThread
  public LookUpActivity_ViewBinding(LookUpActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LookUpActivity_ViewBinding(final LookUpActivity target, View source) {
    this.target = target;

    View view;
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tvTitle'", TextView.class);
    target.rlTop = Utils.findRequiredViewAsType(source, R.id.rl_top, "field 'rlTop'", RelativeLayout.class);
    target.etSearch = Utils.findRequiredViewAsType(source, R.id.et_search, "field 'etSearch'", EditText.class);
    target.tvEmpty = Utils.findRequiredViewAsType(source, R.id.tv_empty, "field 'tvEmpty'", TextView.class);
    target.rvItem = Utils.findRequiredViewAsType(source, R.id.rv_item, "field 'rvItem'", RecyclerView.class);
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
    LookUpActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.rlTop = null;
    target.etSearch = null;
    target.tvEmpty = null;
    target.rvItem = null;

    view7f0800aa.setOnClickListener(null);
    view7f0800aa = null;
  }
}
