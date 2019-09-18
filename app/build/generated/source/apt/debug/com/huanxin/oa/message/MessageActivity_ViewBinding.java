// Generated code from Butter Knife. Do not modify!
package com.huanxin.oa.message;

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
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MessageActivity_ViewBinding implements Unbinder {
  private MessageActivity target;

  private View view7f0800aa;

  @UiThread
  public MessageActivity_ViewBinding(MessageActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MessageActivity_ViewBinding(final MessageActivity target, View source) {
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
    target.rlTop = Utils.findRequiredViewAsType(source, R.id.rl_top, "field 'rlTop'", RelativeLayout.class);
    target.rvMessage = Utils.findRequiredViewAsType(source, R.id.rv_message, "field 'rvMessage'", XRecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MessageActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ivBack = null;
    target.tvTitle = null;
    target.rlTop = null;
    target.rvMessage = null;

    view7f0800aa.setOnClickListener(null);
    view7f0800aa = null;
  }
}
