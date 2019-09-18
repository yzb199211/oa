// Generated code from Butter Knife. Do not modify!
package com.huanxin.oa.login;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.huanxin.oa.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LoginActivity_ViewBinding implements Unbinder {
  private LoginActivity target;

  private View view7f080052;

  private View view7f080054;

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginActivity_ViewBinding(final LoginActivity target, View source) {
    this.target = target;

    View view;
    target.ivLoginHeader = Utils.findRequiredViewAsType(source, R.id.iv_login_header, "field 'ivLoginHeader'", ImageView.class);
    target.etUser = Utils.findRequiredViewAsType(source, R.id.et_user, "field 'etUser'", EditText.class);
    target.etPwd = Utils.findRequiredViewAsType(source, R.id.et_pwd, "field 'etPwd'", EditText.class);
    view = Utils.findRequiredView(source, R.id.btn_login, "field 'btnLogin' and method 'onViewClicked'");
    target.btnLogin = Utils.castView(view, R.id.btn_login, "field 'btnLogin'", TextView.class);
    view7f080052 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_sweep, "field 'btnSweep' and method 'onViewClicked'");
    target.btnSweep = Utils.castView(view, R.id.btn_sweep, "field 'btnSweep'", TextView.class);
    view7f080054 = view;
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
    LoginActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ivLoginHeader = null;
    target.etUser = null;
    target.etPwd = null;
    target.btnLogin = null;
    target.btnSweep = null;

    view7f080052.setOnClickListener(null);
    view7f080052 = null;
    view7f080054.setOnClickListener(null);
    view7f080054 = null;
  }
}
