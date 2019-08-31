package com.huanxin.oa.main.utils;

import android.content.Context;

import com.huanxin.oa.R;
import com.huanxin.oa.model.main.PersonModel;

import java.util.ArrayList;
import java.util.List;

public class MineUtil {
    List<PersonModel> ids = new ArrayList<>();
    Context context;

    public List<MineItem> getItemList() {
        for (int i = 0; i < ids.size(); i++)
            judge(ids.get(i));
        return itemList;
    }


    List<MineItem> itemList = new ArrayList<>();

    public MineUtil(Context context, List<PersonModel> ids) {
        this.ids = ids;
        this.context = context;
    }

    private void judge(PersonModel person) {
        switch (person.getId()) {
            case 1:
                itemList.add(new MineItem(person.getId(), 10, R.mipmap.icon_person, true, context.getString(R.string.mine_person), false, true, person.getContent(), false));
                break;
            case 2:
                itemList.add(new MineItem(person.getId(), 10, R.mipmap.icon_mobile, true, context.getString(R.string.mine_mobile), false, false, person.getContent(), false));
                break;
            case 3:
                itemList.add(new MineItem(person.getId(), 10, R.mipmap.icon_mail, true, context.getString(R.string.mine_mail), false, false, person.getContent(), false));
                break;
            case 4:
                itemList.add(new MineItem(person.getId(), 10, R.mipmap.icon_help, true, context.getString(R.string.mine_help), false, true, person.getContent(), false));
                break;
            case 5:
                itemList.add(new MineItem(person.getId(), 1, R.mipmap.icon_study, true, context.getString(R.string.mine_study), false, true, person.getContent(), false));
                break;
            case 6:
                itemList.add(new MineItem(person.getId(), 1, R.mipmap.icon_code, true, context.getString(R.string.mine_code), false, true, person.getContent(), false));
                break;
            default:
                break;
        }
    }
}
