package com.huanxin.oa.main.utils;

import android.content.Context;

import com.huanxin.oa.R;
import com.huanxin.oa.model.login.MenuBean;
import com.huanxin.oa.utils.FontUtil;

import java.util.ArrayList;
import java.util.List;

public class MenuUtil {
    Context context;

    List<MenuBean> menuBeans = new ArrayList<>();

    public MenuUtil(List<MenuBean> menuBeans, Context context) {
        this.menuBeans = menuBeans;
        this.context = context;
    }

    public MenuUtil(Context context) {
        this.context = context;
    }

    public List<Menu> getMenu() {
        List<Menu> menus = new ArrayList<>();
        if (menuBeans.size() == 0) {
            menus.add(new Menu(1, R.mipmap.icon_message, context.getString(R.string.menu_message), 1));
            menus.add(new Menu(2, R.mipmap.icon_review, context.getString(R.string.menu_review), 0));
            menus.add(new Menu(3, R.mipmap.icon_check, context.getString(R.string.menu_check)));
            menus.add(new Menu(4, R.mipmap.icon_daily, context.getString(R.string.menu_daily)));
        }
        for (int i = 0; i < menuBeans.size(); i++) {
            MenuBean menuBean = menuBeans.get(i);
            menus.add(new Menu(menuBean.getIFormID(), FontUtil.getImg(menuBean.getSIcon()), menuBean.getSMenuName()));
        }
        return menus;
    }


}


