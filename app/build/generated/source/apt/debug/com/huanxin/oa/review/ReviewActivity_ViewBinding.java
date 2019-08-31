// Generated code from Butter Knife. Do not modify!
package com.huanxin.oa.review;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.huanxin.oa.R;
import com.huanxin.oa.view.review.ReviewTabView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ReviewActivity_ViewBinding implements Unbinder {
  private ReviewActivity target;

  private View view7f0800a4;

  private View view7f08015f;

  @UiThread
  public ReviewActivity_ViewBinding(ReviewActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ReviewActivity_ViewBinding(final ReviewActivity target, View source) {
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
    view = Utils.findRequiredView(source, R.id.tv_right, "field 'tvRight' and method 'onViewClicked'");
    target.tvRight = Utils.castView(view, R.id.tv_right, "field 'tvRight'", TextView.class);
    view7f08015f = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.rlTop = Utils.findRequiredViewAsType(source, R.id.rl_top, "field 'rlTop'", RelativeLayout.class);
    target.rvReview = Utils.findRequiredViewAsType(source, R.id.rv_reviewed, "field 'rvReview'", XRecyclerView.class);
    target.topLine = Utils.findRequiredView(source, R.id.top_line, "field 'topLine'");
    target.tabView = Utils.findRequiredViewAsType(source, R.id.tab_view, "field 'tabView'", ReviewTabView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ReviewActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ivBack = null;
    target.tvTitle = null;
    target.tvRight = null;
    target.rlTop = null;
    target.rvReview = null;
    target.topLine = null;
    target.tabView = null;

    view7f0800a4.setOnClickListener(null);
    view7f0800a4 = null;
    view7f08015f.setOnClickListener(null);
    view7f08015f = null;
  }
}
